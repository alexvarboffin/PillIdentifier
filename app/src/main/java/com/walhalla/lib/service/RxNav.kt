package com.walhalla.lib.service

import com.google.gson.JsonObject
import com.walhalla.lib.datamodel.pkg0.Response0
import com.walhalla.lib.datamodel.pkg1.Response1
import com.walhalla.lib.datamodel.pkg2.Response2
import com.walhalla.lib.datamodel.pkg4.Response4
import com.walhalla.lib.datamodel.pkg5.Response5
import com.walhalla.lib.datamodel.pkg6.Response6
import com.walhalla.lib.datamodel.pkg7.Response7
import com.walhalla.lib.datamodel.pkg0.IdGroup
import com.walhalla.lib.datamodel.pkg8.Response8
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RxnormApi {
    //11
    @GET("interaction/interaction.json")
    fun interaction(
        @Query("rxcui") id: String,
        @Query("caller") caller: String?,
        @Query("sources") sources: String?
    ): Call<JsonObject>

    //10
    @GET("rxcui/{id}/allhistoricalndcs.json")
    fun allhistoricalndcs(
        @Path("id") id: String,
        @Query("caller") caller: String?,
        @Query("history") history: Int
    ): Call<JsonObject>

    //9
    @GET("rxclass/class/byRxcui.json")
    fun byRxcui(
        @Query("rxcui") id: String? //@Query("caller") String caller

    ): Call<JsonObject>

    //8
    @GET("rxcui/{id}/historystatus.json")
    fun historystatus(
        @Path("id") id: String,
        @Query("caller") caller: String

    ): Call<Response7>

    //7
    @GET("interaction/interaction.json")
    fun interaction(
        @Query("rxcui") id: String,
        @Query("sources") sources: String

    ): Call<Response6>

    //[5]
    //    brandName	""
    //    displayName	"Acetaminophen (Oral Pill)"
    //    synonym	"APAP"
    //    fullName	"acetaminophen 500 MG Oral Tablet"
    //    fullGenericName	"acetaminophen 500 MG Oral Tablet"
    //    strength	"500 mg"
    //    rxtermsDoseForm	"Tab"
    //    route	"Oral Pill"
    //    termType	"SCD"
    //    rxcui	"198440"
    //    genericRxcui	""
    //    rxnormDoseForm	"Oral Tablet"
    //    suppress	""
    @GET("RxTerms/rxcui/{id}/allinfo.json")
    fun getAllRxTermInfo(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response5>

    //5
    @GET("ndcproperties.json")
    fun ndcproperties(
        @Query("id") id: String,
        @Query("caller") caller: String

    ): Call<JsonObject>


    //Attributes
    //Codes
    //Names
    //Sources
    //ALL
    //4 [tab3]
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allProperties.json?caller=RxNav&prop=Attributes
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allProperties.json?caller=RxNav&prop=Sources
    @GET("rxcui/{id}/allProperties.json")
    fun allProperties(
        @Path("id") id: String,
        @Query("caller") caller: String,
        @Query("prop") prop: String?

    ): Call<Response4>

    //[tab3]
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allrelatedextension.json?caller=RxNav
    @GET("rxcui/{id}/allrelatedextension.json")
    fun allrelatedextension(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response2>


    //&srclist=yourSources&
    @GET("rxcui.json")
    fun searchRxcui(
        @Query("name") value: String,  //lidocaine+hydrochloride
        @Query("search") value2: Int,  //0or1or2
        @Query("allsrc") value3: Int //0or1
    ): Call<Response0>

    //    properties
    //    rxcui	"198449"
    //    name	"acetaminophen 500 MG / pamabrom 25 MG Oral Tablet"
    //    synonym	"APAP 500 MG / pamabrom 25 MG Oral Tablet"
    //    tty	"SCD"
    //    language	"ENG"
    //    suppress	"N"
    //    umlscui	""
    @GET("rxcui/{id}/properties.json")
    fun searchRxcuiProp(
        @Path("id") id: String,
        @Query("caller") caller: String
    ): Call<Response1>

    @Headers("Content-Type: application/json")
    @GET("drugs.json")
    fun searchDrugsBase(
        @Query("name") value: String
    ): Call<JsonObject>


    // --- RxClass API (реальные endpoint'ы вместо заглушек) ---

    // Поиск класса по имени
    @GET("rxclass/class/byName.json")
    fun findClassByName(
        @Query("className") className: String,
        @Query("classTypes") classTypes: String
    ): Call<JsonObject>

    // Поиск классов по id
    @GET("rxclass/class/byId.json")
    fun findClassesById(
        @Query("id") id: String,
        @Query("idType") idType: String,
        @Query("classTypes") classTypes: String
    ): Call<JsonObject>

    // Поиск похожих классов по классу
    @GET("rxclass/class/similarByClass.json")
    fun findSimilarClassesByClass(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Поиск похожих классов по списку препаратов
    @GET("rxclass/class/similarByDrugList.json")
    fun findSimilarClassesByDrugList(
        @Query("rxcuis") rxcuis: String
    ): Call<JsonObject>

    // Получить все классы
    @GET("rxclass/class/allClasses.json")
    fun getAllClasses(
        @Query("classTypes") classTypes: String
    ): Call<JsonObject>

    // Получить класс по RxNorm drug id
    @GET("rxclass/class/classByRxNormDrugId.json")
    fun getClassByRxNormDrugId(
        @Query("rxcui") rxcui: String
    ): Call<JsonObject>

    // Получить класс по RxNorm drug name
    @GET("rxclass/class/classByDrugName.json")
    fun getClassByRxNormDrugName(
        @Query("drugName") drugName: String
    ): Call<JsonObject>

    // Получить контексты классов
    @GET("rxclass/class/classContexts.json")
    fun getClassContexts(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить граф класса по источнику
    @GET("rxclass/class/classGraphBySource.json")
    fun getClassGraphBySource(
        @Query("classId") classId: String,
        @Query("classType") classType: String,
        @Query("source") source: String
    ): Call<JsonObject>

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
    ): Call<JsonObject>

    // Получить версию источника отношений
    @GET("rxclass/class/relaSourceVersion.json")
    fun getRelaSourceVersion(
        @Query("source") source: String
    ): Call<JsonObject>

    // Получить отношения
    @GET("rxclass/class/relas.json")
    fun getRelas(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить информацию о схожести
    @GET("rxclass/class/similarityInformation.json")
    fun getSimilarityInformation(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить источники связей классов
    @GET("rxclass/class/sourcesOfDrugClassRelations.json")
    fun getSourcesOfDrugClassRelations(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить подсказки по написанию
    @GET("rxclass/class/spellingsuggestions.json")
    fun getSpellingSuggestionsRxClass(
        @Query("term") term: String
    ): Call<JsonObject>

    // Получить типы классов
    @GET("rxclass/classTypes.json")
    fun getClassTypes(): Call<JsonObject>


    @GET("/rxcui/rxcui/filter.json")
    fun filterByProperty(
        @Query("propName") propName: String,
        @Query("propValues") propValues: String

    ): Call<JsonObject>

    // --- RxNorm API: недостающие методы ---

    // Получить концепты по статусу
    @GET("allstatus")
    fun getAllConceptsByStatus(): Call<JsonObject>

    // Получить концепты по типу термина
    @GET("allconcepts")
    fun getAllConceptsByTTY(): Call<JsonObject>

    // Получить все NDC по статусу
    @GET("allNDCstatus")
    fun getAllNDCsByStatus(): Call<JsonObject>

    // Категории свойств RxNav
    @GET("propCategories")
    fun getPropCategories(): Call<JsonObject>

    // Имена свойств RxNav
    @GET("propnames")
    fun getPropNames(): Call<JsonObject>

    // Концепты, связанные по reformulation_of
    @GET("reformulationConcepts")
    fun getReformulationConcepts(): Call<JsonObject>

    // Пути отношений
    @GET("relapaths")
    fun getRelaPaths(): Call<JsonObject>

    // Типы отношений RxNorm
    @GET("relatypes")
    fun getRelaTypes(): Call<JsonObject>

    // Источники словарей
    @GET("sourcetypes")
    fun getSourceTypes(): Call<JsonObject>

    // Подсказки по написанию
    @GET("spellingsuggestions")
    fun getSpellingSuggestions(@Query("term") term: String): Call<JsonObject>

    // Типы терминов
    @GET("termtypes")
    fun getTermTypes(): Call<JsonObject>

    // Версия RxNorm
    @GET("version")
    fun getRxNormVersion(): Call<JsonObject>

    // Получить NDCs, связанные с концептом
    @GET("rxcui/{rxcui}/ndcs")
    fun getNDCs(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить все связанные концепты
    @GET("rxcui/{rxcui}/allrelated")
    fun getAllRelatedInfo(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить свойства концепта
    @GET("rxcui/{rxcui}/properties")
    fun getRxConceptProperties(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить имя концепта
    @GET("rxcui/{rxcui}")
    fun getRxNormName(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить свойство концепта
    @GET("rxcui/{rxcui}/property")
    fun getRxProperty(@Path("rxcui") rxcui: String, @Query("propName") propName: String): Call<JsonObject>

    // Получить статус/историю концепта
    @GET("rxcui/{rxcui}/historystatus")
    fun getRxcuiHistoryStatus(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить связанные концепты по relationship
    @GET("rxcui/{rxcui}/related")
    fun getRelatedByRelationship(@Path("rxcui") rxcui: String, @Query("rela") rela: String): Call<JsonObject>

    // Получить связанные концепты по типу
    @GET("rxcui/{rxcui}/related")
    fun getRelatedByType(@Path("rxcui") rxcui: String, @Query("tty") tty: String): Call<JsonObject>

    // Получить проприетарную информацию
    @GET("rxcui/{rxcui}/proprietary")
    fun getProprietaryInformation(@Path("rxcui") rxcui: String): Call<JsonObject>
}
// --- RxTerms API ---
interface RxTermsApi {
    // Получить все концепты RxTerms
    @GET("RxTerms/allconcepts.json")
    fun getAllConcepts(): Call<JsonObject> // TODO: создать data/model класс

    // Получить всю информацию по RxTerm
    @GET("RxTerms/rxcui/{rxcui}/allinfo.json")
    fun getAllRxTermInfo(@Path("rxcui") rxcui: String): Call<Response5>

    // Получить display name по RxTerm
    @GET("RxTerms/rxcui/{rxcui}/displayname.json")
    fun getRxTermDisplayName(@Path("rxcui") rxcui: String): Call<JsonObject> // TODO: создать data/model класс

    // Получить версию RxTerms
    @GET("RxTerms/version.json")
    fun getRxTermsVersion(): Call<JsonObject> // TODO: создать data/model класс
}

// --- Prescribable RxNorm API ---
interface PrescribableRxNormApi {
    // Фильтрация по свойству
    @GET("prescribable/rxcui/{rxcui}/filter.json")
    fun filterByProperty(@Path("rxcui") rxcui: String, @Query("propName") propName: String, @Query("propValues") propValues: String): Call<JsonObject> // TODO: создать data/model класс

    // Поиск rxcui по id
    @GET("prescribable/rxcui.json")
    fun findRxcuiById(@Query("idtype") idtype: String, @Query("id") id: String): Call<IdGroup>

    // Поиск rxcui по строке
    @GET("prescribable/rxcui.json")
    fun findRxcuiByString(@Query("name") name: String): Call<IdGroup>

    // Получить все концепты по TTY
    @GET("prescribable/allconcepts.json")
    fun getAllConceptsByTTY(@Query("tty") tty: String): Call<JsonObject>

    // Получить все свойства
    @GET("prescribable/rxcui/{rxcui}/allProperties.json")
    fun getAllProperties(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить всю связанную информацию
    @GET("prescribable/rxcui/{rxcui}/allrelated.json")
    fun getAllRelatedInfo(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить approximate match
    @GET("prescribable/approximateTerm.json")
    fun getApproximateMatch(@Query("term") term: String): Call<JsonObject>

    // Получить display terms
    @GET("prescribable/displaynames.json")
    fun getDisplayTerms(): Call<JsonObject>

    // Получить drugs
    @GET("prescribable/drugs.json")
    fun getDrugs(@Query("name") name: String): Call<JsonObject>

    // Получить id types
    @GET("prescribable/idtypes.json")
    fun getIdTypes(): Call<JsonObject>

    // Получить бренды по ингредиентам
    @GET("prescribable/brands.json")
    fun getMultiIngredBrand(@Query("ingreds") ingreds: String): Call<JsonObject>

    // Получить NDCs
    @GET("prescribable/rxcui/{rxcui}/ndcs.json")
    fun getNDCs(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить prop categories
    @GET("prescribable/propCategories.json")
    fun getPropCategories(): Call<JsonObject>

    // Получить prop names
    @GET("prescribable/propnames.json")
    fun getPropNames(): Call<JsonObject>

    // Получить rela paths
    @GET("prescribable/relapaths.json")
    fun getRelaPaths(): Call<JsonObject>

    // Получить rela types
    @GET("prescribable/relatypes.json")
    fun getRelaTypes(): Call<JsonObject>

    // Получить related by relationship
    @GET("prescribable/rxcui/{rxcui}/related.json")
    fun getRelatedByRelationship(@Path("rxcui") rxcui: String, @Query("rela") rela: String): Call<JsonObject>

    // Получить related by type
    @GET("prescribable/rxcui/{rxcui}/related.json")
    fun getRelatedByType(@Path("rxcui") rxcui: String, @Query("tty") tty: String): Call<JsonObject>

    // Получить свойства концепта
    @GET("prescribable/rxcui/{rxcui}/properties.json")
    fun getRxConceptProperties(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить имя концепта
    @GET("prescribable/rxcui/{rxcui}.json")
    fun getRxNormName(@Path("rxcui") rxcui: String): Call<JsonObject>

    // Получить свойство концепта
    @GET("prescribable/rxcui/{rxcui}/property.json")
    fun getRxProperty(@Path("rxcui") rxcui: String, @Query("propName") propName: String): Call<JsonObject>

    // Получить источники
    @GET("prescribable/sourcetypes.json")
    fun getSourceTypes(): Call<JsonObject>

    // Получить spelling suggestions
    @GET("prescribable/spellingsuggestions.json")
    fun getSpellingSuggestions(@Query("term") term: String): Call<JsonObject>

    // Получить term types
    @GET("prescribable/termtypes.json")
    fun getTermTypes(): Call<JsonObject>

    // Получить версию
    @GET("prescribable/version.json")
    fun getRxNormVersion(): Call<JsonObject>
}

// --- RxClass API ---
interface RxClassApi {
    // Поиск класса по имени
    @GET("rxclass/class/byName.json")
    fun findClassByName(
        @Query("className") className: String,
        @Query("classTypes") classTypes: String
    ): Call<Response8> // rxclassDrugInfoList

    // Поиск классов по id
    @GET("rxclass/class/byId.json")
    fun findClassesById(
        @Query("id") id: String,
        @Query("idType") idType: String,
        @Query("classTypes") classTypes: String
    ): Call<Response8>

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
    ): Call<JsonObject>

    // Получить класс по RxNorm drug name
    @GET("rxclass/class/classByDrugName.json")
    fun getClassByRxNormDrugName(
        @Query("drugName") drugName: String
    ): Call<JsonObject>

    // Получить контексты классов
    @GET("rxclass/class/classContexts.json")
    fun getClassContexts(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить граф класса по источнику
    @GET("rxclass/class/classGraphBySource.json")
    fun getClassGraphBySource(
        @Query("classId") classId: String,
        @Query("classType") classType: String,
        @Query("source") source: String
    ): Call<JsonObject>

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
    ): Call<JsonObject>

    // Получить версию источника отношений
    @GET("rxclass/class/relaSourceVersion.json")
    fun getRelaSourceVersion(
        @Query("source") source: String
    ): Call<JsonObject>

    // Получить отношения
    @GET("rxclass/class/relas.json")
    fun getRelas(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить информацию о схожести
    @GET("rxclass/class/similarityInformation.json")
    fun getSimilarityInformation(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить источники связей классов
    @GET("rxclass/class/sourcesOfDrugClassRelations.json")
    fun getSourcesOfDrugClassRelations(
        @Query("classId") classId: String,
        @Query("classType") classType: String
    ): Call<JsonObject>

    // Получить подсказки по написанию
    @GET("rxclass/class/spellingsuggestions.json")
    fun getSpellingSuggestionsRxClass(
        @Query("term") term: String
    ): Call<JsonObject>

    // Получить типы классов
    @GET("rxclass/classTypes.json")
    fun getClassTypes(): Call<JsonObject>
}