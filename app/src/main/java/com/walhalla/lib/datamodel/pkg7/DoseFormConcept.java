
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class DoseFormConcept {

    @SerializedName("doseFormRxcui")
    @Expose
    public String doseFormRxcui;
    @SerializedName("doseFormName")
    @Expose
    public String doseFormName;

}
