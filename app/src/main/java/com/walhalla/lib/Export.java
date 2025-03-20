package com.walhalla.lib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.service.Mpc;

import java.util.ArrayList;
import java.util.List;

public class Export extends BaseImages
{
    //New v2
    @SerializedName("imp")
    @Expose
    public List<String> imp = new ArrayList<>();

    @SerializedName("rxnavImageSize300")
    @Expose
    public Integer rxnavImageSize300;

    @SerializedName("nlmImageSize800")
    @Expose
    public Integer nlmImageSize800;

    @SerializedName("ingredientsAvailable")
    @Expose
    public Boolean ingredientsAvailable;
    @SerializedName("nlmImageSizeFull")
    @Expose
    public Integer nlmImageSizeFull;

    @SerializedName("acqDate")
    @Expose
    public String acqDate;
    @SerializedName("nlmImageSize600")
    @Expose
    public Integer nlmImageSize600;
    @SerializedName("rxnavImageSize120")
    @Expose
    public Integer rxnavImageSize120;
    @SerializedName("rxnavImageObjectId")
    @Expose
    public Integer rxnavImageObjectId;
    @SerializedName("deaSchedule")
    @Expose
    public String deaSchedule;
    @SerializedName("rxnavImageSize600")
    @Expose
    public Integer rxnavImageSize600;
    @SerializedName("rxnavImageSizeFull")
    @Expose
    public Integer rxnavImageSizeFull;
    @SerializedName("rxnavImageSize1024")
    @Expose
    public Integer rxnavImageSize1024;
    @SerializedName("attribution")
    @Expose
    public String attribution;
    @SerializedName("rxnavImageSize")
    @Expose
    public Integer rxnavImageSize;
    @SerializedName("nlmImageSize120")
    @Expose
    public Integer nlmImageSize120;
    @SerializedName("nlmImageSize300")
    @Expose
    public Integer nlmImageSize300;

    @SerializedName("nlmImageSize")
    @Expose
    public Integer nlmImageSize;

    @SerializedName("ingredients")
    @Expose
    public Ingredients ingredients;


    @SerializedName("nlmImageFileName")
    @Expose
    public String nlmImageFileName;

    @SerializedName("rxnavImageFileName")
    @Expose
    public String rxnavImageFileName;


    @SerializedName("_id")
    @Expose
    public Id id;
    @SerializedName("imageSize")
    @Expose
    public Integer imageSize;
    @SerializedName("rxnavImageSize800")
    @Expose
    public Integer rxnavImageSize800;


    @SerializedName("nlmImageObjectId")
    @Expose
    public Integer nlmImageObjectId;
}
