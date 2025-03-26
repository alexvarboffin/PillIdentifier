package gov.fda.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Main {
    @kotlin.jvm.JvmField
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}
