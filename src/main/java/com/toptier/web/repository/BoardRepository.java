package com.toptier.web.repository;

import com.toptier.web.entity.Board;
import com.toptier.web.entity.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    Page<Board> findByBoardType_seq(int seq, Pageable pageable);

    Optional<Board> findBySeq(Integer seq);

    void deleteBySeq(Integer seq);

    Page<Board> findByBoardType_Seq(int seq, Pageable pageable);
}
