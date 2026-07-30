package com.pegorara.docmind.dto;

import com.pegorara.docmind.entity.JobStatus;

import java.time.Instant;
import java.util.UUID;

public record DocumentUploadResponse(
        UUID documentId,
        UUID jobId,
        String originalFileName,
        JobStatus status,
        Instant createdAt
) {
}
