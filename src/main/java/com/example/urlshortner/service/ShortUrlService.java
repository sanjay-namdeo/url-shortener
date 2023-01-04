package com.example.urlshortner.service;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

import com.example.urlshortner.entity.ShortURL;
import com.example.urlshortner.repository.ShortUrlRepository;

@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository = repository;
    }

    public String checkIfUrlExists(String url) {
        ShortURL shortUrl = repository.findByOriginalUrl(url);
        if (shortUrl != null) {
            return shortUrl.getShortUrl();
        } else {
            return null;
        }
    }

    public String generateAndSaveShortUrl(String originalUrl) {
        String shortUrl = this.checkIfUrlExists(originalUrl);
        if (shortUrl != null) {
            return shortUrl;
        } else {
            shortUrl = this.generateShortUrl(originalUrl);
            repository.save(new ShortURL(originalUrl, shortUrl));
            return shortUrl;
        }
    }

    public String getOriginalUrl(String shortUrl) {
        ShortURL shortened = repository.findByShortUrl(shortUrl);
        if (shortened != null) {
            return shortened.getOriginalUrl();
        } else {
            return null;
        }
    }

    private String generateShortUrl(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(url.getBytes("UTF-8"));
            StringBuilder shortenedString = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                // convert each byte to a hexadecimal string and append to the shortenedString
                shortenedString.append(String.format("%02x", hash[i]));
            }
            return shortenedString.toString().substring(0,7);
        } catch (Exception e) {
            throw new RuntimeException("Error generating shortened string", e);
        }
    }
}