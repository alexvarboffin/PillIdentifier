
package com.walhalla.lib.datamodel.pkg6;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class InteractionConcept {

    @SerializedName("minConceptItem")
    @Expose
    public MinConceptItem minConceptItem;
    @SerializedName("sourceConceptItem")
    @Expose
    public SourceConceptItem sourceConceptItem;

}
