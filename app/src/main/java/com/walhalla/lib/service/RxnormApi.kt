package com.walhalla.lib.service;

import com.google.gson.JsonObject;
import com.walhalla.lib.datamodel.pkg0.Response0;
import com.walhalla.lib.datamodel.pkg1.Response1;
import com.walhalla.lib.datamodel.pkg2.Response2;
import com.walhalla.lib.datamodel.pkg4.Response4;
import com.walhalla.lib.datamodel.pkg5.Response5;
import com.walhalla.lib.datamodel.pkg6.Response6;
import com.walhalla.lib.datamodel.pkg7.Response7;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public  interface RxnormApi {

    //11
    @GET("interaction/interaction.json")
    Call<JsonObject> interaction(
            @Query("rxcui") String id,
            @Query("caller") String caller,
            @Query("sources") String sources
    );

    //10
    @GET("rxcui/{id}/allhistoricalndcs.json")
    Call<JsonObject> allhistoricalndcs(
            @Path("id") String id,
            @Query("caller") String caller,
            @Query("history") int history
    );

    //9
    @GET("rxclass/class/byRxcui.json")
    Call<JsonObject> byRxcui(
            @Query("rxcui") String id
            //@Query("caller") String caller

    );

    //8
    @GET("rxcui/{id}/historystatus.json")
    Call<Response7> historystatus(
            @Path("id") String id,
            @Query("caller") String caller

    );

    //7
    @GET("interaction/interaction.json")
    Call<Response6> interaction(
            @Query("rxcui") String id,
            @Query("sources") String sources

    );
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
    Call<Response5> getAllRxTermInfo(
            @Path("id") String id,
            @Query("caller") String caller
    );

    //5
    @GET("ndcproperties.json")
    Call<JsonObject> ndcproperties(
            @Query("id") String id,
            @Query("caller") String caller

    );


    //Attributes
    //Codes
    //Names
    //Sources
    //ALL
    //4 [tab3]
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allProperties.json?caller=RxNav&prop=Attributes
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allProperties.json?caller=RxNav&prop=Sources
    @GET("rxcui/{id}/allProperties.json")
    Call<Response4> allProperties(
            @Path("id") String id,
            @Query("caller") String caller,
            @Query("prop") String prop

    );
    //[tab3]
    //https://rxnav.nlm.nih.gov/REST/rxcui/1186300/allrelatedextension.json?caller=RxNav
    @GET("rxcui/{id}/allrelatedextension.json")
    Call<Response2> allrelatedextension(
            @Path("id") String id,
            @Query("caller") String caller
    );


    //&srclist=yourSources&
    @GET("rxcui.json")
    Call<Response0> searchRxcui(
            @Query("name") String value, //lidocaine+hydrochloride
            @Query("search") int value2,//0or1or2
            @Query("allsrc") int value3//0or1
    );

//    properties
//    rxcui	"198449"
//    name	"acetaminophen 500 MG / pamabrom 25 MG Oral Tablet"
//    synonym	"APAP 500 MG / pamabrom 25 MG Oral Tablet"
//    tty	"SCD"
//    language	"ENG"
//    suppress	"N"
//    umlscui	""
    @GET("rxcui/{id}/properties.json")
    Call<Response1> searchRxcuiProp(
            @Path("id") String id,
            @Query("caller") String caller
    );

    @Headers("Content-Type: application/json")
    @GET("drugs.json")
    Call<JsonObject> searchDrugsBase(
            @Query("name") String value
    );

    @GET("rxclass/classTypes.json")
    Call<JsonObject> getClassTypes();

    @GET("rxclass/class/byName.json")
    Call<JsonObject> findClassByName(
            @Query("className") String className,
            @Query("classTypes") String classTypes //coma divider

    );

    @GET("...")
    Call<JsonObject> findClassesById(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> findSimilarClassesByClass(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> findSimilarClassesByDrugList(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getAllClasses(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassByRxNormDrugId(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassByRxNormDrugName(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassContexts(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassGraphBySource(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassMembers(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getClassTree(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getRelaSourceVersion(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getRelas(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getSimilarityInformation(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getSourcesOfDrugClassRelations(
            @Query("...") String value
    );

    @GET("...")
    Call<JsonObject> getSpellingSuggestions(
            @Query("...") String value
    );


    @GET("/rxcui/rxcui/filter.json")
    Call<JsonObject> filterByProperty(
            @Query("propName") String propName,
            @Query("propValues") String propValues

    );
}