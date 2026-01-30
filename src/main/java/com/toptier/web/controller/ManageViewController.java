package com.toptier.web.controller;

import com.toptier.web.dto.*;
import com.toptier.web.service.BoardService;
import com.toptier.web.service.FranchiseService;
import com.toptier.web.service.MenuService;
import com.toptier.web.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manage")
@Slf4j
public class ManageViewController {
    private final BoardService boardService;
    private final MenuService menuService;
    private final ShopService shopService;
    private final FranchiseService franchiseService;

    @GetMapping("/main")
    public String manageMain(Model mv) {

        return "manage/manageMain";
    }

    @GetMapping("/menu")
    public String manageMenu(@RequestParam(defaultValue="") String category,
                             @RequestParam(defaultValue="1") int currentPage,
                             @RequestParam(defaultValue="10") int pageSize,
                             @RequestParam(defaultValue = "") String search,
                             @RequestParam(defaultValue="insDate,desc") String sort,
                             Model mv) {
        // 페이지 정보 설정
        currentPage = Math.max(currentPage -1, 0);
        String[] sorts = sort.split(",");
        Sort.Direction direction = (sorts.length > 1 && "asc".equalsIgnoreCase(sorts[1])) ? Sort.Direction.ASC :Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(direction, sorts[0]));

        // 메뉴 목록 조회
        Page<MenuResponse> menuList = menuService.getAllMenuList(category, pageable);

        // Pagenation
        Pagenation pagenation = new Pagenation(menuList, pageSize);

        // Model에 담기
        mv.addAttribute("CATEGORY", category);
        mv.addAttribute("MENU_LIST", menuList);
        mv.addAttribute("PAGENATION", pagenation);

        return "manage/menuList";
    }

    @GetMapping("/menu/{id}")
    public String getMenuDetail(@PathVariable("id") Integer id,
                                @RequestParam(defaultValue = "1") int currentPage,
                                Model mv){
        // 메뉴 정보 조회
        MenuResponse menu = menuService.getMenuInfo(id);

        // Model에 담기
        mv.addAttribute("MENU", menu);
        mv.addAttribute("currentPage", currentPage);

        return "manage/menuDetail";
    }

    @GetMapping("/menu/regNewMenuItem")
    public String regNewMenuItem(Model mv) {

        return "manage/regNewMenu";
    }

    @GetMapping("/shop")
    public String manageShop(Model mv,
                             @RequestParam(defaultValue = "1") int currentPage,
                             @RequestParam(defaultValue = "10") int pageSize) {
        // Pageable
        currentPage = Math.max(currentPage - 1, 0);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // 매장 목록 조회
        Page<ShopResponse> shopList = shopService.getAllShopList(pageable);

        // Pagenation
        Pagenation pagenation = new Pagenation(shopList, pageSize);

        // Model에 담기
        mv.addAttribute("SHOP_LIST", shopList);
        mv.addAttribute("PAGENATION", pagenation);

        return "manage/shopList";
    }

    @GetMapping("/shop/{id}")
    public String getShopDetail(@PathVariable("id") Integer id,
                                @RequestParam(defaultValue = "1") Integer currentPage,
                                Model mv){
        ShopResponse shop = shopService.getShopInfo(id);
        mv.addAttribute("SHOP", shop);
        mv.addAttribute("currentPage", currentPage);
        return "manage/shopDetail";
    }

    @GetMapping("/franchise")
    public String manageFranchise(Model mv,
                                  @RequestParam(defaultValue = "1") int currentPage,
                                  @RequestParam(defaultValue = "10") int pageSize) {
        // Pageable
        currentPage = Math.max(currentPage - 1, 0);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // 가맹 문의 조회
        Page<FranchiseResponse> franchiseList = franchiseService.getAllFranchiseList(pageable);

        // Pagenation
        Pagenation pagenation = new Pagenation(franchiseList, pageSize);

        // Model에 담기
        mv.addAttribute("FRANCHISE_LIST", franchiseList);
        mv.addAttribute("PAGENATION", pagenation);

        return "manage/franchiseList";
    }

    @GetMapping("/board")
    public String manageBoard(Model mv,
                              @RequestParam(defaultValue = "0") int type,
                              @RequestParam(defaultValue = "1") int currentPage,
                              @RequestParam(defaultValue = "10") int pageSize) {
        // Pageable
        currentPage = Math.max(currentPage - 1, 0);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // 게시판 목록 조회
        Page<BoardResponse> boardList = boardService.getAllBoard(type, pageable);

        // Pagenation
        Pagenation pagenation = new Pagenation(boardList, pageSize);

        // Model에 담기
        mv.addAttribute("BOARD_LIST", boardList);
        mv.addAttribute("PAGENATION", pagenation);
        mv.addAttribute("CATEGORY", type);

        return "manage/boardList";
    }

    @GetMapping("/board/{seq}")
    public String getBoardDetail(@PathVariable("seq") Integer seq,
                                 @RequestParam(defaultValue = "") int currentPage,
                                 @RequestParam(defaultValue = "") int type,
                                 Model mv){
        // 게시판 정보 조회
        BoardResponse board = boardService.getBoardDetail(seq);
        // Model에 담기
        mv.addAttribute("BOARD", board);
        mv.addAttribute("currentPage", currentPage);
        mv.addAttribute("CATEGORY", type);

        return "manage/boardDetail";
    }
}
