package com.project.urlshortner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenResponse {

    private String shortCode;
    private String shortUrl;
    private String originalUrl;

}
