
package com.walhalla.lib.datamodel.pkg6;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class InteractionTypeGroup {

    @SerializedName("sourceDisclaimer")
    @Expose
    public String sourceDisclaimer;
    @SerializedName("sourceName")
    @Expose
    public String sourceName;
    @SerializedName("interactionType")
    @Expose
    public List<InteractionType> interactionType = new ArrayList<InteractionType>();


    @Override
    public String toString() {
        return "InteractionTypeGroup{" +
                "sourceDisclaimer='" + sourceDisclaimer + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", interactionType=" + interactionType +
                '}';
    }
}
