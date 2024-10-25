package com.example.pdf.controller;

import com.example.pdf.model.InvoiceData;
import com.example.pdf.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/generate")
    public void generatePdf(@RequestBody InvoiceData invoiceRequest, HttpServletResponse response) {
        try {
            pdfService.generateInvoicePdf(invoiceRequest, response);
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
