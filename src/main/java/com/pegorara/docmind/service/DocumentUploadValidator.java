package com.pegorara.docmind.service;

import com.pegorara.docmind.exception.InvalidDocumentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DocumentUploadValidator {

    private static final String PDF_CONTENT_TYPE = "application/pdf";

    private final long maxSizeInBytes;

    public DocumentUploadValidator(@Value("${docmind.upload.max-size-bytes}") long maxSizeInBytes) {
        this.maxSizeInBytes = maxSizeInBytes;
    }

    public void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new InvalidDocumentException("Uploaded file must not be empty");
        }

        if (!PDF_CONTENT_TYPE.equals(file.getContentType())) {
            throw new InvalidDocumentException("Only PDF files are supported, received: " + file.getContentType());
        }

        if (file.getSize() > maxSizeInBytes) {
            throw new InvalidDocumentException(
                    "File exceeds maximum allowed size of " + maxSizeInBytes + " bytes"
            );
        }
    }

}