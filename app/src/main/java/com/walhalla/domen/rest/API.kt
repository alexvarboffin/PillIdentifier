package com.walhalla.domen.rest

import gov.nih.nlm.model.ModelObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface API {
    @GET("apps/drug-fda/api/function_api.php")
    fun searchDrugsNav( //@Query("color") String imprintColor
        @QueryMap(encoded = false) options: Map<String?, String?>?
    ): Call<ModelObject?>?


    //Original API
    //    @GET("rximage/1/rxnav")
    //    Call<ModelObject> searchDrugsNav(
    //            //@Query("color") String imprintColor
    //            @QueryMap(encoded = false) Map<String, String> options
    //    );
    @GET("rximage/1/rxbase")
    fun searchDrugsBase( //@Query("color") String imprintColor
        @QueryMap(encoded = false) options: Map<String?, String?>?
    ): Call<ModelObject?>?
}
