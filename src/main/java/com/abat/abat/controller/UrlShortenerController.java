package com.abat.abat.controller;

import com.abat.abat.model.ShortUrl;
import com.abat.abat.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);
    private final UrlShortenerService urlShortenerService;

    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<ShortUrl> createShortUrl(
            @RequestParam String originalUrl,
            @RequestParam Optional<String> customId,
            @RequestParam Optional<Long> ttl) {

        try {
            ShortUrl shortUrl = urlShortenerService.createShortUrl(originalUrl, customId, ttl);
            logger.info("Created Short URL: {}", shortUrl.getId());
            return ResponseEntity.ok(shortUrl);
        } catch (IllegalArgumentException e) {
            logger.error("Failed to create Short URL: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String id) {
        Optional<ShortUrl> shortUrl = urlShortenerService.getOriginalUrl(id);
        if (shortUrl.isPresent()) {
            logger.info("Redirecting to Original URL: {}", shortUrl.get().getOriginalUrl());
            return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortUrl.get().getOriginalUrl())).build();
        } else {
            logger.warn("Short URL not found or expired for ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable String id) {
        urlShortenerService.deleteShortUrl(id);
        logger.info("Deleted Short URL with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}

