package com.toptier.web.service;

import com.toptier.web.dto.AddBoardRequest;
import com.toptier.web.dto.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface BoardService {
    Page<BoardResponse> getBoardList(String path, Pageable pageable) throws Exception;
    BoardResponse getBoardDetail(Integer seq);
    Integer createBoard(AddBoardRequest reqBoard);
    Integer updateBoard(Integer seq, AddBoardRequest reqBoard);
    void deleteBoard(Integer seq);
    void increaseHits(Integer seq);

    Page<BoardResponse> getAllBoard(int type, Pageable pageable);
}
