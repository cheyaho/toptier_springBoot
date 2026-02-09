package com.toptier.web.repository;

import com.toptier.web.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Supplier;

public interface BoardTypeRepository extends JpaRepository<BoardType, Integer> {
    Optional<BoardType> findByPathName(String path);
    Optional<BoardType> findById(int i);
}
