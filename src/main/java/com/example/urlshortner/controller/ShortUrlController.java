package com.example.urlshortner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.urlshortner.service.ShortUrlService;

@RestController
public class ShortUrlController {
    private ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String url) {
        String shortUrl = shortUrlService.generateAndSaveShortUrl(url);

        if (shortUrl != null) {
            return ResponseEntity.ok(shortUrl);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
        String url = shortUrlService.getOriginalUrl(shortUrl);

        if (url == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(url);
        }
    }
}
