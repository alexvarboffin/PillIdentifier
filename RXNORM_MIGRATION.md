# Миграция RxNorm: легаси Retrofit -> библиотека :network:lk0-kmp (DefaultApi)

Дата: 2026-10-23. Ветка: dev/ktor-api (без коммита).
Сборка: :app:assembleDebug OK (low21Google, low21Rustore). На устройстве ещё НЕ проверено.

Общее для всех перенесённых вызовов:
- хост тот же: https://rxnav.nlm.nih.gov/REST/
- параметр caller=RxNav больше не передаётся (в API его нет);
- нет HTTP-логирования (раньше BODY-логгер) и retryOnConnectionFailure;
- таймауты 60 с, не-2xx -> null, колбэки в главном потоке (lib/service/RxNormLib.kt).

## 1. Перешли безболезненно (то же поведение)

| Где вызывается | Было (Retrofit) | Стало (DefaultApi) | Эндпоинт |
|---|---|---|---|
| RxnormRepository.globalRequest | searchRxcui(name, 2, 1) | findRxcui(name, search=2, allsrc=1) | /rxcui.json |
| Fragment2 | allProperties(id, "RxNav", "ALL") | getAllProperties(rxcui, ["ALL"]) | /rxcui/{rxcui}/allProperties.json |
| FragmentNdc | getNDCs | getNDCs | /rxcui/{rxcui}/ndcs.json |
| FragmentNdcHistory | allhistoricalndcs(id, null, 1) | getAllHistoricalNDCs(rxcui, 1) | /rxcui/{rxcui}/allhistoricalndcs.json |
| FragmentRelated | getAllRelatedInfo | getAllRelatedInfo | /rxcui/{rxcui}/allrelated.json |
| FragmentProperties | getRxConceptProperties | getRxConceptProperties | /rxcui/{rxcui}/properties.json |
| FragmentRelatedByType | getTermTypes | getTermTypes | /termtypes.json |
| FragmentRelatedByType | getRelatedByType | getRelated(tty=...) | /rxcui/{rxcui}/related.json?tty= |
| FragmentRelatedByType | getRelatedByRelationship | getRelated(rela=...) | /rxcui/{rxcui}/related.json?rela= |

## 2. Перешли, но поведение изменилось (проверить на устройстве)

| Где вызывается | Метод | Что изменилось |
|---|---|---|
| FragmentRelatedByType | getRelaTypes -> getRelaTypes | Список теперь реально приходит; старый DTO читал не то поле и всегда был пуст. |
| Fragment7 | historystatus -> getRxcuiHistoryStatus | scdConcept и qdFreeConcept теперь объект, а не список (как отдаёт сервер). |
| RxNorm.kt (подсказки) | getSpellingSuggestions | При не-2xx приходит null вместо падения с NPE. |

## 3. Остались на Retrofit (в генерации нет)

Подробности по живым пробам: %TEMP%\legacy_endpoints_probe.md (73 метода).
- interaction / interactionWithSources: сервис закрыт (404), FragmentInteraction:89 не работает.
- allrelatedextension: живой, вызывается только в закомментированном коде.
- RxClassApi: FragmentClass:77 (byRxcui, работает); AnalogsActivity:49/64/100 сломаны (неверные пути, classId вместо rxcui).
- RxTermsApi.getAllRxTermInfo: Fragment5:75, работает.
- PrescribableRxNormApi: 24 метода с неверным префиксом (prescribable/ -> Prescribe/), не вызываются.
- API2.searchDrag: openFDA (api.fda.gov), другой API, NlmRxFragment:559.

## 4. Удалено (бэкап: %TEMP%\pill_rxnorm_backup)
- lib/datamodel/rxnorm/response/RxNormResponses.kt (57 DTO)
- lib/datamodel/AttributesResponse.java

## 5. Сверка легаси DTO с живыми ответами (исправленные пути)

Полный отчёт: %TEMP%\legacy_dto_vs_live.md. Из 68 живых методов: 16 совпадают, 8 частично, 44 не совпадают (42 по корневому ключу -> пустой экран, 2 по типу -> исключение Gson).

Вызываемые в приложении:
| Где | Метод | Вердикт |
|---|---|---|
| FragmentClass:77 | getClassByRxNormDrugId (class/byRxcui) | совпадает |
| Fragment5:75 | getAllRxTermInfo | совпадает |
| NlmRxFragment:559 | openFDA searchDrag | совпадает |
| AnalogsActivity:49 | getSourcesOfDrugClassRelations | НЕТ: DTO sourcesOfDrugClassRelationsList, сервер relaSourceList.relaSourceName |
| AnalogsActivity:64 | getClassMembersWithSource | НЕТ: DTO rxclassDrugInfoList, сервер drugMemberGroup.drugMember; нужны родные relaSource+rela |
| AnalogsActivity:100 | byRxcui | НЕТ: DTO rxclassMinConceptList, сервер rxclassDrugInfoList; плюс передаётся classId вместо rxcui (400) |

