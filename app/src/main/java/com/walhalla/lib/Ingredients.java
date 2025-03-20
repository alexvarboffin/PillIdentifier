package com.walhalla.lib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ingredients {

    @SerializedName("active")
    @Expose
    public List<String> active = null;
    @SerializedName("inactive")
    @Expose
    public List<String> inactive = null;

}