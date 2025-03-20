package gov.nih.nlm.model;

/**
 * Created by ponch on 14.03.17.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class ReplyStatus {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("date")
    @Expose
    public String date = String.valueOf(new Date());

    @SerializedName("imageCount")
    @Expose
    public Integer imageCount;


    @SerializedName("totalImageCount")
    @Expose
    private Integer totalImageCount;
    @SerializedName("pageNumber")
    @Expose
    public Integer pageNumber;
    @SerializedName("totalPageCount")
    @Expose
    public Integer totalPageCount;
    @SerializedName("matchedTerms")
    @Expose
    private MatchedTerms matchedTerms;

    public Boolean getSuccess() {
        return success;
    }
    public Integer getTotalImageCount() {
        return totalImageCount;
    }
    public MatchedTerms getMatchedTerms() {
        return matchedTerms;
    }

}