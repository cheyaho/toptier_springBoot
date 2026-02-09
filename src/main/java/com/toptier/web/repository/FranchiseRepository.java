package com.toptier.web.repository;

import com.toptier.web.entity.Franchise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FranchiseRepository extends JpaRepository<Franchise, Integer> {
    Page<Franchise> findAllByHidden(Pageable pageable, String n);
}
