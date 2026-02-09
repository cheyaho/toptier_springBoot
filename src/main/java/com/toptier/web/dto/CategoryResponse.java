package com.toptier.web.dto;

import com.toptier.web.entity.MenuCategory;

public record CategoryResponse(
        Integer id,
        String parent,
        String categoryName
) {
    public static CategoryResponse from(MenuCategory category) {
        return new CategoryResponse(
                category.getId(),
                category.getParent(),
                category.getCategoryName()
        );
    }
}
