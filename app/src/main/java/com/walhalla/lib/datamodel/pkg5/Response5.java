
package com.walhalla.lib.datamodel.pkg5;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Keep;

@Keep
public class Response5 {
    @SerializedName("rxtermsProperties")
    @Expose
    public RxtermsProperties rxtermsProperties;

    @Override
    public String toString() {
        return "Response5{" +
                "rxtermsProperties=" + rxtermsProperties +
                '}';
    }
}
