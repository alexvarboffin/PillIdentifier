package gov.fda.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("disclaimer")
    @Expose
    public String disclaimer;
    @SerializedName("terms")
    @Expose
    public String terms;
    @SerializedName("license")
    @Expose
    public String license;
    @SerializedName("last_updated")
    @Expose
    public String lastUpdated;
    @SerializedName("results")
    @Expose
    public Results results;

}
