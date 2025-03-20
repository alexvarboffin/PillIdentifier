
package com.walhalla.lib.datamodel.pkg2;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class ConceptGroup {

    @SerializedName("tty")
    @Expose
    public String tty;
    @SerializedName("conceptProperties")
    @Expose
    public List<ConceptProperty> conceptProperties = new ArrayList<ConceptProperty>();

}
