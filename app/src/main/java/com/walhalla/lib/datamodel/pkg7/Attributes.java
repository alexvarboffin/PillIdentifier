
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Attributes {

    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("tty")
    @Expose
    public String tty;
    @SerializedName("isMultipleIngredient")
    @Expose
    public String isMultipleIngredient;
    @SerializedName("isBranded")
    @Expose
    public String isBranded;

}
