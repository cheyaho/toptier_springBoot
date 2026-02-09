package com.toptier.web.service;

import com.toptier.web.common.utils.FileUtil;
import com.toptier.web.dto.BoardRequest;
import com.toptier.web.dto.BoardResponse;
import com.toptier.web.dto.BoardTypeResponse;
import com.toptier.web.dto.FileUploadResponse;
import com.toptier.web.entity.Board;
import com.toptier.web.entity.BoardType;
import com.toptier.web.repository.BoardRepository;
import com.toptier.web.repository.BoardTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        Page<BoardResponse> boardList = boardRepository.findByBoardType_id(boardType.id(), pageable)
                .map(BoardResponse::from);

        return boardList;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponse getBoardDetail(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        return BoardResponse.from(board);
    }

    @Override
    @Transactional
    public Integer createBoard(BoardRequest reqBoard, MultipartFile file) throws Exception {
        BoardType boardType = boardTypeRepository.findById(reqBoard.boardType()).orElse(null);
        Board board = new Board(reqBoard, boardType);
        Board savedBoard = boardRepository.save(board);
        Integer id = savedBoard.getId();
        if(file != null && !file.isEmpty()){
            String path = "/files/board/" + String.valueOf(id) + "/";
            FileUploadResponse uploadResult = FileUtil.fileUploader(file, path);
            if(uploadResult.uploaded()){
                savedBoard.setFilePath(uploadResult.url());
            }
        }
        boardRepository.flush();
        return id;
    }

    @Override
    public void updateBoard(Integer id, BoardRequest reqBoard, MultipartFile file) throws Exception {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        BoardType boardType = boardTypeRepository.findById(reqBoard.boardType()).orElse(null);
        board.update(reqBoard, boardType);
        if(file != null && !file.isEmpty()){
            String path = "/files/board/" + id + "/";
            FileUploadResponse uploadResult = FileUtil.fileUploader(file, path);
            if(uploadResult.uploaded()){
                board.setFilePath(uploadResult.url());
            }
        }
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(Integer id) {
        boardRepository.deleteById(id);
    }

    @Override
    public void increaseHits(Integer id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다."));
        board.setHits(board.getHits() + 1);
        boardRepository.save(board);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getAllBoard(Pageable pageable, Integer type) {
        if (type == null || type == 0) {
            Page<Board> boardList = boardRepository.findAll(pageable);
            return boardList.map(BoardResponse::from);
        } else {
            Page<Board> boardList = boardRepository.findByBoardType_id(type, pageable);
            return boardList.map(BoardResponse::from);
        }
    }
}
