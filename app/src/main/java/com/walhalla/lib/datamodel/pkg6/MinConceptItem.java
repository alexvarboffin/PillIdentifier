
package com.walhalla.lib.datamodel.pkg6;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class MinConceptItem {

    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("tty")
    @Expose
    public String tty;

    @Override
    public String toString() {
        return "MinConceptItem{" +
                "rxcui='" + rxcui + '\'' +
                ", name='" + name + '\'' +
                ", tty='" + tty + '\'' +
                '}';
    }
}
