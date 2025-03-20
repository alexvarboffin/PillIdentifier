package com.walhalla.lib.service;

import androidx.annotation.Keep;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Keep
public class Mpc {

    @SerializedName("shape")
    @Expose
    public String shape = "";
    @SerializedName("size")
    @Expose
    public Integer size = 0;
    @SerializedName("color")
    @Expose
    public String color = "";
    @SerializedName("imprint")
    @Expose
    public String imprint = "";

    @SerializedName("imprintColor")
    @Expose
    public String imprintColor = "";
    @SerializedName("imprintType")
    @Expose
    public String imprintType = "";
    @SerializedName("symbol")
    @Expose
    public Boolean symbol;
    @SerializedName("score")
    @Expose
    public Integer score = 0;

}
