//package com;
//
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Text;
//
//import java.io.OutputStream;
//
//public class PDFUtil1 {
//
//    public static void makePDF(OutputStream fos) {
//        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(fos));
//        Document document = new Document(pdfDocument);
//        Paragraph paragraph = new Paragraph("Hello World");
//        Text text0 = new Text("123").setBold();
//        Text text1 = new Text("123").setBold();
//        paragraph.add(text0);
//        paragraph.add(text1);
//        document.add(paragraph);
//        document.close();
//    }
//
//}
