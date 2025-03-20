
package com.walhalla.lib.datamodel.pkg6;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class InteractionType {

    @SerializedName("comment")
    @Expose
    public String comment;
    @SerializedName("minConceptItem")
    @Expose
    public MinConceptItem minConceptItem;
    @SerializedName("interactionPair")
    @Expose
    public List<InteractionPair> interactionPair = new ArrayList<InteractionPair>();

    @Override
    public String toString() {
        return "InteractionType{" +
                "comment='" + comment + '\'' +
                ", minConceptItem=" + minConceptItem +
                ", interactionPair=" + interactionPair +
                '}';
    }
}
