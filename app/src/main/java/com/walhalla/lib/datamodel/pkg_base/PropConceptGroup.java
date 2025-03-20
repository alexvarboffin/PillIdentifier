package com.walhalla.lib.datamodel.pkg_base;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.datamodel.pkg_base.PropConcept;

import java.util.ArrayList;
import java.util.List;

/**
 * pkg3 pkg4
 *
 */

public class PropConceptGroup {

    @SerializedName("propConcept")
    @Expose
    public List<PropConcept> propConcept = new ArrayList<PropConcept>();

}