Вывод: AnalogsActivity сломан на всех трёх шагах; в спеку эти ответы описывать по живому JSON, не по легаси DTO.

## 6. RxClass / RxTerms: перенос 5 вызовов (23.10.2026, без коммита)

В спеку RxNorm-OpenAPI добавлены 4 операции (34 -> 38): теги `RxClass` (getClassByRxcui, getClassMembers, getRelaSources) и `RxTerms` (getAllRxTermInfo). Генерация не менялась (один DefaultApi, пакет gov.nih.nlm.rxnorm). desktopTest: 135/135, newman: 45 запросов / 215 проверок, 0 ошибок.

| Где | Было (Retrofit) | Стало (DefaultApi) | Эндпоинт |
|---|---|---|---|
| FragmentClass.loadData | RxClassApi.getClassByRxNormDrugId(rxcui) -> Response8 | getClassByRxcui(rxcui) | /rxclass/class/byRxcui.json |
| Fragment5.loadData | RxTermsApi.getAllRxTermInfo(rxcui) -> Response5 | getAllRxTermInfo(rxcui) | /RxTerms/rxcui/{rxcui}/allinfo.json |
| AnalogsActivity.loadAnalogs | getSourcesOfDrugClassRelations(classId, classType) | getRelaSources() | /rxclass/relaSources.json |
| AnalogsActivity.loadAnalogs | getClassMembersWithSource(classId, relaSource) | getClassMembers(classId, relaSource, rela) | /rxclass/classMembers.json |
| AnalogsActivity.loadClassInfoFallback | byRxcui(classId) | getClassByRxcui(rxcui) | /rxclass/class/byRxcui.json |

Минимальные исправления логики (AnalogsActivity был сломан на всех трёх шагах):
- ClassCardItem: добавлены `relaSource`, `rela` (по умолчанию ""). FragmentClass кладёт в них родные relaSource/rela класса; AnalogsActivity.start получает их и rxcui препарата (новые extras EXTRA_RELA_SOURCE, EXTRA_RELA, EXTRA_RXCUI).
- Источник: родной relaSource класса, если он есть в getRelaSources (или список пуст); иначе первый из PRIORITY_SOURCES, найденный в списке. Строка -> enum `RelaSource` без учёта регистра.
- rela: передаётся родной rela класса (обязателен для не-ATC источников, например VA: has_VAclass; без него сервер отдаёт {}). Если источник подменён — rela не передаётся.
- classMembers читается из `drugMemberGroup.drugMember[].minConcept` (а не rxclassDrugInfoList).
- Fallback: getClassByRxcui вызывается с rxcui препарата (раньше передавался classId -> HTTP 400); классы дедуплицируются по classId; при пустом rxcui — «Нет информации…».
- Fragment5: `prop.x ?: ""` -> `prop.x` (в модели non-null). Ответ {} (ингредиент и т.п.) -> rxtermsProperties = null -> EmptyViewObj, 404 -> null — как раньше.
- Старый код закомментирован (`//    old Retrofit ...`), старые импорты закомментированы. Fragment5 больше не реализует Callback<Response5>.

Удалено: ничего. Response5/Response8, RxClassResponses.kt и интерфейсы RxClassApi/RxTermsApi/RxnormApi в RxNav.kt ещё используются (NlmRxFragment импортирует Response5/Response8, в RxNav.kt остаются непокрытые методы). Бэкап изменённых файлов: %TEMP%\pill_rxclass_backup.

Изменение поведения (проверить на устройстве): экран аналогов теперь реально показывает членов класса (заголовок «Аналоги класса: … (источник: …)», строки имя/TTY); при пустом списке/ошибке — список классов препарата.

## 7. Обоснование замен

Метод: каждый легаси DTO развёрнут до листовых полей (путь от корня ответа, `[]` — элемент списка, тип Kotlin как в DTO) и сопоставлен с листовыми полями сгенерированной модели (`gov.nih.nlm.rxnorm.models`, путь по JSON-именам, тип как в сгенерированном коде; `?` — nullable). Сгенерированные модели строго соответствуют живым ответам rxnav.nlm.nih.gov (23.10.2026; проверено strict-тестами desktopTest 135/135 и newman). Сравнение — по структуре и смыслу полей, а не по именам классов.

