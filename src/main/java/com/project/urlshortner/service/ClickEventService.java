package com.project.urlshortner.service;

import com.project.urlshortner.entities.ClickEvent;
import com.project.urlshortner.repository.ClickEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClickEventService {

    public final ClickEventRepository clickEventRepository;

    @Async
    public void logClick(String shortCode, String ipAddress, String userAgent, String referrer){
        clickEventRepository.save(
                ClickEvent.builder()
                        .shortCode(shortCode)
                        .ipAddress(ipAddress)
                        .userAgent(userAgent)
                        .referrer(referrer)
                        .deviceType(parseDeviceType(userAgent))
                        .clickedAt(LocalDateTime.now())
                        .build()
        );


    }

    private String parseDeviceType(String userAgent) {
        if (userAgent == null) return "unknown";
        String ua = userAgent.toLowerCase();
        if (ua.contains("mobile") || ua.contains("android") || ua.contains("iphone")) {
            return "mobile";
        } else if (ua.contains("tablet") || ua.contains("ipad")) {
            return "tablet";
        } else {
            return "desktop";
        }
    }
}
