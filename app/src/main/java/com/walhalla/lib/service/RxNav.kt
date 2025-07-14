package com.walhalla.lib.service

import com.google.gson.JsonObject
import com.walhalla.lib.datamodel.common.response.*
import com.walhalla.lib.datamodel.rxclass.response.*
import com.walhalla.lib.datamodel.rxterms.response.*
import com.walhalla.lib.datamodel.prescribable.response.*
import com.walhalla.lib.datamodel.rxnorm.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

// --- Типобезопасный idType для RxNav/RxClass/Prescribable API ---
sealed class RxIdType(val value: String) {
    object AMPID : RxIdType("AMPID")
    object ANADA : RxIdType("ANADA")
    object ANDA : RxIdType("ANDA")
    object ATC : RxIdType("ATC")
    object BLA : RxIdType("BLA")
    object CVX : RxIdType("CVX")
    object DRUGBANK : RxIdType("DRUGBANK")
    object GCN_SEQNO : RxIdType("GCN_SEQNO")
    object GFC : RxIdType("GFC")
    object HCPCS : RxIdType("HCPCS")
    object HIC_SEQN : RxIdType("HIC_SEQN")
    object MMSL_CODE : RxIdType("MMSL_CODE")
    object NADA : RxIdType("NADA")
    object NDA : RxIdType("NDA")
    object NDC : RxIdType("NDC")
    object NHRIC : RxIdType("NHRIC")
    object SNOMEDCT : RxIdType("SNOMEDCT")
    object SPL_SET_ID : RxIdType("SPL_SET_ID")
    object UNII_CODE : RxIdType("UNII_CODE")
    object USP : RxIdType("USP")
    object VUID : RxIdType("VUID")
}

// --- RxNorm API ---
interface RxnormApi {
    // Поиск RxCUI по имени
    @GET("rxcui.json")
    fun searchRxcui(
        @Query("name") value: String,
        @Query("search") value2: Int,
        @Query("allsrc") value3: Int
    ): Call<Response0>

