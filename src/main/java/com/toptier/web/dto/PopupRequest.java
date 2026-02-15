package com.toptier.web.dto;

import java.time.LocalDateTime;

public record PopupRequest(
        int id,
        String title,
        String content,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int width,
        int height,
        String useYn,
        String cookie
) {
}
