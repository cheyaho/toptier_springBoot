package com.toptier.web.dto;

import com.toptier.web.entity.BoardType;

public record BoardTypeResponse(
        int id,
        String typeName,
        String pathName
) {
    public static BoardTypeResponse from(BoardType boardType) {
        return new BoardTypeResponse(
                boardType.getId(),
                boardType.getTypeName(),
                boardType.getPathName()
        );
    }
}
