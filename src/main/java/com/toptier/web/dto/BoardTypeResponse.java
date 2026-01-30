package com.toptier.web.dto;

import com.toptier.web.entity.BoardType;

public record BoardTypeResponse(
        int seq,
        String typeName,
        String pathName
) {
    public static BoardTypeResponse from(BoardType boardType) {
        return new BoardTypeResponse(
                boardType.getSeq(),
                boardType.getTypeName(),
                boardType.getPathName()
        );
    }
}
