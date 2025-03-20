
package com.walhalla.lib.datamodel.pkg2;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Response2 {

    @SerializedName("allRelatedGroup")
    @Expose
    public AllRelatedGroup allRelatedGroup;

}
