
package com.walhalla.lib.datamodel.pkg8;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class MinConcept {

    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("tty")
    @Expose
    public String tty;

}
