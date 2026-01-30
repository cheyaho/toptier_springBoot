package com.toptier.web.service;

import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.dto.MenuResponse;
import com.toptier.web.entity.Menu;
import com.toptier.web.repository.CategoryRepository;
import com.toptier.web.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<MenuResponse> getMenuInCategory(String cate) {
        List<MenuResponse> menus = menuRepository.findByCategory_CateOrderByIdDesc(cate)
                .stream()
                .map(MenuResponse::from)
                .toList();
        return menus;
    }

    @Override
    public List<MenuResponse> getMenuInCategories(List<String> cates) {
        List<Menu> menus = menuRepository.findByCategory_CateInOrderByCategoryIdDesc(cates);
        return menus.stream().map(MenuResponse::from).toList();
    }

    @Override
    public List<MenuResponse> getSignatureMenu(String signature) {
        List<Menu> signatures = menuRepository.findBySignatureYN(signature);
        return signatures.stream().map(MenuResponse::from).toList();
    }

    @Override
    public String getCategoryName(String cate) {
        CategoryResponse category = categoryRepository.findByCate(cate);
        return category.categoryName();
    }

    @Override
    public Page<MenuResponse> getAllMenuList(String cate, Pageable pageable) {
        if(!cate.equals("")) {
            Page<MenuResponse> menuList = menuRepository.findAllByCategory_Cate(cate, pageable)
                    .map(MenuResponse::from);
            return menuList;
        }else{
            Page<MenuResponse> menuList = menuRepository.findAll(pageable)
                .map(MenuResponse::from);
            return menuList;
        }
    }

    @Override
    public MenuResponse getMenuInfo(Integer id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        return MenuResponse.from(menu);
    }
}
