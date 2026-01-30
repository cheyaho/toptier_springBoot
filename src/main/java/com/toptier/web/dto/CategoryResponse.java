package com.toptier.web.dto;

import com.toptier.web.entity.MenuCategory;

public record CategoryResponse(
        int id,
        String cate,
        String parent,
        String categoryName
) {
    public static CategoryResponse from(MenuCategory category) {
        return new CategoryResponse(
                category.getId(),
                category.getCate(),
                category.getParent(),
                category.getCategoryName()
        );
    }
}
