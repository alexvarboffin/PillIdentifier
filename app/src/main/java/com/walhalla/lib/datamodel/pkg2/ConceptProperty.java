
package com.walhalla.lib.datamodel.pkg2;

import androidx.annotation.Keep;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Keep
public class ConceptProperty {

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
    @SerializedName("rxnavstr")
    @Expose
    public String rxnavstr;
    @SerializedName("pres")
    @Expose
    public String pres;
    @SerializedName("humandrug")
    @Expose
    public String humandrug;
    @SerializedName("vetdrug")
    @Expose
    public String vetdrug;
    @SerializedName("inferedhuman")
    @Expose
    public String inferedhuman;
    @SerializedName("inferedvet")
    @Expose
    public String inferedvet;
    @SerializedName("genCard")
    @Expose
    public String genCard;

}