Статусы: **идентично** — тот же путь и тип; **переименовано** — те же данные под другим путём/именем обёртки; **тип изменён** — та же величина, но другая кратность (список ↔ объект); **отсутствует** — поле DTO, которого нет в живом ответе; **новое** — поле живого ответа, которого не было в DTO.

### 7.1. RxNorm (12 вызовов, перенесены ранее)

#### 1. RxnormRepository.globalRequest: `searchRxcui(name, 2, 1)` → `findRxcui(name, search=2, allsrc=1)`

Легаси DTO: `Response0`. Новое: `DefaultApi.findRxcui` → `IdGroupResponse` (`/rxcui.json`). Живые образцы: `rxcui.json?name=tylenol&search=2&allsrc=1`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `idGroup.rxnormId[]` (`List<String>?`) | `idGroup.rxnormId[]` (`List<String>?`) | идентично |
| — | `idGroup.name` (`String?`) | новое |

**Вывод:** Response0.idGroup.rxnormId: List<String> -> IdGroupResponse.idGroup.rxnormId: List<String> (идентично; новое idGroup.name) => замена верна: та же цель (поиск RxCUI по имени), тот же лист rxnormId.

#### 2. Fragment2: `allProperties(id, "RxNav", "ALL")` → `getAllProperties(rxcui, listOf("ALL"))`

Легаси DTO: `Response4`. Новое: `DefaultApi.getAllProperties` → `PropConceptGroupResponse` (`/rxcui/{rxcui}/allProperties.json`). Живые образцы: `rxcui/161/allProperties.json?prop=ALL`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `propConceptGroup.propConcept[].propCategory` (`String?`) | `propConceptGroup.propConcept[].propCategory` (`String?`) | идентично |
| `propConceptGroup.propConcept[].propName` (`String?`) | `propConceptGroup.propConcept[].propName` (`String?`) | идентично |
| `propConceptGroup.propConcept[].propValue` (`String?`) | `propConceptGroup.propConcept[].propValue` (`String?`) | идентично |
| `propConceptGroup.propConcept[].propSource` (`String?`) | — | отсутствует |
| `propConceptGroup.propConcept[].propType` (`String?`) | — | отсутствует |

**Вывод:** Response4.propConceptGroup.propConcept[].{propCategory,propName,propValue} -> PropConceptGroupResponse.propConceptGroup.propConcept[].{…} (идентично); propSource/propType отсутствуют в live => замена верна: эти два поля сервер не отдаёт (их не было и в старом JSON), приложение их не читает.

#### 3. FragmentNdc: `getNDCs(rxcui)` → `getNDCs(rxcui)`

Легаси DTO: `RxNormNDCsResponse`. Новое: `DefaultApi.getNDCs` → `NdcGroupResponse` (`/rxcui/{rxcui}/ndcs.json`). Живые образцы: `rxcui/198440/ndcs.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `ndcGroup.ndcList.ndc[]` (`List<String>`) | `ndcGroup.ndcList.ndc[]` (`List<String>?`) | идентично |
| `ndcGroup.rxcui` (`String?`) | `ndcGroup.rxcui` (`String?`) | идентично |

**Вывод:** RxNormNDCsResponse.ndcGroup.{rxcui, ndcList.ndc} -> NdcGroupResponse.ndcGroup.{rxcui, ndcList.ndc} (идентично) => замена верна: форма 1:1.

#### 4. FragmentNdcHistory: `allhistoricalndcs(id, null, 1)` → `getAllHistoricalNDCs(rxcui, 1)`

Легаси DTO: `RxNormHistoricalNDCsResponse`. Новое: `DefaultApi.getAllHistoricalNDCs` → `HistoricalNdcConceptResponse` (`/rxcui/{rxcui}/allhistoricalndcs.json`). Живые образцы: `rxcui/198440/allhistoricalndcs.json?history=1`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `historicalNdcConcept.historicalNdcTime[].status` (`String?`) | `historicalNdcConcept.historicalNdcTime[].status` (`String?`) | идентично |
| `historicalNdcConcept.historicalNdcTime[].rxcui` (`String?`) | `historicalNdcConcept.historicalNdcTime[].rxcui` (`String?`) | идентично |
| `historicalNdcConcept.historicalNdcTime[].ndcTime[].ndc[]` (`List<String>`) | `historicalNdcConcept.historicalNdcTime[].ndcTime[].ndc[]` (`List<String>?`) | идентично |
| `historicalNdcConcept.historicalNdcTime[].ndcTime[].startDate` (`String?`) | `historicalNdcConcept.historicalNdcTime[].ndcTime[].startDate` (`String?`) | идентично |
| `historicalNdcConcept.historicalNdcTime[].ndcTime[].endDate` (`String?`) | `historicalNdcConcept.historicalNdcTime[].ndcTime[].endDate` (`String?`) | идентично |

**Вывод:** RxNormHistoricalNDCsResponse.historicalNdcConcept.historicalNdcTime[].{status,rxcui,ndcTime[].{ndc,startDate,endDate}} -> HistoricalNdcConceptResponse.… (идентично) => замена верна: форма 1:1.

#### 5. FragmentRelated: `getAllRelatedInfo(rxcui)` → `getAllRelatedInfo(rxcui)`

Легаси DTO: `RxNormAllRelatedResponse`. Новое: `DefaultApi.getAllRelatedInfo` → `AllRelatedGroupResponse` (`/rxcui/{rxcui}/allrelated.json`). Живые образцы: `rxcui/161/allrelated.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `allRelatedGroup.conceptGroup[].tty` (`String?`) | `allRelatedGroup.conceptGroup[].tty` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | идентично |
| `allRelatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | `allRelatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | идентично |
| — | `allRelatedGroup.rxcui` (`String?`) | новое |
| — | `allRelatedGroup.conceptGroup[].conceptProperties[].genCard` (`String?`) | новое |
| — | `allRelatedGroup.conceptGroup[].conceptProperties[].psn` (`String?`) | новое |

