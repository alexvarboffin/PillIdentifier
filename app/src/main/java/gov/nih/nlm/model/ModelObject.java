package gov.nih.nlm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class ModelObject implements Serializable {

    @SerializedName("replyStatus")
    @Expose
    public ReplyStatus replyStatus;
    @SerializedName("nlmRxImages")
    @Expose
    public List<NlmRxImage> nlmRxImages = null;
}