    // Получить свойства RxCUI
    @GET("rxcui/{id}/properties.json")
    fun searchRxcuiProp(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response1>

    // Получить все свойства RxCUI
    @GET("rxcui/{id}/allProperties.json")
    fun allProperties(
        @Path("id") id: String,
        @Query("caller") caller: String,
        @Query("prop") prop: String?
    ): Call<Response4>

    // Получить историю RxCUI
    @GET("rxcui/{id}/historystatus.json")
    fun historystatus(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response7>

    // Получить все исторические NDC
    @GET("rxcui/{id}/allhistoricalndcs.json")
    fun allhistoricalndcs(
        @Path("id") id: String,
        @Query("caller") caller: String?,
        @Query("history") history: Int
    ): Call<RxNormHistoricalNDCsResponse>

    // Получить связанные концепты
    @GET("rxcui/{id}/allrelatedextension.json")
    fun allrelatedextension(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response2>

    // Получить взаимодействия
    @GET("interaction/interaction.json")
    fun interaction(
        @Query("rxcui") id: String,
        @Query("caller") caller: String?,
        @Query("sources") sources: String?
    ): Call<Response6>

    // Получить взаимодействия (альтернативная сигнатура)
    @GET("interaction/interaction.json")
    fun interactionWithSources(
        @Query("rxcui") id: String,
        @Query("sources") sources: String
    ): Call<Response6>

    // Получить NDC свойства
    @GET("ndcproperties.json")
    fun ndcproperties(
        @Query("id") id: String,
        @Query("caller") caller: String
    ): Call<RxNormNDCPropertiesResponse>

    // Поиск препаратов
    @Headers("Content-Type: application/json")
    @GET("drugs.json")
    fun searchDrugsBase(
        @Query("name") value: String
    ): Call<RxNormDrugsResponse>

    // Фильтрация по свойству
    @GET("rxcui/rxcui/filter.json")
    fun filterByProperty(
        @Query("propName") propName: String,
        @Query("propValues") propValues: String
    ): Call<RxNormFilterResponse>

    // --- RxNorm API: дополнительные методы ---

    // Получить концепты по статусу
    @GET("allstatus")
    fun getAllConceptsByStatus(): Call<RxNormAllStatusResponse>

    // Получить концепты по типу термина
    @GET("allconcepts")
    fun getAllConceptsByTTY(): Call<RxNormAllConceptsResponse>

    // Получить все NDC по статусу
    @GET("allNDCstatus")
    fun getAllNDCsByStatus(): Call<RxNormAllNDCStatusResponse>

    // Категории свойств RxNav
    @GET("propCategories")
    fun getPropCategories(): Call<RxNormPropCategoriesResponse>

    // Имена свойств RxNav
    @GET("propnames")
    fun getPropNames(): Call<RxNormPropNamesResponse>

    // Концепты, связанные по reformulation_of
    @GET("reformulationConcepts")
    fun getReformulationConcepts(): Call<RxNormReformulationConceptsResponse>

    // Пути отношений
    @GET("relapaths")
    fun getRelaPaths(): Call<RxNormRelaPathsResponse>

    // Типы отношений RxNorm
    @GET("relatypes")
    fun getRelaTypes(): Call<RxNormRelaTypesResponse>

    // Источники словарей
    @GET("sourcetypes")
    fun getSourceTypes(): Call<RxNormSourceTypesResponse>

    // Подсказки по написанию
    @GET("spellingsuggestions")
    fun getSpellingSuggestions(@Query("term") term: String): Call<RxNormSpellingSuggestionsResponse>

    // Типы терминов
    @GET("termtypes")
    fun getTermTypes(): Call<RxNormTermTypesResponse>

    // Версия RxNorm
    @GET("version")
    fun getRxNormVersion(): Call<RxNormVersionResponse>

    // Получить NDCs, связанные с концептом
    @GET("rxcui/{rxcui}/ndcs")
    fun getNDCs(@Path("rxcui") rxcui: String): Call<RxNormNDCsResponse>

    // Получить все связанные концепты
    @GET("rxcui/{rxcui}/allrelated")
    fun getAllRelatedInfo(@Path("rxcui") rxcui: String): Call<RxNormAllRelatedResponse>

    // Получить свойства концепта
    @GET("rxcui/{rxcui}/properties")
    fun getRxConceptProperties(@Path("rxcui") rxcui: String): Call<RxNormPropertiesResponse>

    // Получить имя концепта
    @GET("rxcui/{rxcui}")
    fun getRxNormName(@Path("rxcui") rxcui: String): Call<RxNormNameResponse>

    // Получить свойство концепта
    @GET("rxcui/{rxcui}/property")
    fun getRxProperty(@Path("rxcui") rxcui: String, @Query("propName") propName: String): Call<RxNormPropertyResponse>

    // Получить статус/историю концепта
    @GET("rxcui/{rxcui}/historystatus")
    fun getRxcuiHistoryStatus(@Path("rxcui") rxcui: String): Call<Response7>

    // Получить связанные концепты по relationship
    @GET("rxcui/{rxcui}/related")
    fun getRelatedByRelationship(@Path("rxcui") rxcui: String, @Query("rela") rela: String): Call<RxNormRelatedResponse>

    // Получить связанные концепты по типу
    @GET("rxcui/{rxcui}/related")
    fun getRelatedByType(@Path("rxcui") rxcui: String, @Query("tty") tty: String): Call<RxNormRelatedResponse>

    // Получить проприетарную информацию
    @GET("rxcui/{rxcui}/proprietary")
    fun getProprietaryInformation(@Path("rxcui") rxcui: String): Call<RxNormProprietaryResponse>

    // --- RxClass API методы в RxNorm ---

    // Поиск класса по имени
    @GET("rxclass/class/byName.json")
    fun findClassByName(
        @Query("className") className: String,
        @Query("classTypes") classTypes: String
    ): Call<Response8>

    // Поиск классов по classId
    @GET("rxclass/class/byId.json")
    fun findClassesById(
        @Query("classId") classId: String
    ): Call<RxClassResponse>

    // Поиск похожих классов по классу
    @GET("rxclass/class/similarByClass.json")
    fun findSimilarClassesByClass(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<Response8>

    // Поиск похожих классов по списку препаратов
    @GET("rxclass/class/similarByDrugList.json")
    fun findSimilarClassesByDrugList(
        @Query("rxcuis") rxcuis: String
    ): Call<Response8>

    // Получить все классы
    @GET("rxclass/class/allClasses.json")
    fun getAllClasses(
        @Query("classTypes") classTypes: String
    ): Call<Response8>

    // Получить класс по RxNorm drug id
    @GET("rxclass/class/classByRxNormDrugId.json")
    fun getClassByRxNormDrugId(
        @Query("rxcui") rxcui: String
    ): Call<Response8>

    // Получить класс по RxNorm drug name
    @GET("rxclass/class/classByDrugName.json")
    fun getClassByRxNormDrugName(
        @Query("drugName") drugName: String
    ): Call<Response8>

    // Получить контексты классов
    @GET("rxclass/class/classContexts.json")
    fun getClassContexts(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassContextsResponse>

    // Получить граф класса по источнику
    @GET("rxclass/class/classGraphBySource.json")
    fun getClassGraphBySource(
        @Query("classId") classId: String,
        @Query("classType") classType: String,
        @Query("source") source: String
    ): Call<RxClassGraphResponse>

    // Получить участников класса
    @GET("rxclass/class/classMembers.json")
    fun getClassMembers(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<Response8>

    // Получить дерево классов
    @GET("rxclass/class/classTree.json")
    fun getClassTree(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassTreeResponse>

    // Получить версию источника отношений
    @GET("rxclass/class/relaSourceVersion.json")
    fun getRelaSourceVersion(
        @Query("source") source: String
    ): Call<RxClassRelaSourceVersionResponse>

    // Получить отношения
    @GET("rxclass/class/relas.json")
    fun getRelas(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassRelasResponse>

    // Получить информацию о схожести
    @GET("rxclass/class/similarityInformation.json")
    fun getSimilarityInformation(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassSimilarityResponse>

    // Получить источники связей классов
    @GET("rxclass/class/sourcesOfDrugClassRelations.json")
    fun getSourcesOfDrugClassRelations(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassSourcesResponse>

    // Получить подсказки по написанию
    @GET("rxclass/class/spellingsuggestions.json")
    fun getSpellingSuggestionsRxClass(
        @Query("term") term: String
    ): Call<RxClassSpellingSuggestionsResponse>

    // Получить типы классов
    @GET("rxclass/classTypes.json")
    fun getClassTypes(): Call<RxClassTypesResponse>

    // Получить классы по RxCUI
    @GET("rxclass/class/byRxcui.json")
    fun byRxcui(
        @Query("rxcui") id: String?
    ): Call<RxClassByRxcuiResponse>
}

// --- RxTerms API ---
interface RxTermsApi {
    // Получить все концепты RxTerms
    @GET("RxTerms/allconcepts.json")
    fun getAllConcepts(): Call<RxTermsAllConceptsResponse>

    // Получить всю информацию по RxTerm
    @GET("RxTerms/rxcui/{rxcui}/allinfo.json")
    fun getAllRxTermInfo(@Path("rxcui") rxcui: String): Call<Response5>

    // Получить display name по RxTerm
    @GET("RxTerms/rxcui/{rxcui}/displayname.json")
    fun getRxTermDisplayName(@Path("rxcui") rxcui: String): Call<RxTermsDisplayNameResponse>

    // Получить версию RxTerms
    @GET("RxTerms/version.json")
    fun getRxTermsVersion(): Call<RxTermsVersionResponse>
}

// --- Prescribable RxNorm API ---
interface PrescribableRxNormApi {
    // Фильтрация по свойству
    @GET("prescribable/rxcui/{rxcui}/filter.json")
    fun filterByProperty(
        @Path("rxcui") rxcui: String, 
        @Query("propName") propName: String, 
        @Query("propValues") propValues: String
    ): Call<PrescribableFilterResponse>

    // Поиск rxcui по id (типобезопасно)
    @GET("prescribable/rxcui.json")
    fun findRxcuiById(@Query("idtype") idtype: String, @Query("id") id: String): Call<IdGroup>

    // Типобезопасная обёртка
    fun findRxcuiByIdSafe(idType: RxIdType, id: String): Call<IdGroup> =
        findRxcuiById(idType.value, id)

    // Поиск rxcui по строке
    @GET("prescribable/rxcui.json")
    fun findRxcuiByString(@Query("name") name: String): Call<IdGroup>

    // Получить все концепты по TTY
    @GET("prescribable/allconcepts.json")
    fun getAllConceptsByTTY(@Query("tty") tty: String): Call<PrescribableAllConceptsResponse>

    // Получить все свойства
    @GET("prescribable/rxcui/{rxcui}/allProperties.json")
    fun getAllProperties(@Path("rxcui") rxcui: String): Call<PrescribableAllPropertiesResponse>

    // Получить всю связанную информацию
    @GET("prescribable/rxcui/{rxcui}/allrelated.json")
    fun getAllRelatedInfo(@Path("rxcui") rxcui: String): Call<PrescribableAllRelatedResponse>

    // Получить approximate match
    @GET("prescribable/approximateTerm.json")
    fun getApproximateMatch(@Query("term") term: String): Call<PrescribableApproximateMatchResponse>

    // Получить display terms
    @GET("prescribable/displaynames.json")
    fun getDisplayTerms(): Call<PrescribableDisplayTermsResponse>

    // Получить drugs
    @GET("prescribable/drugs.json")
    fun getDrugs(@Query("name") name: String): Call<PrescribableDrugsResponse>

    // Получить id types
    @GET("prescribable/idtypes.json")
    fun getIdTypes(): Call<PrescribableIdTypesResponse>

    // Получить бренды по ингредиентам
    @GET("prescribable/brands.json")
    fun getMultiIngredBrand(@Query("ingreds") ingreds: String): Call<PrescribableMultiIngredBrandResponse>

    // Получить NDCs
    @GET("prescribable/rxcui/{rxcui}/ndcs.json")
    fun getNDCs(@Path("rxcui") rxcui: String): Call<PrescribableNDCsResponse>

    // Получить prop categories
    @GET("prescribable/propCategories.json")
    fun getPropCategories(): Call<PrescribablePropCategoriesResponse>

    // Получить prop names
    @GET("prescribable/propnames.json")
    fun getPropNames(): Call<PrescribablePropNamesResponse>

    // Получить rela paths
    @GET("prescribable/relapaths.json")
    fun getRelaPaths(): Call<PrescribableRelaPathsResponse>

    // Получить rela types
    @GET("prescribable/relatypes.json")
    fun getRelaTypes(): Call<PrescribableRelaTypesResponse>

    // Получить related by relationship
    @GET("prescribable/rxcui/{rxcui}/related.json")
    fun getRelatedByRelationship(@Path("rxcui") rxcui: String, @Query("rela") rela: String): Call<PrescribableRelatedResponse>

    // Получить related by type
    @GET("prescribable/rxcui/{rxcui}/related.json")
    fun getRelatedByType(@Path("rxcui") rxcui: String, @Query("tty") tty: String): Call<PrescribableRelatedResponse>

    // Получить свойства концепта
    @GET("prescribable/rxcui/{rxcui}/properties.json")
    fun getRxConceptProperties(@Path("rxcui") rxcui: String): Call<PrescribablePropertiesResponse>

    // Получить имя концепта
    @GET("prescribable/rxcui/{rxcui}.json")
    fun getRxNormName(@Path("rxcui") rxcui: String): Call<PrescribableNameResponse>

    // Получить свойство концепта
    @GET("prescribable/rxcui/{rxcui}/property.json")
    fun getRxProperty(@Path("rxcui") rxcui: String, @Query("propName") propName: String): Call<PrescribablePropertyResponse>

    // Получить источники
    @GET("prescribable/sourcetypes.json")
    fun getSourceTypes(): Call<PrescribableSourceTypesResponse>

    // Получить spelling suggestions
    @GET("prescribable/spellingsuggestions.json")
    fun getSpellingSuggestions(@Query("term") term: String): Call<PrescribableSpellingSuggestionsResponse>

    // Получить term types
    @GET("prescribable/termtypes.json")
    fun getTermTypes(): Call<PrescribableTermTypesResponse>

    // Получить версию
    @GET("prescribable/version.json")
    fun getRxNormVersion(): Call<PrescribableVersionResponse>
}

// --- RxClass API ---
interface RxClassApi {
    // Поиск класса по имени
    @GET("rxclass/class/byName.json")
    fun findClassByName(
        @Query("className") className: String,
        @Query("classTypes") classTypes: String
    ): Call<Response8>

    // Поиск классов по classId (правильная сигнатура согласно документации)
    @GET("rxclass/class/byId.json")
    fun findClassesById(
        @Query("classId") classId: String
    ): Call<RxClassResponse>

    // Поиск похожих классов по классу
    @GET("rxclass/class/similarByClass.json")
    fun findSimilarClassesByClass(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<Response8>

    // Поиск похожих классов по списку препаратов
    @GET("rxclass/class/similarByDrugList.json")
    fun findSimilarClassesByDrugList(
        @Query("rxcuis") rxcuis: String
    ): Call<Response8>

    // Получить все классы
    @GET("rxclass/class/allClasses.json")
    fun getAllClasses(
        @Query("classTypes") classTypes: String
    ): Call<Response8>

    // Получить класс по RxNorm drug id
    @GET("rxclass/class/classByRxNormDrugId.json")
    fun getClassByRxNormDrugId(
        @Query("rxcui") rxcui: String
    ): Call<Response8>

    // Получить класс по RxNorm drug name
    @GET("rxclass/class/classByDrugName.json")
    fun getClassByRxNormDrugName(
        @Query("drugName") drugName: String
    ): Call<Response8>

    // Получить контексты классов
    @GET("rxclass/class/classContexts.json")
    fun getClassContexts(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassContextsResponse>

    // Получить граф класса по источнику
    @GET("rxclass/class/classGraphBySource.json")
    fun getClassGraphBySource(
        @Query("classId") classId: String,
        @Query("classType") classType: String,
        @Query("source") source: String
    ): Call<RxClassGraphResponse>

    // Получить участников класса
    @GET("rxclass/class/classMembers.json")
    fun getClassMembers(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<Response8>

    // Получить дерево классов
    @GET("rxclass/class/classTree.json")
    fun getClassTree(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassTreeResponse>

    // Получить версию источника отношений
    @GET("rxclass/class/relaSourceVersion.json")
    fun getRelaSourceVersion(
        @Query("source") source: String
    ): Call<RxClassRelaSourceVersionResponse>

    // Получить отношения
    @GET("rxclass/class/relas.json")
    fun getRelas(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassRelasResponse>

    // Получить информацию о схожести
    @GET("rxclass/class/similarityInformation.json")
    fun getSimilarityInformation(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassSimilarityResponse>

    // Получить источники связей классов
    @GET("rxclass/class/sourcesOfDrugClassRelations.json")
    fun getSourcesOfDrugClassRelations(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<RxClassSourcesResponse>

    // Получить подсказки по написанию
    @GET("rxclass/class/spellingsuggestions.json")
    fun getSpellingSuggestionsRxClass(
        @Query("term") term: String
    ): Call<RxClassSpellingSuggestionsResponse>

    // Получить типы классов
    @GET("rxclass/classTypes.json")
    fun getClassTypes(): Call<RxClassTypesResponse>
}