**Вывод:** RxNormAllRelatedResponse.allRelatedGroup.conceptGroup[].{tty, conceptProperties[].*} -> AllRelatedGroupResponse.allRelatedGroup.… (идентично; новые rxcui, genCard, psn) => замена верна: все 8 листов совпадают, новые поля приложение игнорирует.

#### 6. FragmentProperties: `getRxConceptProperties(rxcui)` → `getRxConceptProperties(rxcui)`

Легаси DTO: `RxNormPropertiesResponse`. Новое: `DefaultApi.getRxConceptProperties` → `PropertiesResponse` (`/rxcui/{rxcui}/properties.json`). Живые образцы: `rxcui/161/properties.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `properties.rxcui` (`String?`) | `properties.rxcui` (`String?`) | идентично |
| `properties.name` (`String?`) | `properties.name` (`String?`) | идентично |
| `properties.synonym` (`String?`) | `properties.synonym` (`String?`) | идентично |
| `properties.tty` (`String?`) | `properties.tty` (`String?`) | идентично |
| `properties.language` (`String?`) | `properties.language` (`String?`) | идентично |
| `properties.suppress` (`String?`) | `properties.suppress` (`String?`) | идентично |
| `properties.umlscui` (`String?`) | `properties.umlscui` (`String?`) | идентично |

**Вывод:** RxNormPropertiesResponse.properties.{rxcui,name,synonym,tty,language,suppress,umlscui} -> PropertiesResponse.properties.{…} (идентично) => замена верна: форма 1:1.

#### 7. FragmentRelatedByType: `getTermTypes()` → `getTermTypes()`

Легаси DTO: `RxNormTermTypesResponse`. Новое: `DefaultApi.getTermTypes` → `TermTypeListResponse` (`/termtypes.json`). Живые образцы: `termtypes.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `termTypeList.termType[]` (`List<String>`) | `termTypeList.termType[]` (`List<String>?`) | идентично |

**Вывод:** RxNormTermTypesResponse.termTypeList.termType: List<String> -> TermTypeListResponse.termTypeList.termType: List<String> (идентично) => замена верна.

#### 8. FragmentRelatedByType: `getRelatedByType(rxcui, tty)` → `getRelated(rxcui, tty = …)`

Легаси DTO: `RxNormRelatedResponse`. Новое: `DefaultApi.getRelated` → `RelatedGroupResponse` (`/rxcui/{rxcui}/related.json`). Живые образцы: `rxcui/174742/related.json?tty=SBD+SBDF`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `relatedGroup.conceptGroup[].tty` (`String?`) | `relatedGroup.conceptGroup[].tty` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | идентично |
| — | `relatedGroup.rxcui` (`String?`) | новое |
| — | `relatedGroup.conceptGroup[].conceptProperties[].genCard` (`String?`) | новое |
| — | `relatedGroup.conceptGroup[].conceptProperties[].psn` (`String?`) | новое |

**Вывод:** RxNormRelatedResponse.relatedGroup.conceptGroup[].{tty, conceptProperties[].*} -> RelatedGroupResponse.relatedGroup.… (идентично; новые rxcui, genCard, psn) => замена верна: один эндпоинт related.json, фильтр tty передаётся параметром.

