package com.toptier.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.toptier.web.entity.Popup;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PopupResponse (
        Integer id,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate sDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate eDate,
        Integer width,
        Integer height,
        String cookie
){
    public static PopupResponse from(Popup popup){
        return new PopupResponse(
                popup.getId(),
                popup.getTitle(),
                popup.getContent(),
                popup.getStartDate() != null ? popup.getStartDate().toLocalDate() : null,
                popup.getEndDate() != null ? popup.getEndDate().toLocalDate() : null,
                popup.getWidth(),
                popup.getHeight(),
                popup.getCookie()
        );
    }
}
