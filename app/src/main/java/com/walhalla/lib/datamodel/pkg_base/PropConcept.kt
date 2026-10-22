package com.walhalla.lib.datamodel.pkg_base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PropConcept {
    @SerializedName("propCategory")
    @Expose
    var propCategory: String? = null

    @SerializedName("propName")
    @Expose
    var propName: String? = null

    @SerializedName("propValue")
    @Expose
    var propValue: String? = null

    @SerializedName("propSource")
    @Expose
    var propSource: String? = null

    @SerializedName("propType")
    @Expose
    var propType: String? = null
}
