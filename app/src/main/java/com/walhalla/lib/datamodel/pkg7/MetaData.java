
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class MetaData {

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("source")
    @Expose
    public String source;
    @SerializedName("releaseStartDate")
    @Expose
    public String releaseStartDate;
    @SerializedName("releaseEndDate")
    @Expose
    public String releaseEndDate;
    @SerializedName("isCurrent")
    @Expose
    public String isCurrent;
    @SerializedName("activeStartDate")
    @Expose
    public String activeStartDate;
    @SerializedName("activeEndDate")
    @Expose
    public String activeEndDate;
    @SerializedName("remappedDate")
    @Expose
    public String remappedDate;

}
