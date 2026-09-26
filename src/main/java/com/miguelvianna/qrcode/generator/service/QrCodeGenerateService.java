package com.miguelvianna.qrcode.generator.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.miguelvianna.qrcode.generator.dto.QrCodeGenerateResponse;
import com.miguelvianna.qrcode.generator.ports.StoragePort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class QrCodeGenerateService {
    private final StoragePort storage;

    public QrCodeGenerateService(StoragePort storage) {
        this.storage = storage;
    }

    public QrCodeGenerateResponse generateAndUploadQrCode(String text) throws WriterException, IOException {
        //Generate QrCode
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        //Upload to storage service
        String url = storage.uploadFile(pngQrCodeData, UUID.randomUUID().toString(), "imapge/png");

        return new QrCodeGenerateResponse(url);
    }
}
