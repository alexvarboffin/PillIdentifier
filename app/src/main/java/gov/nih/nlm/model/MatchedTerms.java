package gov.nih.nlm.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchedTerms {

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("shape")
    @Expose
    private String shape;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("size")
    @Expose
    private String size;

    @SerializedName("imprint")
    @Expose
    private String imprint;

    public String getColor() {
        return color;
    }

    public String getScore() {
        return score;
    }

    public String getSize() {
        return size;
    }

    public String getImprint() {
        return imprint;
    }

    public String getShape() {
        return shape;
    }


}