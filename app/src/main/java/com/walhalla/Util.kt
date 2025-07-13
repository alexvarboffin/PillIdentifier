package com.walhalla

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.walhalla.lib.Export
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.R
import com.walhalla.pillfinder.adapter.mpc.MpcObj
import com.walhalla.pillfinder.adapter.obj.HeaderObject
import com.walhalla.pillfinder.adapter.obj.NameValue2_1
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString
import com.walhalla.pillfinder.adapter.obj.SimpleString
import com.walhalla.pillfinder.adapter.obj.VieModel
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString
import gov.fda.api.Main
import gov.nih.nlm.model.NlmRxImage
import gov.nih.nlm.model.ReplyStatus

object Util {
    private const val TAB = '\t'

    fun findArrayIndex(data: String, arr: Array<String>): Int {
        for (i in arr.indices) {
            if (arr[i] == data) {
                return i
            }
        }
        return 0 //return first element = > null => "ANY"
    }

    fun wrapper(o: ReplyStatus?): SpannableStringBuilder {
        val v = SpannableStringBuilder()
        if (o == null) {
            return v
        }

        //v.append("<br />").append("<b>").append("success").append("</b>: ").append(String.valueOf(o.getSuccess()));
        val date: Spannable = SpannableStringBuilder(o.date)
        date.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            0,
            date.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        date.setSpan(
            BackgroundColorSpan(Color.RED),
            0,
            date.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        v /*.append("<b>").append("date")/ *.append("</b>")*/.append(date)

        //REMOVED v.append(TAB)/*.append("<b>")*/.append("ImageCount: ")/*.append("</b>")*/.append(String.valueOf(o.imageCount));
        //REMOVED v.append(TAB)/*.append("<b>")*/.append("TotalImageCount: ")/*.append("</b>")*/.append(String.valueOf(o.getTotalImageCount()));

        //REMOVED SpannableStringBuilder pn = mQ(String.valueOf(o.pageNumber));
        //REMOVED SpannableStringBuilder aq = mQ(String.valueOf(o.totalPageCount));

        //REMOVED v.append(TAB)/*.append("<b>")*/.append("PageNumber: ")/*.append("</b>")*/.append(pn);
        //REMOVED v.append(TAB)/*.append("<b>")*/.append("TotalPageCount: ")/*.append("</b>")*/.append(aq);
        if (o.getMatchedTerms() != null) {
            v.append(TAB) /*.append("<b>")*/.append("MatchedTerms: ") /*.append("</b>")*/

            noneNull(v, o.matchedTerms.imprint)
            noneNull(v, o.getMatchedTerms().getSize())
            noneNull(v, o.getMatchedTerms().getColor())
            noneNull(v, o.getMatchedTerms().getShape())
            noneNull(v, o.getMatchedTerms().getScore())
        }

        return v
    }

    private fun noneNull(v: SpannableStringBuilder, color: String?) {
        if (color != null) {
            v.append(color).append(TAB)
        }
    }

    fun wrapper(data: Main): SpannableStringBuilder {
        val value = SpannableStringBuilder()
        val meta = data.meta
        val results = data.results
        if (meta != null) {
            append(value, "disclaimer", "" + meta.disclaimer)
            append(value, "terms", "" + meta.terms)
            append(value, "license", "" + meta.license)
            append(value, "last_updated", "" + meta.lastUpdated)
            val mr = meta.results
            if (mr != null) {
                append(value, "results", "________")
                append(value, "skip", "" + mr.skip)
                append(value, "limit", "" + mr.limit)
                append(value, "total", "" + mr.total)
            }
        }
        if (results != null) {
            //dappend(value, "#################", "________");
            append(value, "results", "________")
            val result = results.get(0)
            if (result != null) {
                append(value, "effective_time", result.effectiveTime)

                wrapList(value, "purpose", result.purpose)
                wrapList(value, "keep_out_of_reach_of_children", result.keepOutOfReachOfChildren)
                wrapList(value, "warnings", result.warnings)
                wrapList(value, "questions", result.questions)
                wrapList(value, "spl_product_data_elements", result.splProductDataElements)
                wrapList(value, "ask_doctor", result.askDoctor)

                val openfda = result.openfda
                if (openfda != null) {
                    wrapList(value, "upc", openfda.upc)
                    wrapList(value, "brand_name", openfda.brandName)
                    wrapList(value, "manufacturer_name", openfda.manufacturerName)

                    wrapList(value, "unii", openfda.unii)
                    wrapList(value, "rxcui", openfda.rxcui)
                    wrapList(value, "spl_id", openfda.splId)

                    wrapList(value, "substance_name", openfda.substanceName)
                    wrapList(value, "product_type", openfda.productType)
                    wrapList(value, "route", openfda.route)
                    wrapList(value, "application_number", openfda.applicationNumber)

                    wrapList(value, "product_ndc", openfda.productNdc)
                    append(value, "is_original_packager", openfda.isOriginalPackager.toString())
                    wrapList(value, "package_ndc", openfda.packageNdc)
                    wrapList(value, "generic_name", openfda.genericName)
                    wrapList(value, "spl_set_id", openfda.splSetId)
                }


                append(value, "version", result.version)
                wrapList(value, "dosage_and_administration", result.dosageAndAdministration)
                wrapList(value, "pregnancy_or_breast_feeding", result.pregnancyOrBreastFeeding)
                wrapList(value, "stop_use", result.stopUse)
                wrapList(value, "storage_and_handling", result.storageAndHandling)
                wrapList(value, "do_not_use", result.doNotUse)
                wrapList(
                    value,
                    "package_label_principal_display_panel",
                    result.packageLabelPrincipalDisplayPanel
                )
                wrapList(value, "indications_and_usage", result.indicationsAndUsage)


                append(value, "set_id", result.setId)
                append(value, "id", result.id)

                val ask_doctor_or_pharmacist = result.askDoctorOrPharmacist
                if (ask_doctor_or_pharmacist != null) {
                    append(value, "ask_doctor_or_pharmacist", "________")
                    for (s in ask_doctor_or_pharmacist) {
                        append(value, null, s)
                    }
                }

                val inactive_ingredient = result.inactiveIngredient
                //dappend(value, "inactive_ingredient", "________");
                if (inactive_ingredient != null) {
                    for (ingredient in inactive_ingredient) {
                        append(value, "inactive_ingredient", ingredient)
                    }
                }

                val active_ingredient = result.activeIngredient
                //dappend(value, "active_ingredient", "________");
                if (active_ingredient != null) {
                    for (ingredient in active_ingredient) {
                        append(value, "active_ingredient", ingredient)
                    }
                }
            }
        }
        return value
    }

    private fun wrapList(
        value: SpannableStringBuilder,
        name: String?,
        data: MutableList<String?>?
    ) {
        if (data != null) {
            for (s in data) {
                append(value, name, s)
            }
        }
    }

    fun wrapper(context: Context, obj: NlmRxImage): ArrayList<VieModel> {
        val data = ArrayList<VieModel>()
        if (obj.ndc11 != null) {
            data.add(HeaderObject("NDC11 (National Drug Code): "))
            data.add(SimpleString(obj.ndc11))
        }

        append(data, "Id", "" + obj.id)
        append(data, "Part", "" + obj.getPart())

        if (obj.relabelersNdc9 != null) {
            append(data, "Relabelers NDC9", " ")
            val relabelersNdc9 = obj.relabelersNdc9

            for (i in relabelersNdc9.indices) {
                if (relabelersNdc9[i] != null) {
                    val mm = SpannableStringBuilder()
                    val sourceNdc9 = relabelersNdc9.get(i)!!.sourceNdc9
                    mm.append(relabelersNdc9[i]!!.ndc9.contentToString()).append("\n")

                    append(data, "\t@sourceNdc9", sourceNdc9)
                    append(data, "\tndc9", "" + mm)
                }
            }
        }

        if (obj.rxcui != null) {
            data.add(HeaderObject(context.getString(R.string.rxcui_label)))
            data.add(RxCuiObjString(obj.rxcui))
        }


        append(data, "AcqDate", obj.acqDate)

        data.add(HeaderObject("Name"))
        data.add(HeaderObject(obj.name))

        data.add(HeaderObject("Labeler"))
        data.add(SimpleString(obj.getLabeler()))


        //REMOVED dappend(data, "ImageUrl", obj.imageUrl);
        append(data, "ImageSize", obj.imageSize.toString())
        append(data, "Attribution", obj.attribution)

        if (obj.mpc != null) {
            data.add(HeaderObject("Physical characteristics (MPC)"))
            data.add(MpcObj(MpcField.SHAPE, obj.mpc.shape))
            data.add(MpcObj(MpcField.SIZE, obj.mpc.size.toString()))
            data.add(MpcObj(MpcField.COLOR, obj.mpc.color.toString()))
            data.add(MpcObj(MpcField.IMPRINT, obj.mpc.imprint.toString()))

            data.add(MpcObj(MpcField.IMPRINT_COLOR, obj.mpc.imprintColor.toString()))
            data.add(MpcObj(MpcField.IMPRINT_TYPE, obj.mpc.imprintType.toString()))
            data.add(MpcObj(MpcField.SYMBOL, obj.mpc.symbol.toString()))
            data.add(MpcObj(MpcField.SCORE, obj.mpc.score.toString()))
        }

        if (obj.ingredients != null) {
            val c0 = if (obj.ingredients.active == null) 0 else obj.ingredients.active.size
            val c1 = if (obj.ingredients.inactive == null) 0 else obj.ingredients.inactive.size
            data.add(HeaderObject("Ingredients: (" + c0 + ", " + c1 + ")"))
            val active = obj.ingredients.active
            if (active != null && obj.ingredients.active.size > 0) {
                data.add(HeaderObject("\tActive"))
                for (i in obj.ingredients.active.indices) {
                    val k = "" + (i + 1)
                    data.add(IngredientString(k, active.get(i)!!)) //)
                }
            }
            val bb = obj.ingredients.inactive
            if (bb != null && bb.size > 0) {
                data.add(HeaderObject("\tInactive"))

                for (i in bb.indices) {
                    val k = "" + (i + 1)
                    data.add(IngredientString(k, bb.get(i)!!)) //)
                }
            }
        }

        append(data, "SplSetId", obj.splSetId)
        append(data, "SplRootId", obj.getSplRootId())
        append(
            data,
            "SplVersion",
            (if (obj.splVersion == null) null else "" + obj.splVersion)
        )

        //        if (BuildConfig.DEBUG) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            DLog.d(gson.toJson(obj));
//
//        }
        return data
    }

    fun wrapperText(context: Context, obj: NlmRxImage): SpannableStringBuilder {
        val value = SpannableStringBuilder()
        if (obj.ndc11 != null) {
            value.append("<br />").append("<b>")
                .append("NDC11 (National Drug Code)")
                .append(": </b>")
                .append(obj.ndc11)
                .setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    obj.ndc11.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            value.setSpan(
                BackgroundColorSpan(Color.YELLOW),
                0,
                obj.ndc11.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        append(value, "Id", "" + obj.id)
        append(value, "Part", "" + obj.getPart())

        if (obj.getRelabelersNdc9() != null) {
            append(value, "Relabelers NDC9", " ")
            val relabelersNdc9 = obj.getRelabelersNdc9()

            for (i in relabelersNdc9.indices) {
                if (relabelersNdc9.get(i) != null) {
                    val mm = SpannableStringBuilder()
                    val sourceNdc9 = relabelersNdc9.get(i)!!.sourceNdc9
                    mm.append(relabelersNdc9.get(i)!!.ndc9.contentToString()).append("\n")

                    append(value, "\t@sourceNdc9", sourceNdc9)
                    append(value, "\tndc9", "" + mm)
                }
            }
        }

        //@@
        append(value, context.getString(R.string.rxcui_label), "" + obj.rxcui)
        append(value, "AcqDate", obj.acqDate)
        append(value, "Name", obj.name)
        append(value, "Labeler", obj.getLabeler())

        //REMOVED dappend(value, "ImageUrl", obj.imageUrl);
        append(value, "ImageSize", obj.imageSize.toString())
        append(value, "Attribution", obj.attribution)

        if (obj.mpc != null) {
            append(value, "\nPhysical characteristics (MPC)", " ")
            append(value, context.getString(R.string.mpc_shape), "" + obj.mpc.shape)
            append(value, context.getString(R.string.mpc_size), "" + obj.mpc.size)
            append(value, context.getString(R.string.mpc_color), "" + obj.mpc.color)
            append(value, context.getString(R.string.mpc_imprint), "" + obj.mpc.imprint)
            append(value, context.getString(R.string.mpc_imprint_color), "" + obj.mpc.imprintColor)
            append(value, context.getString(R.string.mpc_imprint_type), "" + obj.mpc.imprintType)
            append(value, context.getString(R.string.mpc_symbol), "" + obj.mpc.symbol)
            append(value, context.getString(R.string.mpc_score), "" + obj.mpc.score)
        }
        if (obj.ingredients != null) {
            append(value, "Ingredients", " ")
            if (obj.ingredients.active != null && !obj.ingredients.active.isEmpty()) {
                append(value, "\tActive", obj.ingredients.active.toString())
            }
            if (obj.ingredients.inactive != null && !obj.ingredients.inactive.isEmpty()) {
                append(value, "\tInactive", obj.ingredients.inactive.toString())
            }
        }

        append(value, "SplSetId", obj.splSetId)
        append(value, "SplRootId", obj.getSplRootId())
        append(
            value,
            "SplVersion",
            (if (obj.getSplVersion() == null) null else "" + obj.getSplVersion())
        )

        //        if (BuildConfig.DEBUG) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            DLog.d(gson.toJson(obj));
//
//        }
        return value
    }

    var B_B: String = ""
    var B_E: String = ""

    //    public static SpannableStringBuilder makeText(NlmRxImage obj) {
    //        SpannableStringBuilder value = new SpannableStringBuilder();
    //        if (obj.ndc11 != null) {
    //            value.append("\n").append(B_B)
    //                    .append("NDC11 (National Drug Code)").append(": ").append(B_E)
    //                    .append(obj.ndc11)
    //                    .setSpan(new ForegroundColorSpan(Color.BLACK), 0, obj.ndc11.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
    //            value.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, obj.ndc11.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
    //        }
    //
    //        appendText(value, "Id", "" + obj.getId());
    //        appendText(value, "Part", "" + obj.getPart());
    //
    //        if (obj.getRelabelersNdc9() != null) {
    //            List<RelabelersNdc9> tmp = obj.getRelabelersNdc9();
    //            SpannableStringBuilder mm = new SpannableStringBuilder();
    //            for (int i = 0; i < tmp.size(); i++) {
    //                if (tmp.get(i) != null) {
    //                    mm.append(Arrays.toString(tmp.get(i).getNdc9())).append("\n");
    //                }
    //
    //            }
    //            appendText(value, "Relabelers NDC9: ", "" + mm);
    //        }
    //
    //
    //        //@@
    //        appendText(value, RXCUI_LABEL, "" + obj.getRxcui());
    //        appendText(value, "AcqDate", obj.getAcqDate());
    //        appendText(value, "Name", obj.getName());
    //        appendText(value, "Labeler", obj.getLabeler());
    //        appendText(value, "ImageUrl", obj.getImageUrl());
    //        appendText(value, "ImageSize", String.valueOf(obj.getImageSize()));
    //        appendText(value, "Attribution", obj.getAttribution());
    //        appendText(value, "SplSetId", obj.getSplSetId());
    //        appendText(value, "SplRootId", obj.getSplRootId());
    //        appendText(value, "SplVersion", ((obj.getSplVersion() == null) ? null : "" + obj.getSplVersion()));
    //
    /*        if (BuildConfig.DEBUG)
    {
        * /            Gson gson = new GsonBuilder().setPrettyPrinting().create();
        * /            DLog.d(gson.toJson(obj));
        * /
        * /
    } */
    //        return value;
    //    }
    private fun appendText(value: SpannableStringBuilder, splVersion: String?, ssss: String?) {
        value.append("\n")
        if (splVersion != null) {
            value.append(B_B)
            value.append(SpannableString(splVersion))
            value.append(": ").append(B_E)
        }
        if (ssss != null && !ssss.isEmpty()) {
            value.append(
                SpannableString(ssss)
            )
        } else {
            val none: Spannable = SpannableStringBuilder("x")
            none.setSpan(
                ForegroundColorSpan(Color.RED),
                0,
                none.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            value.append(none)
        }
    }

    private fun append(o: ArrayList<VieModel>, key: String?, values: String?) {
        val none: SpannableString?
        if (values != null && !values.isEmpty()) {
            none = SpannableString(values)
        } else {
            none = SpannableString("x")
            none.setSpan(
                ForegroundColorSpan(Color.RED),
                0,
                none.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        o.add(NameValue2_1("$key:", none.toString()))
    }

    private fun append(value: SpannableStringBuilder, splVersion: String?, ssss: String?) {
        value.append("<br />")
        if (splVersion != null) {
            value.append("<b>")
            value.append(SpannableString(splVersion))
            value.append(": </b>")
        }
        if (ssss != null && !ssss.isEmpty()) {
            value.append(
                SpannableString(ssss)
            )
        } else {
            val none: Spannable = SpannableStringBuilder("x")
            none.setSpan(
                ForegroundColorSpan(Color.RED),
                0,
                none.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            value.append(none)
        }
    }

    private fun mQ(valueOf: String?): SpannableStringBuilder {
        val pn = SpannableStringBuilder(valueOf)
        pn.setSpan(
            ForegroundColorSpan(Color.YELLOW),
            0,
            pn.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        pn.setSpan(BackgroundColorSpan(Color.RED), 0, pn.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        return pn
    }


    @JvmStatic
    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun wrapper0(aa: Export?): NlmRxImage {
        val o = NlmRxImage()
        if (aa == null) {
            return o
        }

        //DLog.d("https://pillidentifier-dfb05.web.app/300/"+aa.nlmImageFileName);
        //DLog.d("https://pillidentifier-dfb05.web.app/300/"+aa.rxnavImageFileName);//with_watermark


        o.imageUrl = "https://pillidentifier-dfb05.web.app/300/" + aa.nlmImageFileName
        o.mpc = aa.mpc
        o.name = aa.name
        o.labeler = aa.labeler
        o.rxcui = aa.rxcui
        o.part = aa.part
        o.ndc11 = aa.ndc11
        //o.id = aa.id.oid[0];
        o.attribution = aa.attribution
        o.imageSize = aa.imageSize
        o.ingredients = aa.ingredients
        return o
    }
}
