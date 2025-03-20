
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class RxcuiStatusHistory {

    @SerializedName("metaData")
    @Expose
    public MetaData metaData;
    @SerializedName("attributes")
    @Expose
    public Attributes attributes;
    @SerializedName("definitionalFeatures")
    @Expose
    public DefinitionalFeatures definitionalFeatures;
    @SerializedName("pack")
    @Expose
    public Pack pack;
    @SerializedName("derivedConcepts")
    @Expose
    public DerivedConcepts derivedConcepts;

}
