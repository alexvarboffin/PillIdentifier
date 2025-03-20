
package com.walhalla.lib.datamodel.pkg7;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class DefinitionalFeatures {

    @SerializedName("doseFormConcept")
    @Expose
    public List<DoseFormConcept> doseFormConcept = new ArrayList<DoseFormConcept>();

}
