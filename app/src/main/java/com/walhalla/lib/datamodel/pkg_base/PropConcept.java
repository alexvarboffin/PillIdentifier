package com.walhalla.lib.datamodel.pkg_base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PropConcept {

    @SerializedName("propCategory")
    @Expose
    public String propCategory;
    @SerializedName("propName")
    @Expose
    public String propName;
    @SerializedName("propValue")
    @Expose
    public String propValue;

}