#### 9. FragmentRelatedByType: `getRelatedByRelationship(rxcui, rela)` → `getRelated(rxcui, rela = …)`

Легаси DTO: `RxNormRelatedResponse`. Новое: `DefaultApi.getRelated` → `RelatedGroupResponse` (`/rxcui/{rxcui}/related.json`). Живые образцы: `rxcui/174742/related.json?rela=tradename_of+has_precise_ingredient`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `relatedGroup.conceptGroup[].tty` (`String?`) | `relatedGroup.conceptGroup[].tty` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].rxcui` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].name` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].synonym` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].tty` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].language` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].suppress` (`String?`) | идентично |
| `relatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | `relatedGroup.conceptGroup[].conceptProperties[].umlscui` (`String?`) | идентично |
| — | `relatedGroup.rxcui` (`String?`) | новое |
| — | `relatedGroup.conceptGroup[].conceptProperties[].genCard` (`String?`) | новое |
| — | `relatedGroup.conceptGroup[].conceptProperties[].psn` (`String?`) | новое |

**Вывод:** RxNormRelatedResponse.relatedGroup.… -> RelatedGroupResponse.relatedGroup.… (идентично) => замена верна: тот же related.json, фильтр rela передаётся параметром.

#### 10. FragmentRelatedByType: `getRelaTypes()` → `getRelaTypes()`

Легаси DTO: `RxNormRelaTypesResponse`. Новое: `DefaultApi.getRelaTypes` → `RelationTypeListResponse` (`/relatypes.json`). Живые образцы: `relatypes.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `relaTypeList.relaType[]` (`List<String>`) | `relationTypeList.relationType[]` (`List<String>?`) | переименовано |

**Вывод:** relaTypeList.relaType: List<String> -> relationTypeList.relationType: List<String> (renamed wrapper, same data) => замена верна; старый DTO из-за неверных имён всегда был пуст, теперь список приходит (поведение изменилось в лучшую сторону).

#### 11. Fragment7: `historystatus(id, "RxNav")` → `getRxcuiHistoryStatus(rxcui)`

