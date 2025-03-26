package gov.fda.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Meta {
    @JvmField
    @SerializedName("disclaimer")
    @Expose
    var disclaimer: String? = null

    @JvmField
    @SerializedName("terms")
    @Expose
    var terms: String? = null

    @JvmField
    @SerializedName("license")
    @Expose
    var license: String? = null

    @JvmField
    @SerializedName("last_updated")
    @Expose
    var lastUpdated: String? = null

    @JvmField
    @SerializedName("results")
    @Expose
    var results: Results? = null
}
