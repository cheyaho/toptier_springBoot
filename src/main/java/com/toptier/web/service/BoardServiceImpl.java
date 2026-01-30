package com.toptier.web.service;

import com.toptier.web.dto.AddBoardRequest;
import com.toptier.web.dto.BoardResponse;
import com.toptier.web.dto.BoardTypeResponse;
import com.toptier.web.entity.Board;
import com.toptier.web.repository.BoardRepository;
import com.toptier.web.repository.BoardTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;
    private final BoardTypeRepository boardTypeRepository;

    private final int pageSize = 10;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getBoardList(String path, Pageable pageable) throws Exception {
        // 게시판 타입 조회
        BoardTypeResponse boardType = boardTypeRepository.findByPathName(path)
                .map(BoardTypeResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다."));

        // 게시물 목록 조회
        Page<BoardResponse> boardList = boardRepository.findByBoardType_seq(boardType.seq(), pageable)
                .map(BoardResponse::from);

        return boardList;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponse getBoardDetail(Integer seq) {
        Board board = boardRepository.findBySeq(seq)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        return BoardResponse.from(board);
    }

    @Override
    @Transactional
    public Integer createBoard(AddBoardRequest reqBoard) {
        Board board = new Board(reqBoard);
        Board savedBoard = boardRepository.save(board);
        boardRepository.flush();
        return savedBoard.getSeq();
    }

    @Override
    public Integer updateBoard(Integer seq, AddBoardRequest reqBoard) {
        Board board = boardRepository.findBySeq(seq)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        board.update(reqBoard);
//        boardRepository.save(board);
        return board.getSeq();
    }

    @Override
    public void deleteBoard(Integer seq) {
        boardRepository.deleteBySeq(seq);
    }

    @Override
    public void increaseHits(Integer seq) {
        Board board = boardRepository.findBySeq(seq)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        board.setHits(board.getHits() + 1);
        boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getAllBoard(int type, Pageable pageable) {
        if (type == 0) {
            Page<Board> boardList = boardRepository.findAll(pageable);
            return boardList.map(BoardResponse::from);
        } else {
            Page<Board> boardList = boardRepository.findByBoardType_Seq(type, pageable);
            return boardList.map(BoardResponse::from);
        }
    }
}
