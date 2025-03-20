package com.walhalla.lib.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.datamodel.pkg_base.PropConceptGroup;

public class AttributesResponse {

    @SerializedName("propConceptGroup")
    @Expose
    public PropConceptGroup propConceptGroup;

}