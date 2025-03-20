
package com.walhalla.lib.datamodel.pkg7;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class IngredientConcept {

    @SerializedName("ingredientRxcui")
    @Expose
    public String ingredientRxcui;
    @SerializedName("ingredientName")
    @Expose
    public String ingredientName;

}
