package com.toptier.web.repository;

import com.toptier.web.dto.MenuResponse;
import com.toptier.web.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.nio.channels.FileChannel;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> findByCategory_IdOrderByIdDesc(Integer id);
    Page<Menu> findAllByCategory_Id(Integer id, Pageable pageable);

    @Query("""
        SELECT m 
        FROM Menu m 
        WHERE m.category.id In :cates 
        ORDER BY m.category.id, m.id DESC
    """)
    List<Menu> findByCategory_IdInOrderByCategoryIdDesc(List<Integer> cates);

    List<Menu> findBySignatureYN(String signature);
    List<Menu> findByBestYN(String category);
    List<Menu> findByNewYN(String category);
}
