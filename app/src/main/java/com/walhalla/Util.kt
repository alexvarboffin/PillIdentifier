package com.walhalla;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;


import gov.fda.api.Main;
import gov.fda.api.Meta;
import gov.fda.api.Openfda;
import gov.fda.api.Result;
import gov.nih.nlm.model.NlmRxImage;
import gov.nih.nlm.model.RelabelersNdc9;
import gov.nih.nlm.model.ReplyStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;

import com.walhalla.lib.Export;
import com.walhalla.pillfinder.MpcField;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.adapter.mpc.MpcObj;
import com.walhalla.pillfinder.adapter.obj.HeaderObject;
import com.walhalla.pillfinder.adapter.obj.ingredient.IngredientString;
import com.walhalla.pillfinder.adapter.obj.NameValue2_1;
import com.walhalla.pillfinder.adapter.obj.RxCuiObjString;
import com.walhalla.pillfinder.adapter.obj.SimpleString;
import com.walhalla.pillfinder.adapter.obj.VieModel;

public class Util {

    private static final char TAB = '\t';

    public static int findArrayIndex(@Nullable String data, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(data)) {
                return i;
            }
        }
        return 0; //return first element = > null => "ANY"
    }

    public static SpannableStringBuilder wrapper(ReplyStatus o) {
        SpannableStringBuilder v = new SpannableStringBuilder();
        if (o == null) {
            return v;
        }

        //v.append("<br />").append("<b>").append("success").append("</b>: ").append(String.valueOf(o.getSuccess()));
        Spannable date = new SpannableStringBuilder(o.date);
        date.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, date.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        date.setSpan(new BackgroundColorSpan(Color.RED), 0, date.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        v/*.append("<b>").append("date")/*.append("</b>")*/.append(date);

        //REMOVED v.append(TAB)/*.append("<b>")*/.append("ImageCount: ")/*.append("</b>")*/.append(String.valueOf(o.imageCount));
        //REMOVED v.append(TAB)/*.append("<b>")*/.append("TotalImageCount: ")/*.append("</b>")*/.append(String.valueOf(o.getTotalImageCount()));

        //REMOVED SpannableStringBuilder pn = mQ(String.valueOf(o.pageNumber));
        //REMOVED SpannableStringBuilder aq = mQ(String.valueOf(o.totalPageCount));

        //REMOVED v.append(TAB)/*.append("<b>")*/.append("PageNumber: ")/*.append("</b>")*/.append(pn);
        //REMOVED v.append(TAB)/*.append("<b>")*/.append("TotalPageCount: ")/*.append("</b>")*/.append(aq);

        if (o.getMatchedTerms() != null) {
            v.append(TAB)/*.append("<b>")*/.append("MatchedTerms: ");/*.append("</b>")*/

            noneNull(v, o.getMatchedTerms().getImprint());
            noneNull(v, o.getMatchedTerms().getSize());
            noneNull(v, o.getMatchedTerms().getColor());
            noneNull(v, o.getMatchedTerms().getShape());
            noneNull(v, o.getMatchedTerms().getScore());

        }

        return v;
    }

    private static void noneNull(SpannableStringBuilder v, String color) {
        if (color != null) {
            v.append(color).append(TAB);
        }
    }

    public static SpannableStringBuilder wrapper(Main data) {
        SpannableStringBuilder value = new SpannableStringBuilder();
        Meta meta = data.meta;
        List<Result> results = data.results;
        if (meta != null) {
            append(value, "disclaimer", "" + meta.disclaimer);
            append(value, "terms", "" + meta.terms);
            append(value, "license", "" + meta.license);
            append(value, "last_updated", "" + meta.lastUpdated);

            if (meta.results != null) {
                append(value, "results", "________");
                append(value, "skip", "" + meta.results.skip);
                append(value, "limit", "" + meta.results.limit);
                append(value, "total", "" + meta.results.total);
            }

        }
        if (results != null) {
            //dappend(value, "#################", "________");
            append(value, "results", "________");
            Result result = results.get(0);
            if (result != null) {
                append(value, "effective_time", result.effectiveTime);

                wrapList(value, "purpose", result.purpose);
                wrapList(value, "keep_out_of_reach_of_children", result.keepOutOfReachOfChildren);
                wrapList(value, "warnings", result.warnings);
                wrapList(value, "questions", result.questions);
                wrapList(value, "spl_product_data_elements", result.splProductDataElements);
                wrapList(value, "ask_doctor", result.askDoctor);

                Openfda openfda = result.openfda;
                if (openfda != null) {
                    wrapList(value, "upc", openfda.upc);
                    wrapList(value, "brand_name", openfda.brandName);
                    wrapList(value, "manufacturer_name", openfda.manufacturerName);

                    wrapList(value, "unii", openfda.unii);
                    wrapList(value, "rxcui", openfda.rxcui);
                    wrapList(value, "spl_id", openfda.splId);

                    wrapList(value, "substance_name", openfda.substanceName);
                    wrapList(value, "product_type", openfda.productType);
                    wrapList(value, "route", openfda.route);
                    wrapList(value, "application_number", openfda.applicationNumber);

                    wrapList(value, "product_ndc", openfda.productNdc);
                    append(value, "is_original_packager", String.valueOf(openfda.isOriginalPackager));
                    wrapList(value, "package_ndc", openfda.packageNdc);
                    wrapList(value, "generic_name", openfda.genericName);
                    wrapList(value, "spl_set_id", openfda.splSetId);


                }


                append(value, "version", result.version);
                wrapList(value, "dosage_and_administration", result.dosageAndAdministration);
                wrapList(value, "pregnancy_or_breast_feeding", result.pregnancyOrBreastFeeding);
                wrapList(value, "stop_use", result.stopUse);
                wrapList(value, "storage_and_handling", result.storageAndHandling);
                wrapList(value, "do_not_use", result.doNotUse);
                wrapList(value, "package_label_principal_display_panel", result.packageLabelPrincipalDisplayPanel);
                wrapList(value, "indications_and_usage", result.indicationsAndUsage);


                append(value, "set_id", result.setId);
                append(value, "id", result.id);

                List<String> ask_doctor_or_pharmacist = result.askDoctorOrPharmacist;
                if (ask_doctor_or_pharmacist != null) {
                    append(value, "ask_doctor_or_pharmacist", "________");
                    for (String s : ask_doctor_or_pharmacist) {
                        append(value, null, s);
                    }
                }

                List<String> inactive_ingredient = result.inactiveIngredient;
                //dappend(value, "inactive_ingredient", "________");
                if (inactive_ingredient != null) {
                    for (String ingredient : inactive_ingredient) {
                        append(value, "inactive_ingredient", ingredient);
                    }
                }

                List<String> active_ingredient = result.activeIngredient;
                //dappend(value, "active_ingredient", "________");
                if (active_ingredient != null) {
                    for (String ingredient : active_ingredient) {
                        append(value, "active_ingredient", ingredient);
                    }
                }
            }
        }
        return value;
    }

    private static void wrapList(SpannableStringBuilder value, String name, List<String> data) {
        if (data != null) {
            for (String s : data) {
                append(value, name, s);
            }
        }
    }

    public static ArrayList<VieModel> wrapper(Context context, NlmRxImage obj) {
        ArrayList<VieModel> data = new ArrayList<>();
        if (obj.ndc11 != null) {
            data.add(new HeaderObject("NDC11 (National Drug Code): "));
            data.add(new SimpleString(obj.ndc11));
        }

        append(data, "Id", "" + obj.id);
        append(data, "Part", "" + obj.getPart());

        if (obj.getRelabelersNdc9() != null) {
            append(data, "Relabelers NDC9", " ");
            List<RelabelersNdc9> relabelersNdc9 = obj.getRelabelersNdc9();

            for (int i = 0; i < relabelersNdc9.size(); i++) {
                if (relabelersNdc9.get(i) != null) {
                    SpannableStringBuilder mm = new SpannableStringBuilder();
                    final String sourceNdc9 = relabelersNdc9.get(i).sourceNdc9;
                    mm.append(Arrays.toString(relabelersNdc9.get(i).ndc9)).append("\n");

                    append(data, "\t@sourceNdc9", sourceNdc9);
                    append(data, "\tndc9", "" + mm);
                }
            }
        }

        if (obj.rxcui != null) {
            data.add(new HeaderObject(context.getString(R.string.rxcui_label)));
            data.add(new RxCuiObjString(obj.rxcui));
        }


        append(data, "AcqDate", obj.acqDate);

        data.add(new HeaderObject("Name"));
        data.add(new HeaderObject(obj.name));

        data.add(new HeaderObject("Labeler"));
        data.add(new SimpleString(obj.getLabeler()));


        //REMOVED dappend(data, "ImageUrl", obj.imageUrl);

        append(data, "ImageSize", String.valueOf(obj.imageSize));
        append(data, "Attribution", obj.attribution);

        if (obj.mpc != null) {
            data.add(new HeaderObject("Physical characteristics (MPC)"));
            data.add(new MpcObj(MpcField.SHAPE, obj.mpc.shape));
            data.add(new MpcObj(MpcField.SIZE, String.valueOf(obj.mpc.size)));
            data.add(new MpcObj(MpcField.COLOR, String.valueOf(obj.mpc.color)));
            data.add(new MpcObj(MpcField.IMPRINT, String.valueOf(obj.mpc.imprint)));

            data.add(new MpcObj(MpcField.IMPRINT_COLOR, String.valueOf(obj.mpc.imprintColor)));
            data.add(new MpcObj(MpcField.IMPRINT_TYPE, String.valueOf(obj.mpc.imprintType)));
            data.add(new MpcObj(MpcField.SYMBOL, String.valueOf(obj.mpc.symbol)));
            data.add(new MpcObj(MpcField.SCORE, String.valueOf(obj.mpc.score)));
        }

        if (obj.ingredients != null) {


            int c0 = (obj.ingredients.active == null) ? 0 : obj.ingredients.active.size();
            int c1 = (obj.ingredients.inactive == null) ? 0 : obj.ingredients.inactive.size();
            data.add(new HeaderObject("Ingredients: (" + c0 + ", " + c1 + ")"));
            List<String> active = obj.ingredients.active;
            if (active != null && obj.ingredients.active.size() > 0) {
                data.add(new HeaderObject("\tActive"));
                for (int i = 0; i < obj.ingredients.active.size(); i++) {
                    String k = "" + (i + 1);
                    data.add(new IngredientString(k, active.get(i)));//)
                }

            }
            List<String> bb = obj.ingredients.inactive;
            if (bb != null && bb.size() > 0) {
                data.add(new HeaderObject("\tInactive"));

                for (int i = 0; i < bb.size(); i++) {
                    String k = "" + (i + 1);
                    data.add(new IngredientString(k, bb.get(i)));//)
                }
            }
        }

        append(data, "SplSetId", obj.splSetId);
        append(data, "SplRootId", obj.getSplRootId());
        append(data, "SplVersion", ((obj.getSplVersion() == null) ? null : "" + obj.getSplVersion()));

//        if (BuildConfig.DEBUG) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            DLog.d(gson.toJson(obj));
//
//        }
        return data;
    }

    public static SpannableStringBuilder wrapperText(Context context, NlmRxImage obj) {
        SpannableStringBuilder value = new SpannableStringBuilder();
        if (obj.ndc11 != null) {
            value.append("<br />").append("<b>")
                    .append("NDC11 (National Drug Code)")
                    .append(": </b>")
                    .append(obj.ndc11)
                    .setSpan(new ForegroundColorSpan(Color.BLACK), 0, obj.ndc11.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            value.setSpan(new BackgroundColorSpan(Color.YELLOW), 0, obj.ndc11.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        append(value, "Id", "" + obj.id);
        append(value, "Part", "" + obj.getPart());

        if (obj.getRelabelersNdc9() != null) {
            append(value, "Relabelers NDC9", " ");
            List<RelabelersNdc9> relabelersNdc9 = obj.getRelabelersNdc9();

            for (int i = 0; i < relabelersNdc9.size(); i++) {
                if (relabelersNdc9.get(i) != null) {
                    SpannableStringBuilder mm = new SpannableStringBuilder();
                    final String sourceNdc9 = relabelersNdc9.get(i).sourceNdc9;
                    mm.append(Arrays.toString(relabelersNdc9.get(i).ndc9)).append("\n");

                    append(value, "\t@sourceNdc9", sourceNdc9);
                    append(value, "\tndc9", "" + mm);
                }
            }
        }

        //@@
        append(value, context.getString(R.string.rxcui_label), "" + obj.rxcui);
        append(value, "AcqDate", obj.acqDate);
        append(value, "Name", obj.name);
        append(value, "Labeler", obj.getLabeler());

        //REMOVED dappend(value, "ImageUrl", obj.imageUrl);

        append(value, "ImageSize", String.valueOf(obj.imageSize));
        append(value, "Attribution", obj.attribution);

        if (obj.mpc != null) {
            append(value, "\nPhysical characteristics (MPC)", " ");
            append(value, context.getString(R.string.mpc_shape), "" + obj.mpc.shape);
            append(value, context.getString(R.string.mpc_size), "" + obj.mpc.size);
            append(value, context.getString(R.string.mpc_color), "" + obj.mpc.color);
            append(value, context.getString(R.string.mpc_imprint), "" + obj.mpc.imprint);
            append(value, context.getString(R.string.mpc_imprint_color), "" + obj.mpc.imprintColor);
            append(value, context.getString(R.string.mpc_imprint_type), "" + obj.mpc.imprintType);
            append(value, context.getString(R.string.mpc_symbol), "" + obj.mpc.symbol);
            append(value, context.getString(R.string.mpc_score), "" + obj.mpc.score);
        }
        if (obj.ingredients != null) {
            append(value, "Ingredients", " ");
            if (obj.ingredients.active != null && !obj.ingredients.active.isEmpty()) {
                append(value, "\tActive", obj.ingredients.active.toString());
            }
            if (obj.ingredients.inactive != null && !obj.ingredients.inactive.isEmpty()) {
                append(value, "\tInactive", obj.ingredients.inactive.toString());
            }
        }

        append(value, "SplSetId", obj.splSetId);
        append(value, "SplRootId", obj.getSplRootId());
        append(value, "SplVersion", ((obj.getSplVersion() == null) ? null : "" + obj.getSplVersion()));

//        if (BuildConfig.DEBUG) {
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            DLog.d(gson.toJson(obj));
//
//        }
        return value;
    }

    static String B_B = "";
    static String B_E = "";

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
////        if (BuildConfig.DEBUG) {
////            Gson gson = new GsonBuilder().setPrettyPrinting().create();
////            DLog.d(gson.toJson(obj));
////
////        }
//        return value;
//    }

    private static void appendText(SpannableStringBuilder value, String splVersion, @Nullable String ssss) {
        value.append("\n");
        if (splVersion != null) {
            value.append(B_B);
            value.append(new SpannableString(splVersion));
            value.append(": ").append(B_E);
        }
        if (ssss != null && !ssss.isEmpty()) {
            value.append(
                    new SpannableString(ssss)
            );
        } else {
            Spannable none = new SpannableStringBuilder("x");
            none.setSpan(new ForegroundColorSpan(Color.RED), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            value.append(none);
        }
    }

    private static void append(ArrayList<VieModel> o, String key, @Nullable String values) {

        SpannableString none;
        if (values != null && !values.isEmpty()) {
            none = new SpannableString(values);
        } else {
            none = new SpannableString("x");
            none.setSpan(new ForegroundColorSpan(Color.RED), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        o.add(new NameValue2_1(key + ":", none.toString()));
    }

    private static void append(SpannableStringBuilder value, String splVersion, @Nullable String ssss) {
        value.append("<br />");
        if (splVersion != null) {
            value.append("<b>");
            value.append(new SpannableString(splVersion));
            value.append(": </b>");
        }
        if (ssss != null && !ssss.isEmpty()) {
            value.append(
                    new SpannableString(ssss)
            );
        } else {
            Spannable none = new SpannableStringBuilder("x");
            none.setSpan(new ForegroundColorSpan(Color.RED), 0, none.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
            value.append(none);
        }
    }

    private static SpannableStringBuilder mQ(String valueOf) {
        SpannableStringBuilder pn = new SpannableStringBuilder(valueOf);
        pn.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, pn.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        pn.setSpan(new BackgroundColorSpan(Color.RED), 0, pn.length(), SPAN_EXCLUSIVE_EXCLUSIVE);
        return pn;
    }


    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static NlmRxImage wrapper0(Export aa) {
        //DLog.d("https://pillidentifier-dfb05.web.app/300/"+aa.nlmImageFileName);
        //DLog.d("https://pillidentifier-dfb05.web.app/300/"+aa.rxnavImageFileName);//with_watermark

        NlmRxImage o = new NlmRxImage();
        o.imageUrl = "https://pillidentifier-dfb05.web.app/300/" + aa.nlmImageFileName;
        o.mpc = aa.mpc;
        o.name = aa.name;
        o.labeler = aa.labeler;
        o.rxcui = aa.rxcui;
        o.part = aa.part;
        o.ndc11 = aa.ndc11;
        //o.id = aa.id.oid[0];
        o.attribution = aa.attribution;
        o.imageSize = aa.imageSize;
        o.ingredients = aa.ingredients;
        return o;
    }
}
