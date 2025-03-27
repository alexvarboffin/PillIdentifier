package com.walhalla.lib.service

import com.google.gson.JsonObject
import com.walhalla.lib.datamodel.pkg0.Response0
import com.walhalla.lib.datamodel.pkg1.Response1
import com.walhalla.lib.datamodel.pkg2.Response2
import com.walhalla.lib.datamodel.pkg4.Response4
import com.walhalla.lib.datamodel.pkg5.Response5
import com.walhalla.lib.datamodel.pkg6.Response6
import com.walhalla.lib.datamodel.pkg7.Response7
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

    @get:GET("rxclass/classTypes.json")
    val classTypes: Call<JsonObject>

    @GET("rxclass/class/byName.json")
    fun findClassByName(
        @Query("className") className: String,
        @Query("classTypes") classTypes: String //coma divider

    ): Call<JsonObject>

    @GET("...")
    fun findClassesById(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun findSimilarClassesByClass(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun findSimilarClassesByDrugList(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getAllClasses(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassByRxNormDrugId(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassByRxNormDrugName(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassContexts(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassGraphBySource(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassMembers(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getClassTree(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getRelaSourceVersion(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getRelas(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getSimilarityInformation(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getSourcesOfDrugClassRelations(
        @Query("...") value: String
    ): Call<JsonObject>

    @GET("...")
    fun getSpellingSuggestions(
        @Query("...") value: String
    ): Call<JsonObject>


    @GET("/rxcui/rxcui/filter.json")
    fun filterByProperty(
        @Query("propName") propName: String,
        @Query("propValues") propValues: String

    ): Call<JsonObject>
}