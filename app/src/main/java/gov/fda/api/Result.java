package gov.fda.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("effective_time")
    @Expose
    public String effectiveTime;
    @SerializedName("inactive_ingredient")
    @Expose
    public List<String> inactiveIngredient = null;
    @SerializedName("purpose")
    @Expose
    public List<String> purpose = null;
    @SerializedName("keep_out_of_reach_of_children")
    @Expose
    public List<String> keepOutOfReachOfChildren = null;
    @SerializedName("warnings")
    @Expose
    public List<String> warnings = null;
    @SerializedName("questions")
    @Expose
    public List<String> questions = null;
    @SerializedName("spl_product_data_elements")
    @Expose
    public List<String> splProductDataElements = null;
    @SerializedName("ask_doctor")
    @Expose
    public List<String> askDoctor = null;
    @SerializedName("openfda")
    @Expose
    public Openfda openfda;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("dosage_and_administration")
    @Expose
    public List<String> dosageAndAdministration = null;
    @SerializedName("pregnancy_or_breast_feeding")
    @Expose
    public List<String> pregnancyOrBreastFeeding = null;
    @SerializedName("stop_use")
    @Expose
    public List<String> stopUse = null;
    @SerializedName("storage_and_handling")
    @Expose
    public List<String> storageAndHandling = null;
    @SerializedName("do_not_use")
    @Expose
    public List<String> doNotUse = null;
    @SerializedName("package_label_principal_display_panel")
    @Expose
    public List<String> packageLabelPrincipalDisplayPanel = null;
    @SerializedName("indications_and_usage")
    @Expose
    public List<String> indicationsAndUsage = null;
    @SerializedName("set_id")
    @Expose
    public String setId;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("ask_doctor_or_pharmacist")
    @Expose
    public List<String> askDoctorOrPharmacist = null;
    @SerializedName("active_ingredient")
    @Expose
    public List<String> activeIngredient = null;

}
