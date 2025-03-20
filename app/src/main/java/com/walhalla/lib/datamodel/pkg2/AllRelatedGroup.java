
package com.walhalla.lib.datamodel.pkg2;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class AllRelatedGroup {

    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("conceptGroup")
    @Expose
    public List<ConceptGroup> conceptGroup = new ArrayList<ConceptGroup>();

}
