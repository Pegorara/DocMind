package com.pegorara.docmind.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Component
public class LocalDiskDocumentStorage implements DocumentStorage {

    private final Path baseDirectory;

    public LocalDiskDocumentStorage(@Value("${docmind.storage.local.base-path}") String basePath) {
        this.baseDirectory = Path.of(basePath);
        try {
            Files.createDirectories(this.baseDirectory);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not create storage directory: " + basePath, e);
        }
    }

    @Override
    public String save(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path targetPath = baseDirectory.resolve(fileName);

        try {
            file.transferTo(targetPath);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to store file: " + fileName, e);
        }

        return targetPath.toString();
    }

}