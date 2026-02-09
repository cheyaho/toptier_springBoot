package com.toptier.web.dto;

import com.toptier.web.entity.Board;
import com.toptier.web.entity.BoardType;

import java.time.LocalDateTime;

public record BoardResponse(
        Integer id,
        BoardTypeResponse type,
        String title,
        String content,
        String filePath,
        int hits,
        String hidden,
        LocalDateTime insDate
) {
    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                BoardTypeResponse.from(board.getBoardType()),
                board.getTitle(),
                board.getContent(),
                board.getFilePath(),
                board.getHits(),
                board.getHidden(),
                board.getInsDate()
        );
    }
}
