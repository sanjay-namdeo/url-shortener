package com.example.urlshortner.repository;

import com.example.urlshortner.entity.ShortURL;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRepository extends CrudRepository<ShortURL, Long> {
    ShortURL findByShortUrl(String shortUrl);
    ShortURL findByOriginalUrl(String originalUrl);
}
