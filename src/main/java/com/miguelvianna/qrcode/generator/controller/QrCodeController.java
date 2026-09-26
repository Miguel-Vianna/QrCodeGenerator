package com.miguelvianna.qrcode.generator.controller;

import com.google.zxing.WriterException;
import com.miguelvianna.qrcode.generator.dto.QrCodeGenerateRequest;
import com.miguelvianna.qrcode.generator.dto.QrCodeGenerateResponse;
import com.miguelvianna.qrcode.generator.service.QrCodeGenerateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/qrcode")
public class QrCodeController {

    private final QrCodeGenerateService qrCodeGeneratorService;

    public QrCodeController(QrCodeGenerateService qrCodeService) {
        this.qrCodeGeneratorService = qrCodeService;
    }

    @PostMapping
    public ResponseEntity<QrCodeGenerateResponse> generate(@RequestBody QrCodeGenerateRequest request) {

        try {
            QrCodeGenerateResponse response = this.qrCodeGeneratorService.generateAndUploadQrCode(request.text());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
