package com.toptier.web.dto;

import com.toptier.web.entity.BoardType;

public record BoardRequest(
        Integer id,
        String title,
        String content,
        String filePath,
        String hidden,
        Integer boardType
) {
}
