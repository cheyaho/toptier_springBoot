package com.toptier.web.dto;

import com.toptier.web.entity.BoardType;

public record AddBoardRequest(
        String title,
        String content,
        String filePath,
        String hidden,
        BoardType boardType
) {
}
