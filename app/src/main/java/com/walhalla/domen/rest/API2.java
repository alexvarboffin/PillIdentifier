package com.walhalla.domen.rest;

import gov.fda.api.Main;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API2 {

    @GET("drug/label.json")
    Call<Main> searchDrag(@Query("search") String query);

}
