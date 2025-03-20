
package com.walhalla.lib.datamodel.pkg8;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class RxclassDrugInfoList {

    @SerializedName("rxclassDrugInfo")
    @Expose
    public List<RxclassDrugInfo> rxclassDrugInfo = new ArrayList<RxclassDrugInfo>();

}