Легаси DTO: `Response7`. Новое: `DefaultApi.getRxcuiHistoryStatus` → `RxcuiStatusHistoryResponse` (`/rxcui/{rxcui}/historystatus.json`). Живые образцы: `rxcui/161/historystatus.json`, `rxcui/198440/historystatus.json`, `rxcui/1049221/historystatus.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `rxcuiStatusHistory.metaData.status` (`String?`) | `rxcuiStatusHistory.metaData.status` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.source` (`String?`) | `rxcuiStatusHistory.metaData.source` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.releaseStartDate` (`String?`) | `rxcuiStatusHistory.metaData.releaseStartDate` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.releaseEndDate` (`String?`) | `rxcuiStatusHistory.metaData.releaseEndDate` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.isCurrent` (`String?`) | `rxcuiStatusHistory.metaData.isCurrent` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.activeStartDate` (`String?`) | `rxcuiStatusHistory.metaData.activeStartDate` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.activeEndDate` (`String?`) | `rxcuiStatusHistory.metaData.activeEndDate` (`String?`) | идентично |
| `rxcuiStatusHistory.metaData.remappedDate` (`String?`) | `rxcuiStatusHistory.metaData.remappedDate` (`String?`) | идентично |
| `rxcuiStatusHistory.attributes.rxcui` (`String?`) | `rxcuiStatusHistory.attributes.rxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.attributes.name` (`String?`) | `rxcuiStatusHistory.attributes.name` (`String?`) | идентично |
| `rxcuiStatusHistory.attributes.tty` (`String?`) | `rxcuiStatusHistory.attributes.tty` (`String?`) | идентично |
| `rxcuiStatusHistory.attributes.isMultipleIngredient` (`String?`) | `rxcuiStatusHistory.attributes.isMultipleIngredient` (`String?`) | идентично |
| `rxcuiStatusHistory.attributes.isBranded` (`String?`) | `rxcuiStatusHistory.attributes.isBranded` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].baseRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].baseRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].baseName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].baseName` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].bossRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].bossRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].bossName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].bossName` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].activeIngredientRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].activeIngredientRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].activeIngredientName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].activeIngredientName` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].moietyRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].moietyRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].moietyName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].moietyName` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].numeratorValue` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].numeratorValue` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].numeratorUnit` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].numeratorUnit` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].denominatorValue` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].denominatorValue` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].denominatorUnit` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.ingredientAndStrength[].denominatorUnit` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.quantityFactor.quantityFactorValue` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.quantityFactor.quantityFactorValue` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.quantityFactor.quantityFactorUnit` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.quantityFactor.quantityFactorUnit` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.qualitativeDistinction` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.qualitativeDistinction` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.doseFormConcept[].doseFormRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.doseFormConcept[].doseFormRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.doseFormConcept[].doseFormName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.doseFormConcept[].doseFormName` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.doseFormGroupConcept[].doseFormGroupRxcui` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.doseFormGroupConcept[].doseFormGroupRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.definitionalFeatures.doseFormGroupConcept[].doseFormGroupName` (`String?`) | `rxcuiStatusHistory.definitionalFeatures.doseFormGroupConcept[].doseFormGroupName` (`String?`) | идентично |
| `rxcuiStatusHistory.pack.packAlias` (`String?`) | `rxcuiStatusHistory.pack.packAlias` (`String?`) | идентично |
| `rxcuiStatusHistory.pack.packConcept[].packRxcui` (`String?`) | `rxcuiStatusHistory.pack.packConcept[].packRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.pack.packConcept[].packName` (`String?`) | `rxcuiStatusHistory.pack.packConcept[].packName` (`String?`) | идентично |
| `rxcuiStatusHistory.pack.packConcept[].packNumber` (`String?`) | `rxcuiStatusHistory.pack.packConcept[].packNumber` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.ingredientConcept[].ingredientRxcui` (`String?`) | `rxcuiStatusHistory.derivedConcepts.ingredientConcept[].ingredientRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.ingredientConcept[].ingredientName` (`String?`) | `rxcuiStatusHistory.derivedConcepts.ingredientConcept[].ingredientName` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.qdFreeConcept[].qdFreeRxcui` (`String?`) | `rxcuiStatusHistory.derivedConcepts.qdFreeConcept.qdFreeRxcui` (`String?`) | тип изменён (List → объект) |
| `rxcuiStatusHistory.derivedConcepts.qdFreeConcept[].qdFreeName` (`String?`) | `rxcuiStatusHistory.derivedConcepts.qdFreeConcept.qdFreeName` (`String?`) | тип изменён (List → объект) |
| `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedRxcui` (`String?`) | `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedRxcui` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedName` (`String?`) | `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedName` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedTTY` (`String?`) | `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedTTY` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedActive` (`String?`) | `rxcuiStatusHistory.derivedConcepts.quantifiedConcept[].quantifiedActive` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedRxCui` (`String?`) | `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedRxCui` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedName` (`String?`) | `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedName` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedTTY` (`String?`) | `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedTTY` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedActive` (`String?`) | `rxcuiStatusHistory.derivedConcepts.remappedConcept[].remappedActive` (`String?`) | идентично |
| `rxcuiStatusHistory.derivedConcepts.scdConcept[].scdConceptRxcui` (`String?`) | `rxcuiStatusHistory.derivedConcepts.scdConcept.scdConceptRxcui` (`String?`) | тип изменён (List → объект) |
| `rxcuiStatusHistory.derivedConcepts.scdConcept[].scdConceptName` (`String?`) | `rxcuiStatusHistory.derivedConcepts.scdConcept.scdConceptName` (`String?`) | тип изменён (List → объект) |

**Вывод:** Response7.rxcuiStatusHistory.* (46 листов) -> RxcuiStatusHistoryResponse.rxcuiStatusHistory.* (идентично); derivedConcepts.scdConcept и qdFreeConcept: List<…> -> объект => замена верна: та же история статуса; Fragment7 переписан на объект scdConcept/qdFreeConcept (как отдаёт сервер, старый Gson-DTO на таких ответах падал).

#### 12. RxNorm.kt (подсказки): `getSpellingSuggestions(term)` → `getSpellingSuggestions(name)`

Легаси DTO: `RxNormSpellingSuggestionsResponse`. Новое: `DefaultApi.getSpellingSuggestions` → `SuggestionGroupResponse` (`/spellingsuggestions.json`). Живые образцы: `spellingsuggestions.json?name=ambienn`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `suggestionGroup.name` (`String?`) | `suggestionGroup.name` (`String?`) | идентично |
| `suggestionGroup.suggestionList.suggestion[]` (`List<String>`) | `suggestionGroup.suggestionList.suggestion[]` (`List<String>?`) | идентично |

**Вывод:** RxNormSpellingSuggestionsResponse.suggestionGroup.{name, suggestionList.suggestion} -> SuggestionGroupResponse.suggestionGroup.{…} (идентично) => замена верна: форма 1:1; при не-2xx теперь null вместо NPE.

### 7.2. RxClass / RxTerms (5 вызовов, перенесены сейчас)

#### 13. FragmentClass:77: `getClassByRxNormDrugId(rxcui)` → `getClassByRxcui(rxcui)`

Легаси DTO: `Response8`. Новое: `DefaultApi.getClassByRxcui` → `RxClassDrugInfoListResponse` (`/rxclass/class/byRxcui.json`). Живые образцы: `rxclass/class/byRxcui.json?rxcui=161`, `rxclass/class/byRxcui.json?rxcui=7052`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classId` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classId` (`String`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.className` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.className` (`String`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classType` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classType` (`String`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.rxcui` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.rxcui` (`String?`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.name` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.name` (`String?`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.tty` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.tty` (`String?`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].rela` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rela` (`String`) | идентично |
| `rxclassDrugInfoList.rxclassDrugInfo[].relaSource` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].relaSource` (`String`) | идентично |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classUrl` (`String?`) | новое |

**Вывод:** Response8.rxclassDrugInfoList.rxclassDrugInfo[].{rxclassMinConceptItem.{classId,className,classType}, minConcept.{rxcui,name,tty}, rela, relaSource} -> RxClassDrugInfoListResponse.… (идентично; classId/className/classType/rela/relaSource теперь non-null String; новое classUrl) => замена верна: форма 1:1; FragmentClass дополнительно сохраняет relaSource/rela класса в ClassCardItem для AnalogsActivity.

#### 14. Fragment5:75: `getAllRxTermInfo(rxcui)` → `getAllRxTermInfo(rxcui)`

Легаси DTO: `Response5`. Новое: `DefaultApi.getAllRxTermInfo` → `RxTermsPropertiesResponse` (`/RxTerms/rxcui/{rxcui}/allinfo.json`). Живые образцы: `RxTerms/rxcui/198440/allinfo.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `rxtermsProperties.brandName` (`String?`) | `rxtermsProperties.brandName` (`String`) | идентично |
| `rxtermsProperties.displayName` (`String?`) | `rxtermsProperties.displayName` (`String`) | идентично |
| `rxtermsProperties.synonym` (`String?`) | `rxtermsProperties.synonym` (`String`) | идентично |
| `rxtermsProperties.fullName` (`String?`) | `rxtermsProperties.fullName` (`String`) | идентично |
| `rxtermsProperties.fullGenericName` (`String?`) | `rxtermsProperties.fullGenericName` (`String`) | идентично |
| `rxtermsProperties.strength` (`String?`) | `rxtermsProperties.strength` (`String`) | идентично |
| `rxtermsProperties.rxtermsDoseForm` (`String?`) | `rxtermsProperties.rxtermsDoseForm` (`String`) | идентично |
| `rxtermsProperties.route` (`String?`) | `rxtermsProperties.route` (`String`) | идентично |
| `rxtermsProperties.termType` (`String?`) | `rxtermsProperties.termType` (`String`) | идентично |
| `rxtermsProperties.rxcui` (`String?`) | `rxtermsProperties.rxcui` (`String`) | идентично |
| `rxtermsProperties.genericRxcui` (`String?`) | `rxtermsProperties.genericRxcui` (`String`) | идентично |
| `rxtermsProperties.rxnormDoseForm` (`String?`) | `rxtermsProperties.rxnormDoseForm` (`String`) | идентично |
| `rxtermsProperties.suppress` (`String?`) | `rxtermsProperties.suppress` (`String`) | идентично |

