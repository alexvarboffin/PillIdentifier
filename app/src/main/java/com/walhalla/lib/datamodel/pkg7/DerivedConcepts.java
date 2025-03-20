
package com.walhalla.lib.datamodel.pkg7;

import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class DerivedConcepts {

    @SerializedName("ingredientConcept")
    @Expose
    public List<IngredientConcept> ingredientConcept = new ArrayList<IngredientConcept>();

}
