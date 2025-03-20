package com.walhalla;

import android.content.Context;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.walhalla.pillfinder.R;

import gov.nih.nlm.model.NlmRxImage;
import gov.nih.nlm.model.RelabelersNdc9;


public class PDFUtil2 {

    //private static final String _FONT_ = "res/font/opensansregular.ttf";
    private static final String _FONT_ = "assets/fonts/opensansregular.ttf";

    private static final String EMPTY_STRING = "";

    public static void makePDF(Context context, OutputStream fos, ByteArrayOutputStream stream, NlmRxImage obj) throws DocumentException, IOException {
        String appName = context.getString(R.string.app_name);
        Document value = new Document();
        PdfWriter.getInstance(value, fos);

        value.open();
        value.setPageSize(PageSize.A4);
        value.addCreationDate();
        value.addAuthor(appName);
        value.addCreator(appName);
        BaseColor colorAccent = new BaseColor(255, 0, 0, 255);

        float headingFontSize = 24.0f;
        float valueFontSize = 22.0f;

//        int res = context.getResources().getIdentifier("opensansregular", "font", context.getPackageName());
//        Toast.makeText(context, "[font]" + res, Toast.LENGTH_SHORT).show();
//        Typeface aa = ResourcesCompat.getFont(context, R.font.opensansregular);
//        if (aa != null) {
//            boolean aaj = aa.isBold();
//        }
        BaseFont baseFont = BaseFont.createFont(_FONT_, "UTF-8", BaseFont.EMBEDDED);

        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

        //Title....
        Font font = new Font(baseFont, 36.0f, Font.BOLD, BaseColor.BLACK);
        if (!TextUtils.isEmpty(obj.getLabeler())) {
            Chunk chunk = new Chunk(obj.getLabeler(), font);
            Paragraph paragraph = new Paragraph(chunk);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            value.add(paragraph);
        }

        value.add(new Paragraph(EMPTY_STRING));
        value.add(Chunk.NEWLINE);
        value.add(new Paragraph(EMPTY_STRING));
        value.add(new Paragraph(EMPTY_STRING));
        value.add(Chunk.NEWLINE);
        value.add(new Paragraph(EMPTY_STRING));


        Image codeImage = Image.getInstance(stream.toByteArray());
        codeImage.setAlignment(Image.ALIGN_CENTER);
        codeImage.scalePercent(40);
        Paragraph imageParagraph = new Paragraph();
        imageParagraph.add(codeImage);
        value.add(imageParagraph);

        value.add(new Paragraph(EMPTY_STRING));
        value.add(Chunk.NEWLINE);
        value.add(new Paragraph(EMPTY_STRING));

        // Adding Chunks for Title and value
        Font mOrderIdFont = new Font(baseFont, headingFontSize, Font.BOLD, colorAccent);

        //mOrderIdChunk.setBackground(colorAccent);


        value.add(new Paragraph(new Chunk("Content:", mOrderIdFont)));

        Font mOrderIdValueFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);

        if (!TextUtils.isEmpty(obj.getLabeler())) {
            Chunk chunk = new Chunk(obj.getLabeler(), mOrderIdValueFont);
            Paragraph paragraph = new Paragraph(chunk);
            value.add(paragraph);
        }


        value.add(new Paragraph(EMPTY_STRING));
        value.add(Chunk.NEWLINE);
        value.add(new Paragraph(EMPTY_STRING));

        Font mOrderDateFont = new Font(baseFont, headingFontSize, Font.BOLD, colorAccent);
        Font mOrderDateValueFont = new Font(baseFont, valueFontSize, Font.NORMAL, BaseColor.BLACK);

        if (!TextUtils.isEmpty(obj.name)) {
            Chunk name = new Chunk("Name", mOrderDateFont);
            value.add(new Paragraph(name));
            Chunk chunk = new Chunk(obj.name, mOrderDateValueFont);
            value.add(new Paragraph(chunk));
        }

        Chunk mOrderDateChunk = new Chunk("NDC11 (National Drug Code)", mOrderDateFont);
        Paragraph mOrderDateParagraph = new Paragraph(mOrderDateChunk);
        value.add(mOrderDateParagraph);
        Chunk mOrderDateValueChunk = new Chunk(obj.ndc11, mOrderDateValueFont);
        Paragraph mOrderDateValueParagraph = new Paragraph(mOrderDateValueChunk);
        value.add(mOrderDateValueParagraph);


        value.add(new Paragraph(new Chunk("Id", mOrderDateFont)));
        value.add(new Paragraph(new Chunk(obj.id.toString(), mOrderDateValueFont)));


        //0
        Chunk mOrderDateChunk0 = new Chunk("Part", mOrderDateFont);
        value.add(new Paragraph(mOrderDateChunk0));

        Chunk mOrderDateValueChunk0 = new Chunk(obj.getPart().toString(), mOrderDateValueFont);
        value.add(new Paragraph(mOrderDateValueChunk0));


