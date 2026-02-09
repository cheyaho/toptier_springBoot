package com.toptier.web.service;

import com.toptier.web.common.utils.FileUtil;
import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.dto.FileUploadResponse;
import com.toptier.web.dto.MenuRequest;
import com.toptier.web.dto.MenuResponse;
import com.toptier.web.entity.Menu;
import com.toptier.web.entity.MenuCategory;
import com.toptier.web.repository.CategoryRepository;
import com.toptier.web.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenuInCategory(Integer cateId) {
        List<MenuResponse> menus = menuRepository.findByCategory_IdOrderByIdDesc(cateId)
                .stream()
                .map(MenuResponse::from)
                .toList();
        return menus;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getMenuInCategories(List<Integer> cateIds) {
        List<Menu> menus = menuRepository.findByCategory_IdInOrderByCategoryIdDesc(cateIds);
        return menus.stream().map(MenuResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuResponse> getSignatureMenu(String signature) {
        List<Menu> signatures = menuRepository.findBySignatureYN(signature);
        return signatures.stream().map(MenuResponse::from).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public String getCategoryName(Integer id) {
        MenuCategory category = categoryRepository.findById(id).orElse(null);
        String categoryName = category.getCategoryName();
        return categoryName;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuResponse> getAllMenuList(Integer id, Pageable pageable) {
        Page<MenuResponse> menuList = null;
        if(id != null) {
            menuList = menuRepository.findAllByCategory_Id(id, pageable)
                    .map(MenuResponse::from);
        }else{
            menuList = menuRepository.findAll(pageable)
                .map(MenuResponse::from);
        }
        return menuList;
    }

    @Override
    @Transactional(readOnly = true)
    public MenuResponse getMenuInfo(Integer id) {
        Menu menu = menuRepository.findById(id).orElse(null);
        return MenuResponse.from(menu);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public void updateMenu(MenuRequest reqMenu, MultipartFile file) throws Exception {
        // 메뉴 조회
        Menu menu = menuRepository.findById(reqMenu.id())
                .orElseThrow(() -> new IllegalArgumentException("해당 메뉴가 존재하지 않습니다. id=" + reqMenu.id()));
        // 메뉴 카테고리 조회
        MenuCategory category = categoryRepository.findById(reqMenu.category())
                .orElse(null);

        // 메뉴 업데이트
        menu.putMenu(reqMenu, category);

        // 파일 업로드 처리
        if(reqMenu.chgImg().equals("Y")) {
            if (file != null && !file.isEmpty()) {
                String path = "/files/menu/" + category.getId() + "/";
                // 파일 업로드
                FileUploadResponse uploadResult = FileUtil.fileUploader(file, path);
                // 파일 업로드 성공 시 이미지 경로 업데이트
                if (uploadResult.uploaded()) {
                    log.info("fileUpload : ", uploadResult.url());
                    menu.setImagePath(uploadResult.url());
                }
            } else {
                menu.setImagePath("");
            }
        }

        // 메뉴 저장
        menuRepository.save(menu);
    }

    @Override
    @Transactional
    public void createMenu(MenuRequest reqMenu, MultipartFile file) throws Exception{
        MenuCategory category = categoryRepository.findById(reqMenu.category())
                .orElse(null);
        Menu menu = new Menu();
        menu.putMenu(reqMenu, category);

        // 파일 업로드
        if (file != null && !file.isEmpty()) {
            String path = "/files/menu/" + category.getId() + "/";
            // 파일 업로드
            FileUploadResponse uploadResult = FileUtil.fileUploader(file, path);
            // 파일 업로드 성공 시 이미지 경로 업데이트
            if (uploadResult.uploaded()) {
                log.info("fileUpload : ", uploadResult.url());
                menu.setImagePath(uploadResult.url());
            }
        } else {
            menu.setImagePath("");
        }

        menuRepository.save(menu);
    }
}
