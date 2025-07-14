package com.walhalla.lib.datamodel.rxnorm.response

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.walhalla.lib.datamodel.common.response.MinConcept

// --- Классы для взаимодействий лекарств ---

@Keep
data class Response6(
    @SerializedName("nlmDisclaimer")
    @Expose
    val nlmDisclaimer: String? = null,
    @SerializedName("interactionTypeGroup")
    @Expose
    val interactionTypeGroup: List<InteractionTypeGroup> = emptyList()
)

@Keep
data class InteractionTypeGroup(
    @SerializedName("sourceDisclaimer")
    @Expose
    val sourceDisclaimer: String? = null,
    @SerializedName("sourceName")
    @Expose
    val sourceName: String? = null,
    @SerializedName("interactionType")
    @Expose
    val interactionType: List<InteractionType> = emptyList()
)

@Keep
data class InteractionType(
    @SerializedName("comment")
    @Expose
    val comment: String? = null,
    @SerializedName("interactionPair")
    @Expose
    val interactionPair: List<InteractionPair> = emptyList(),
    @SerializedName("minConceptItem")
    @Expose
    val minConceptItem: MinConcept? = null
)

@Keep
data class InteractionPair(
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("severity")
    @Expose
    val severity: String? = null,
    @SerializedName("interactionConcept")
    @Expose
    val interactionConcept: List<InteractionConcept> = emptyList()
)

@Keep
data class InteractionConcept(
    @SerializedName("minConceptItem")
    @Expose
    val minConceptItem: MinConcept? = null,
    @SerializedName("sourceConceptItem")
    @Expose
    val sourceConceptItem: SourceConceptItem? = null
)

@Keep
data class SourceConceptItem(
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null
) 