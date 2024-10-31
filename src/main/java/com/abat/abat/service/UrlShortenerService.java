package com.abat.abat.service;

import com.abat.abat.model.ShortUrl;
import com.abat.abat.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlShortenerService {

    private final ShortUrlRepository repository;

    public UrlShortenerService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public ShortUrl createShortUrl(String originalUrl, Optional<String> customId, Optional<Long> ttl) {
        String id = customId.orElse(UUID.randomUUID().toString().substring(0, 8));
        if (repository.existsById(id)) {
            throw new IllegalArgumentException("ID already in use.");
        }
        LocalDateTime expirationDate = ttl.map(seconds -> LocalDateTime.now().plusSeconds(seconds)).orElse(null);
        ShortUrl shortUrl = new ShortUrl(id, originalUrl, expirationDate);
        return repository.save(shortUrl);
    }

    public Optional<ShortUrl> getOriginalUrl(String id) {
        return repository.findByIdAndExpirationDateAfter(id, LocalDateTime.now());
    }

    public void deleteShortUrl(String id) {
        repository.deleteById(id);
    }
}
