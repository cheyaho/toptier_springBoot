package com.toptier.web.service;

import com.toptier.web.dto.BoardRequest;
import com.toptier.web.dto.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {
    Page<BoardResponse> getBoardList(String path, Pageable pageable) throws Exception;
    BoardResponse getBoardDetail(Integer id);
    Integer createBoard(BoardRequest reqBoard, MultipartFile file) throws Exception;
    void updateBoard(Integer id, BoardRequest reqBoard, MultipartFile file) throws Exception;
    void deleteBoard(Integer id);
    void increaseHits(Integer id);
    Page<BoardResponse> getAllBoard(Pageable pageable, Integer type);
}
