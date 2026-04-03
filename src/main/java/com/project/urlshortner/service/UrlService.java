package com.project.urlshortner.service;

import com.project.urlshortner.dto.ShortenRequest;
import com.project.urlshortner.dto.ShortenResponse;
import com.project.urlshortner.entities.Urls;
import com.project.urlshortner.repository.UrlRepository;
import com.project.urlshortner.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor  // Lombok annotation to generate a constructor with required arguments (final fields)
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    public ShortenResponse shortenUrl( ShortenRequest shortenRequest) {

        Urls urlEntity =Urls.builder()
                .originalUrl(shortenRequest.getOriginalUrl())
                .createdAt(LocalDateTime.now())
                .clickCount(0L)
        .build();

        Urls urls = urlRepository.save(urlEntity);

        String shortCode = base62Encoder.encode(urls.getId());
        urls.setShortCode(shortCode);
        urlRepository.save(urls);

        return ShortenResponse.builder()
                .shortCode(shortCode)
                .shortUrl("http://localhost:8080/" + shortCode)
                .originalUrl(shortenRequest.getOriginalUrl())
                .build();
    }
}
