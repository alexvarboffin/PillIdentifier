
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Keep
public class Response7 implements Serializable {

    @SerializedName("rxcuiStatusHistory")
    @Expose
    public RxcuiStatusHistory rxcuiStatusHistory;

}
