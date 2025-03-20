package gov.fda.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Main {

    @SerializedName("meta")
    @Expose
    public Meta meta;
    @SerializedName("results")
    @Expose
    public List<Result> results = null;

}
