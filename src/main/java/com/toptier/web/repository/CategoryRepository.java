package com.toptier.web.repository;

import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<MenuCategory, Integer> {
    CategoryResponse findByCate(String cate);
}