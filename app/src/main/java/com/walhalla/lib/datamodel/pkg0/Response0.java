
package com.walhalla.lib.datamodel.pkg0;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class Response0 implements Serializable {

    @SerializedName("idGroup")
    @Expose
    public IdGroup idGroup;

}
