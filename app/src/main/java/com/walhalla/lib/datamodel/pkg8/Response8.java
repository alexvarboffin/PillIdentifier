
package com.walhalla.lib.datamodel.pkg8;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Response8 {

    @SerializedName("rxclassDrugInfoList")
    @Expose
    public RxclassDrugInfoList rxclassDrugInfoList;

}
