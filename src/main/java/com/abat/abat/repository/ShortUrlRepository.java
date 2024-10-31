package com.abat.abat.repository;

import com.abat.abat.model.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {
    Optional<ShortUrl> findByIdAndExpirationDateAfter(String id, LocalDateTime now);
}

