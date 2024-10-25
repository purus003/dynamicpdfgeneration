package com.example.pdf.service;

import com.example.pdf.model.InvoiceData;
import com.example.pdf.model.Item;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import jakarta.servlet.http.HttpServletResponse; 

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public void generateInvoicePdf(InvoiceData invoiceRequest, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.setMargins(20, 20, 20, 20);

       
        document.add(new Paragraph("Invoice").setBold().setFontSize(16).setPaddingBottom(10));

        
        Table detailsTable = new Table(UnitValue.createPercentArray(new float[]{3, 3})); 
        detailsTable.setWidth(UnitValue.createPercentValue(100));

        String sellerDetails = "Seller:\n" + invoiceRequest.getSeller() + "\n" +
                               "Address: " + invoiceRequest.getSellerAddress() + "\n" +
                               "GSTIN: " + invoiceRequest.getSellerGstin();
        detailsTable.addCell(new Paragraph(sellerDetails).setPadding(10));
        
     
        String buyerDetails = "Buyer:\n" + invoiceRequest.getBuyer() + "\n" +
                              "Address: " + invoiceRequest.getBuyerAddress() + "\n" +
                              "GSTIN: " + invoiceRequest.getBuyerGstin();
        detailsTable.addCell(new Paragraph(buyerDetails).setPadding(10));

     
        document.add(detailsTable);

       
        Table itemTable = new Table(UnitValue.createPercentArray(new float[]{3, 1, 1, 1})); 
        itemTable.setWidth(UnitValue.createPercentValue(100));
        
        itemTable.addCell(new Paragraph("Product Name").setBold());
        itemTable.addCell(new Paragraph("Quantity").setBold());
        itemTable.addCell(new Paragraph("Rate").setBold());
        itemTable.addCell(new Paragraph("Amount").setBold());

     
        for (Item item : invoiceRequest.getItems()) {
            itemTable.addCell(new Paragraph(item.getName())); 
            itemTable.addCell(new Paragraph(String.valueOf(item.getQuantity()))); 
            itemTable.addCell(new Paragraph(String.valueOf(item.getRate()))); 
            itemTable.addCell(new Paragraph(String.valueOf(item.getAmount())));
        }

        itemTable.addCell(new Paragraph("")); 
        itemTable.addCell(new Paragraph("")); 
        itemTable.addCell(new Paragraph("")); 
        itemTable.addCell(new Paragraph("").setHeight(15f)); 

       
        document.add(itemTable);
        document.close();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + invoiceRequest.getBuyer() + "_invoice.pdf");

        
        baos.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }
}
