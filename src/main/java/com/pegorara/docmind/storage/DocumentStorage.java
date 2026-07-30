package com.pegorara.docmind.storage;

import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorage {

    String save(MultipartFile file);
}
