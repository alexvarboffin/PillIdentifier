package com.walhalla.lib;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.service.Mpc;

public class BaseImages {
    @SerializedName("part")
    @Expose
    public Integer part;
    @SerializedName("rxcui")
    @Expose
    public Integer rxcui;
    @SerializedName("labeler")
    @Expose
    public String labeler;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("mpc")
    @Expose
    public Mpc mpc;

    @SerializedName("ndc11")
    @Expose
    public String ndc11;
}
