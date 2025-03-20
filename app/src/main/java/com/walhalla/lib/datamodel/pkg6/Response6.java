
package com.walhalla.lib.datamodel.pkg6;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Response6 {

    @SerializedName("nlmDisclaimer")
    @Expose
    public String nlmDisclaimer;
    @SerializedName("interactionTypeGroup")
    @Expose
    public List<InteractionTypeGroup> interactionTypeGroup = new ArrayList<InteractionTypeGroup>();

}
