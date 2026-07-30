package com.pegorara.docmind.service;

import com.pegorara.docmind.dto.DocumentUploadResponse;
import com.pegorara.docmind.entity.Document;
import com.pegorara.docmind.entity.Job;
import com.pegorara.docmind.entity.JobStatus;
import com.pegorara.docmind.repository.DocumentRepository;
import com.pegorara.docmind.repository.JobRepository;
import com.pegorara.docmind.storage.DocumentStorage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentUploadService {

    private final DocumentUploadValidator validator;
    private final DocumentStorage documentStorage;
    private final DocumentRepository documentRepository;
    private final JobRepository jobRepository;

    public DocumentUploadService(DocumentUploadValidator validator,
                                 DocumentStorage documentStorage,
                                 DocumentRepository documentRepository,
                                 JobRepository jobRepository) {
        this.validator = validator;
        this.documentStorage = documentStorage;
        this.documentRepository = documentRepository;
        this.jobRepository = jobRepository;
    }

    @Transactional
    public DocumentUploadResponse upload(MultipartFile file) {
        validator.validate(file);

        String storagePath = documentStorage.save(file);

        Document document = documentRepository.save(
                Document.builder()
                        .originalFileName(file.getOriginalFilename())
                        .storagePath(storagePath)
                        .sizeInBytes(file.getSize())
                        .build()
        );

        Job job = jobRepository.save(
                Job.builder()
                        .document(document)
                        .status(JobStatus.PENDING)
                        .build()
        );

        return new DocumentUploadResponse(
                document.getId(),
                job.getId(),
                document.getOriginalFileName(),
                job.getStatus(),
                job.getCreatedAt()
        );
    }

}