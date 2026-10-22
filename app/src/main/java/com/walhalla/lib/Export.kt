package com.walhalla.lib

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Export : BaseImages() {
    //New v2
    @SerializedName("imp")
    @Expose
    var imp: MutableList<String?> = ArrayList<String?>()

    @SerializedName("rxnavImageSize300")
    @Expose
    var rxnavImageSize300: Int? = null

    @SerializedName("nlmImageSize800")
    @Expose
    var nlmImageSize800: Int? = null

    @SerializedName("ingredientsAvailable")
    @Expose
    var ingredientsAvailable: Boolean? = null

    @SerializedName("nlmImageSizeFull")
    @Expose
    var nlmImageSizeFull: Int? = null

    @SerializedName("acqDate")
    @Expose
    var acqDate: String? = null

    @SerializedName("nlmImageSize600")
    @Expose
    var nlmImageSize600: Int? = null

    @SerializedName("rxnavImageSize120")
    @Expose
    var rxnavImageSize120: Int? = null

    @SerializedName("rxnavImageObjectId")
    @Expose
    var rxnavImageObjectId: Int? = null

    @SerializedName("deaSchedule")
    @Expose
    var deaSchedule: String? = null

    @SerializedName("rxnavImageSize600")
    @Expose
    var rxnavImageSize600: Int? = null

    @SerializedName("rxnavImageSizeFull")
    @Expose
    var rxnavImageSizeFull: Int? = null

    @SerializedName("rxnavImageSize1024")
    @Expose
    var rxnavImageSize1024: Int? = null

    @SerializedName("attribution")
    @Expose
    var attribution: String? = null

    @SerializedName("rxnavImageSize")
    @Expose
    var rxnavImageSize: Int? = null

    @SerializedName("nlmImageSize120")
    @Expose
    var nlmImageSize120: Int? = null

    @SerializedName("nlmImageSize300")
    @Expose
    var nlmImageSize300: Int? = null

    @SerializedName("nlmImageSize")
    @Expose
    var nlmImageSize: Int? = null

    @SerializedName("ingredients")
    @Expose
    var ingredients: Ingredients? = null


    @SerializedName("nlmImageFileName")
    @Expose
    var nlmImageFileName: String? = null

    @SerializedName("rxnavImageFileName")
    @Expose
    var rxnavImageFileName: String? = null


    @JvmField
    @SerializedName("_id")
    @Expose
    var id: Id? = null

    @SerializedName("imageSize")
    @Expose
    var imageSize: Int? = null

    @SerializedName("rxnavImageSize800")
    @Expose
    var rxnavImageSize800: Int? = null


    @SerializedName("nlmImageObjectId")
    @Expose
    var nlmImageObjectId: Int? = null
}
