package gov.nih.nlm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.walhalla.lib.BaseImages;
import com.walhalla.lib.Ingredients;
import com.walhalla.lib.service.Mpc;

import java.util.List;


public class NlmRxImage extends BaseImages {

    @SerializedName("id")
    @Expose
    public Integer id = 0;


    @SerializedName("relabelersNdc9")
    @Expose
    private List<RelabelersNdc9> relabelersNdc9 = null;

    @SerializedName("acqDate")
    @Expose
    public String acqDate = "";

    @SerializedName("imageUrl")
    @Expose
    public String imageUrl;
    @SerializedName("imageSize")
    @Expose
    public Integer imageSize;
    @SerializedName("attribution")
    @Expose
    public String attribution;
    @SerializedName("splSetId")
    @Expose
    public String splSetId = "";
    @SerializedName("splRootId")
    @Expose
    private String splRootId;
    @SerializedName("splVersion")
    @Expose
    private Integer splVersion;


    @SerializedName("ingredients")
    @Expose
    public Ingredients ingredients;
    @SerializedName("ingredientsAvailable")
    @Expose
    public Boolean ingredientsAvailable;


    public Integer getPart() {
        return part;
    }

    public List<RelabelersNdc9> getRelabelersNdc9() {
        return relabelersNdc9;
    }


    public String getLabeler() {
        return labeler;
    }

    public String getSplRootId() {
        return splRootId;
    }

    public Integer getSplVersion() {
        return splVersion;
    }

    @Override
    public String toString() {
        return "NlmRxImage{" +
                "id=" + id +
                ", ndc11='" + ndc11 + '\'' +
                ", part=" + part +
                ", relabelersNdc9=" + relabelersNdc9 +
                ", rxcui=" + rxcui +
                ", acqDate='" + acqDate + '\'' +
                ", name='" + name + '\'' +
                ", labeler='" + labeler + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageSize=" + imageSize +
                ", attribution='" + attribution + '\'' +
                ", splSetId='" + splSetId + '\'' +
                ", splRootId='" + splRootId + '\'' +
                ", splVersion=" + splVersion +
                '}';
    }
}


