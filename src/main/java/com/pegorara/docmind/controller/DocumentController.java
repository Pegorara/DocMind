package com.pegorara.docmind.controller;

import com.pegorara.docmind.dto.DocumentUploadResponse;
import com.pegorara.docmind.service.DocumentUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final DocumentUploadService documentUploadService;

    public DocumentController(DocumentUploadService documentUploadService) {
        this.documentUploadService = documentUploadService;
    }

    @PostMapping
    public ResponseEntity<DocumentUploadResponse> upload(@RequestParam("file") MultipartFile file) {
        DocumentUploadResponse response = documentUploadService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}