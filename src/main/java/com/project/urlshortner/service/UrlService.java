package com.project.urlshortner.service;

import com.project.urlshortner.dto.ShortenRequest;
import com.project.urlshortner.dto.ShortenResponse;
import com.project.urlshortner.entities.Urls;
import com.project.urlshortner.repository.UrlRepository;
import com.project.urlshortner.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor  // Lombok annotation to generate a constructor with required arguments (final fields)
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;
    private final RedisTemplate<String, String> redisTemplate;
    private final ClickEventService clickEventService;

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
                .shortUrl("http://localhost:8080/api/" + shortCode)
                .originalUrl(shortenRequest.getOriginalUrl())
                .build();
    }

    public String getOriginalUrl(String shortCode, String ipAddress,
                                 String userAgent, String referrer)
    {
        String cachedUrl = redisTemplate.opsForValue().get(shortCode); // Check Redis cache first, return if found
        if (cachedUrl != null) {
            clickEventService.logClick(shortCode, ipAddress, userAgent, referrer);
            return cachedUrl;
        }

        Urls url = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("Short code not found: " + shortCode));
        redisTemplate.opsForValue().set(shortCode, url.getOriginalUrl(), 24, TimeUnit.HOURS);

        clickEventService.logClick(shortCode, ipAddress, userAgent, referrer);
        return url.getOriginalUrl();
    }
}
