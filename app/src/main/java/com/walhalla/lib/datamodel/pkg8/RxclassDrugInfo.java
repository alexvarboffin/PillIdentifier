
package com.walhalla.lib.datamodel.pkg8;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class RxclassDrugInfo {

    @SerializedName("minConcept")
    @Expose
    public MinConcept minConcept;
    @SerializedName("rxclassMinConceptItem")
    @Expose
    public RxclassMinConceptItem rxclassMinConceptItem;
    @SerializedName("rela")
    @Expose
    public String rela;
    @SerializedName("relaSource")
    @Expose
    public String relaSource;

}
