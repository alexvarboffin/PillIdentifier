
package com.walhalla.lib.datamodel.pkg6;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class InteractionPair {

    @SerializedName("interactionConcept")
    @Expose
    public List<InteractionConcept> interactionConcept = new ArrayList<InteractionConcept>();
    @SerializedName("severity")
    @Expose
    public String severity;
    @SerializedName("description")
    @Expose
    public String description;

    @Override
    public String toString() {
        return "InteractionPair{" +
                "interactionConcept=" + interactionConcept +
                ", severity='" + severity + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
