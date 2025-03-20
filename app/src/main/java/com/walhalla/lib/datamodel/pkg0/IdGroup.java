
package com.walhalla.lib.datamodel.pkg0;

import androidx.annotation.Keep;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class IdGroup implements Serializable {

    @SerializedName("rxnormId")
    @Expose
    public List<String> rxnormId = new ArrayList<String>();

}
