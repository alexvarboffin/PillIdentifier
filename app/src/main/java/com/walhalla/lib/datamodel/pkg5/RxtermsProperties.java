
package com.walhalla.lib.datamodel.pkg5;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class RxtermsProperties {

    @SerializedName("brandName")
    @Expose
    public String brandName;
    @SerializedName("displayName")
    @Expose
    public String displayName;
    @SerializedName("synonym")
    @Expose
    public String synonym;
    @SerializedName("fullName")
    @Expose
    public String fullName;
    @SerializedName("fullGenericName")
    @Expose
    public String fullGenericName;
    @SerializedName("strength")
    @Expose
    public String strength;
    @SerializedName("rxtermsDoseForm")
    @Expose
    public String rxtermsDoseForm;
    @SerializedName("route")
    @Expose
    public String route;
    @SerializedName("termType")
    @Expose
    public String termType;
    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("genericRxcui")
    @Expose
    public String genericRxcui;
    @SerializedName("rxnormDoseForm")
    @Expose
    public String rxnormDoseForm;
    @SerializedName("suppress")
    @Expose
    public String suppress;

    @Override
    public String toString() {
        return "RxtermsProperties{" +
                "brandName='" + brandName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", synonym='" + synonym + '\'' +
                ", fullName='" + fullName + '\'' +
                ", fullGenericName='" + fullGenericName + '\'' +
                ", strength='" + strength + '\'' +
                ", rxtermsDoseForm='" + rxtermsDoseForm + '\'' +
                ", route='" + route + '\'' +
                ", termType='" + termType + '\'' +
                ", rxcui='" + rxcui + '\'' +
                ", genericRxcui='" + genericRxcui + '\'' +
                ", rxnormDoseForm='" + rxnormDoseForm + '\'' +
                ", suppress='" + suppress + '\'' +
                '}';
    }
}
