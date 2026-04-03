package com.project.urlshortner.controller;

import com.project.urlshortner.dto.ShortenRequest;
import com.project.urlshortner.dto.ShortenResponse;
import com.project.urlshortner.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@RequestBody ShortenRequest shortenRequest){
        return ResponseEntity.ok(urlService.shortenUrl(shortenRequest));
    }
}
