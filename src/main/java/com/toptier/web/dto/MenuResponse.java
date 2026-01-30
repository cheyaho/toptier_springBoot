package com.toptier.web.dto;

import com.toptier.web.entity.Menu;

public record MenuResponse (
    int id,
    CategoryResponse category,
    String name_kr,
    String name_en,
    String imagePath,
    String thumbPath,
    String description,
    String signatureYN,
    String bestYN,
    String newYN,
    String status
){
    public static MenuResponse from(Menu menu) {
        return new MenuResponse(
            menu.getId(),
            menu.getCategory() == null ? null : CategoryResponse.from(menu.getCategory()),
            menu.getNameKr(),
            menu.getNameEn(),
            (menu.getImagePath() == null) ? "" : menu.getImagePath(),
            (menu.getThumbPath() == null) ? "" : menu.getThumbPath(),
            (menu.getDescription() == null) ? "" : menu.getDescription(),
            (menu.getSignatureYN() == null) ? "N" : menu.getSignatureYN(),
            (menu.getBestYN() == null) ? "N" : menu.getBestYN(),
            (menu.getNewYN() == null) ? "N" : menu.getNewYN(),
            (menu.getStatus() == null) ? "1" : menu.getStatus()
        );
    }
}
