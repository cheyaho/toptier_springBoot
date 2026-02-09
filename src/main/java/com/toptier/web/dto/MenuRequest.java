package com.toptier.web.dto;

public record MenuRequest(
        Integer id,
        Integer category,
        String name_kr,
        String name_en,
        String imagePath,
        String chgImg,
        String description,
        String signatureYN,
        String bestYN,
        String newYN,
        String status
) {

}
