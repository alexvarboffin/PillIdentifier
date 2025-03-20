
package com.walhalla.lib.datamodel.pkg4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.datamodel.pkg_base.PropConceptGroup;

import androidx.annotation.Keep;

@Keep
public class Response4 {

    @SerializedName("propConceptGroup")
    @Expose
    public PropConceptGroup propConceptGroup;

}
