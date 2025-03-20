
package com.walhalla.lib.datamodel.pkg1;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class Properties {

    @SerializedName("rxcui")
    @Expose
    public String rxcui;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("synonym")
    @Expose
    public String synonym;
    @SerializedName("tty")
    @Expose
    public String tty;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("suppress")
    @Expose
    public String suppress;
    @SerializedName("umlscui")
    @Expose
    public String umlscui;

}
