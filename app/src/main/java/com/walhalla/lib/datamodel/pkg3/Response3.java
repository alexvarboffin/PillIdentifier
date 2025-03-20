
package com.walhalla.lib.datamodel.pkg3;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.datamodel.pkg_base.PropConceptGroup;

@Keep
public class Response3 {

    @SerializedName("propConceptGroup")
    @Expose
    public PropConceptGroup propConceptGroup;

}
