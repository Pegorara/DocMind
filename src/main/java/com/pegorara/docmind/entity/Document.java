package com.pegorara.docmind.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String storagePath;

    @Column(nullable = false)
    private Long sizeInBytes;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }
}
