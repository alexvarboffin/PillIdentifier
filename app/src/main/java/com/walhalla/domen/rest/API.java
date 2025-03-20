package com.walhalla.domen.rest;


import gov.nih.nlm.model.ModelObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface API {

    @GET("apps/drug-fda/api/function_api.php")
    Call<ModelObject> searchDrugsNav(
            //@Query("color") String imprintColor
            @QueryMap(encoded = false) Map<String, String> options
    );


//Original API
//    @GET("rximage/1/rxnav")
//    Call<ModelObject> searchDrugsNav(
//            //@Query("color") String imprintColor
//            @QueryMap(encoded = false) Map<String, String> options
//    );

    @GET("rximage/1/rxbase")
    Call<ModelObject> searchDrugsBase(
            //@Query("color") String imprintColor
            @QueryMap(encoded = false) Map<String, String> options
    );
}
