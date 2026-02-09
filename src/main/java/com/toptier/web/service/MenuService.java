package com.toptier.web.service;

import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.dto.MenuRequest;
import com.toptier.web.dto.MenuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MenuService {
    List<MenuResponse> getMenuInCategory(Integer id);
    List<MenuResponse> getMenuInCategories(List<Integer> ids);
    List<MenuResponse> getSignatureMenu(String signatureYn);
    String getCategoryName(Integer id);
    Page<MenuResponse> getAllMenuList(Integer id, Pageable pageable);
    MenuResponse getMenuInfo(Integer id);
    List<CategoryResponse> getAllCategories();
    void updateMenu(MenuRequest reqMenu, MultipartFile file) throws Exception;
    void createMenu(MenuRequest reqMenu, MultipartFile file) throws Exception;
}