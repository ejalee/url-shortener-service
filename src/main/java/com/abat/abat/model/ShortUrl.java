package com.abat.abat.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ShortUrl {

    @Id
    private String id;

    private String originalUrl;

    private LocalDateTime expirationDate;

    public ShortUrl() {}

    public ShortUrl(String id, String originalUrl, LocalDateTime expirationDate) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.expirationDate = expirationDate;
    }

    // Getters and Setters
}
