package gov.fda.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Results {
    @JvmField
    @SerializedName("skip")
    @Expose
    var skip: Int? = null

    @JvmField
    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    @JvmField
    @SerializedName("total")
    @Expose
    var total: Int? = null
}
