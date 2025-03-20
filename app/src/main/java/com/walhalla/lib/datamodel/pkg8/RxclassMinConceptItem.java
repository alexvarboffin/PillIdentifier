
package com.walhalla.lib.datamodel.pkg8;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class RxclassMinConceptItem {

    @SerializedName("classId")
    @Expose
    public String classId;
    @SerializedName("className")
    @Expose
    public String className;
    @SerializedName("classType")
    @Expose
    public String classType;

}