**Вывод:** Response5.rxtermsProperties.{13 полей}: String? -> RxTermsPropertiesResponse.rxtermsProperties.{те же 13}: String (идентично, non-null) => замена верна: форма 1:1; `?: ""` убран, т.к. поля non-null; ответ {} -> rxtermsProperties = null -> EmptyViewObj (как раньше), 404 -> null (как раньше).

#### 15. AnalogsActivity:49: `getSourcesOfDrugClassRelations(classId, classType)` → `getRelaSources()`

Легаси DTO: `RxClassSourcesResponse`. Новое: `DefaultApi.getRelaSources` → `RelaSourceListResponse` (`/rxclass/relaSources.json`). Живые образцы: `rxclass/relaSources.json`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `sourcesOfDrugClassRelationsList.source[]` (`List<String>`) | `relaSourceList.relaSourceName[]` (`List<String>`) | переименовано |

**Вывод:** sourcesOfDrugClassRelationsList.source: List<String> -> relaSourceList.relaSourceName: List<String> (renamed wrapper, same data) => замена верна: тот же список источников связей «препарат–класс»; старый путь class/sourcesOfDrugClassRelations.json не существует, параметры classId/classType API не принимает.

#### 16. AnalogsActivity:64: `getClassMembersWithSource(classId, relaSource)` → `getClassMembers(classId, relaSource, rela)`

