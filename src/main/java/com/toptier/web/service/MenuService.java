package com.toptier.web.service;

import com.toptier.web.dto.MenuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    List<MenuResponse> getMenuInCategory(String cate);
    List<MenuResponse> getMenuInCategories(List<String> cates);
    List<MenuResponse> getSignatureMenu(String signatureYn);
    String getCategoryName(String cate);
    Page<MenuResponse> getAllMenuList(String cate, Pageable pageable);
    MenuResponse getMenuInfo(Integer id);
}
