package com.mindhub.homebanking.Utilities;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.models.Transaction;

import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Stream;

public class CreatePDF {
    public static void createPDF (Set<Transaction> transactionArray) throws Exception {
        var doc = new Document();

        String route = System.getProperty("user.home");
        PdfWriter.getInstance(doc, new FileOutputStream("Your-transactions.pdf"));
        PdfWriter.getInstance(doc, new FileOutputStream(route + "/Downloads/TransactionInfo.pdf"));

        doc.open();

        var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        var paragraph = new Paragraph(Paragraph.ALIGN_CENTER, "Your transactions", bold);
        var table = new PdfPTable(4);
        Stream.of("Hello world").forEach(table::addCell);

        transactionArray.forEach(transaction -> {
            table.addCell("$" +transaction.getAmount());
            table.addCell(transaction.getDescription());
            table.addCell(transaction.getType()+"");
            table.addCell(transaction.getDate().format(DateTimeFormatter.BASIC_ISO_DATE));
        });

        paragraph.add(table);
        doc.add(paragraph);
        doc.close();


    }
/*
    var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        try
    {
        Image foto = Image.getInstance("C:\\Users\\leand\\Desktop\\MindHub\\FASE 4 - JAVA\\homebanking\\homebanking_CON_H2\\homebanking\\src\\main\\resources\\static\\web\\assets\\img\\Nuevo proyecto2.png");
        foto.scaleToFit(100, 100);
        foto.setAlignment(Chunk.ALIGN_MIDDLE);
        doc.add(foto);
    }
        catch ( Exception e )
    {
        e.printStackTrace();
    }*/
}