Легаси DTO: `Response8`. Новое: `DefaultApi.getClassMembers` → `DrugMemberGroupResponse` (`/rxclass/classMembers.json`). Живые образцы: `rxclass/classMembers.json?classId=CN103&relaSource=VA&rela=has_VAclass`, `rxclass/classMembers.json?classId=A12CA&relaSource=ATC`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classId` (`String?`) | — | отсутствует |
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.className` (`String?`) | — | отсутствует |
| `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classType` (`String?`) | — | отсутствует |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.rxcui` (`String?`) | `drugMemberGroup.drugMember[].minConcept.rxcui` (`String?`) | переименовано |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.name` (`String?`) | `drugMemberGroup.drugMember[].minConcept.name` (`String?`) | переименовано |
| `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.tty` (`String?`) | `drugMemberGroup.drugMember[].minConcept.tty` (`String?`) | переименовано |
| `rxclassDrugInfoList.rxclassDrugInfo[].rela` (`String?`) | — | отсутствует |
| `rxclassDrugInfoList.rxclassDrugInfo[].relaSource` (`String?`) | — | отсутствует |
| — | `drugMemberGroup.drugMember[].nodeAttr[].attrName` (`String`) | новое |
| — | `drugMemberGroup.drugMember[].nodeAttr[].attrValue` (`String`) | новое |

**Вывод:** rxclassDrugInfoList.rxclassDrugInfo[].minConcept.{rxcui,name,tty} -> drugMemberGroup.drugMember[].minConcept.{rxcui,name,tty} (renamed wrapper, same data); rxclassMinConceptItem.*, rela, relaSource отсутствуют; новое nodeAttr[] => замена верна: члены класса — те же minConcept; отсутствующие поля — это параметры запроса (classId, relaSource, rela), приложение их знает. Обязателен rela для не-ATC источников (VA: has_VAclass) — берётся родной rela класса из FragmentClass.

#### 17. AnalogsActivity:100 (fallback): `byRxcui(classId)` → `getClassByRxcui(rxcui)`

Легаси DTO: `RxClassByRxcuiResponse`. Новое: `DefaultApi.getClassByRxcui` → `RxClassDrugInfoListResponse` (`/rxclass/class/byRxcui.json`). Живые образцы: `rxclass/class/byRxcui.json?rxcui=7052`.

| Легаси DTO: поле (тип) | Live / сгенерированная модель: поле (тип) | Статус |
|---|---|---|
| `rxclassMinConceptList.rxclassMinConcept[].classId` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classId` (`String`) | переименовано |
| `rxclassMinConceptList.rxclassMinConcept[].className` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.className` (`String`) | переименовано |
| `rxclassMinConceptList.rxclassMinConcept[].classType` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classType` (`String`) | переименовано |
| `rxclassMinConceptList.rxclassMinConcept[].rxcui` (`String?`) | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.rxcui` (`String?`) | переименовано |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.name` (`String?`) | новое |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].minConcept.tty` (`String?`) | новое |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].rxclassMinConceptItem.classUrl` (`String?`) | новое |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].rela` (`String`) | новое |
| — | `rxclassDrugInfoList.rxclassDrugInfo[].relaSource` (`String`) | новое |

**Вывод:** rxclassMinConceptList.rxclassMinConcept[].{classId,className,classType,rxcui} -> rxclassDrugInfoList.rxclassDrugInfo[].{rxclassMinConceptItem.{classId,className,classType}, minConcept.rxcui} (renamed wrapper, same data) => замена верна: тот же class/byRxcui.json; плюс исправлен баг — раньше передавался classId вместо rxcui (HTTP 400), теперь передаётся rxcui препарата; классы дедуплицируются по classId.

### 7.3. Сводка

Всего листовых полей в таблицах: идентично 112, переименовано 9, тип изменён 4, отсутствует 7, новое 18.
- 1–9, 12, 13, 14: структура 1:1 (только новые поля) — замена механическая.
- 10 (relatypes), 15 (relaSources), 17 (byRxcui): те же данные под другой обёрткой — renamed wrapper, same data.
- 11 (historystatus): scdConcept/qdFreeConcept — объект вместо списка; код Fragment7 адаптирован.
- 16 (classMembers): листья minConcept те же; поля класса/связи отсутствуют, потому что это параметры запроса; дополнительно нужен rela для не-ATC источников.
- 2 (allProperties): propSource/propType сервер не отдаёт, приложение их не использует.
