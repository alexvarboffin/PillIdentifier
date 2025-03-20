
package com.walhalla.lib.datamodel.pkg1;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Response1 {

    @SerializedName("properties")
    @Expose
    public Properties properties;

}