        if (obj.getRelabelersNdc9() != null) {
            dappend(value, "Relabelers NDC9", " ", mOrderDateFont);

            List<RelabelersNdc9> relabelersNdc9 = obj.getRelabelersNdc9();
            for (int i = 0; i < relabelersNdc9.size(); i++) {
                if (relabelersNdc9.get(i) != null) {
                    SpannableStringBuilder mm = new SpannableStringBuilder();
                    final String sourceNdc9 = relabelersNdc9.get(i).sourceNdc9;
                    mm.append(Arrays.toString(relabelersNdc9.get(i).ndc9)).append("\n");

                    dappend(value, "\t@sourceNdc9", sourceNdc9, mOrderDateValueFont);
                    dappend(value, "\tndc9", "" + mm.toString(), mOrderDateValueFont);
                }
            }
        }

        if (obj.rxcui != null) {
            Chunk chunk = new Chunk(context.getString(R.string.rxcui_label), mOrderDateFont);
            value.add(new Paragraph(chunk));
            Chunk chunk1 = new Chunk("" + obj.rxcui, mOrderDateValueFont);
            value.add(new Paragraph(chunk1));
        }

        Chunk ch2 = new Chunk("AcqDate", mOrderDateFont);
        value.add(new Paragraph(ch2));
        Chunk mOrderDateValueChunk03 = new Chunk(obj.acqDate, mOrderDateValueFont);
        value.add(new Paragraph(mOrderDateValueChunk03));

        Chunk ch4 = new Chunk("Labeler", mOrderDateFont);
        value.add(new Paragraph(ch4));
        Chunk mOrderDateValue = new Chunk(obj.getLabeler(), mOrderDateValueFont);
        value.add(new Paragraph(mOrderDateValue));
        Chunk ch5 = new Chunk("ImageUrl", mOrderDateFont);
        value.add(new Paragraph(ch5));

        //REMOVED Chunk mOrder = new Chunk(obj.imageUrl, mOrderDateValueFont);
        //REMOVED value.add(new Paragraph(mOrder));

        Chunk ch6 = new Chunk("ImageSize", mOrderDateFont);
        value.add(new Paragraph(ch6));
        Chunk mOrderDateValueChunk0z = new Chunk(String.valueOf(obj.imageSize), mOrderDateValueFont);
        value.add(new Paragraph(mOrderDateValueChunk0z));
        Chunk ch7 = new Chunk("Attribution", mOrderDateFont);
        value.add(new Paragraph(ch7));
        Chunk mOrderDateValueChunk0xx = new Chunk(obj.attribution, mOrderDateValueFont);
        value.add(new Paragraph(mOrderDateValueChunk0xx));

        if (obj.mpc != null) {
            dappend(value, "\nPhysical characteristics (MPC)", " ", mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_shape), "" + obj.mpc.shape, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_size), "" + obj.mpc.size, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_color), "" + obj.mpc.color, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_imprint), "" + obj.mpc.imprint, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_imprint_color), "" + obj.mpc.imprintColor, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_imprint_type), "" + obj.mpc.imprintType, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_symbol), "" + obj.mpc.symbol, mOrderDateValueFont);
            dappend(value, context.getString(R.string.mpc_score), "" + obj.mpc.score, mOrderDateValueFont);
        }
        if (obj.ingredients != null) {
            dappend(value, "Ingredients", " ", mOrderDateValueFont);
            if (obj.ingredients.active != null && obj.ingredients.active.size() > 0) {
                dappend(value, "\tActive", obj.ingredients.active.toString(), mOrderDateValueFont);
            }
            if (obj.ingredients.inactive != null && obj.ingredients.inactive.size() > 0) {
                dappend(value, "\tInactive", obj.ingredients.inactive.toString(), mOrderDateValueFont);
            }
        }

        if (obj.splSetId != null) {
            dappend(value, "SplSetId", "", mOrderDateFont);
            Chunk chunk1 = new Chunk(obj.splSetId, mOrderDateValueFont);
            value.add(new Paragraph(chunk1));
        }

        if (obj.getSplRootId() != null) {
            Chunk chunk = new Chunk("SplRootId", mOrderDateFont);
            value.add(new Paragraph(chunk));
            Chunk chunk1 = new Chunk(obj.getSplRootId(), mOrderDateValueFont);
            value.add(new Paragraph(chunk1));
        }

        if (obj.getSplVersion() != null) {
            Chunk splVersion = new Chunk("SplVersion", mOrderDateFont);
            value.add(new Paragraph(splVersion));
            Chunk chunk = new Chunk("" + obj.getSplVersion(), mOrderDateValueFont);
            value.add(new Paragraph(chunk));
        }
        value.close();
    }


    private static void dappend(Document document, String h1, String s, Font mOrderDateFont) throws DocumentException {
        Chunk mOrderDateChunk0aaa = new Chunk(h1 + ": " + s, mOrderDateFont);
        document.add(new Paragraph(mOrderDateChunk0aaa));
    }
}
