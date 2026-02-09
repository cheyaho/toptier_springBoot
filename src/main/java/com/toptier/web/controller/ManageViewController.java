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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String manageMenu(MenuSearchRequest req,
                             Model mv) {
        // 메뉴 목록 조회
        Page<MenuResponse> menuList = menuService.getAllMenuList(req.getCategory(), req.pageable());

        // Pagenation
        Pagination pagination = new Pagination(menuList, req.getPageSize());

        // Model에 담기
        mv.addAttribute("CATEGORY", req.getCategory());
        mv.addAttribute("MENU_LIST", menuList);
        mv.addAttribute("PAGINATION", pagination);

        return "manage/menuList";
    }

    @GetMapping("/menu/{id}")
    public String getMenuDetail(@PathVariable("id") Integer id,
                                MenuSearchRequest req,
                                Model mv){
        // 메뉴 정보 조회
        MenuResponse menu = menuService.getMenuInfo(id);

        // 카테고리 정보 조회
        List<CategoryResponse> categories = menuService.getAllCategories();

        // Model에 담기
        mv.addAttribute("MENU", menu);
        mv.addAttribute("currentPage", req.getCurrentPage());
        mv.addAttribute("CATEGORY", req.getCategory());
        mv.addAttribute("CATEGORIES", categories);


        return "manage/menuDetail";
    }

    @GetMapping("/menu/regNewMenuItem")
    public String regNewMenuItem(CommonSearchRequest req, Model mv) {
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
        Pagination pagination = new Pagination(shopList, pageSize);

        // Model에 담기
        mv.addAttribute("SHOP_LIST", shopList);
        mv.addAttribute("PAGINATION", pagination);

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

    @GetMapping("/shop/create")
    public String createShop(@RequestParam(defaultValue = "1") Integer currentPage, Model mv){
        mv.addAttribute("currentPage", currentPage);
        return "manage/regNewShop";
    }

    /**
     * 가맹 문의
     * @param mv
     * @param reqFr
     * @return
     */
    @GetMapping("/franchise")
    public String manageFranchise(Model mv,
                                  FranchiseSearchRequest reqFr) {
        // Pageable
        String hidden = reqFr.getHidden();

        // 가맹 문의 조회
        Page<FranchiseResponse> franchiseList = franchiseService.getAllFranchiseList(reqFr.pageable(), hidden);

        // Pagenation
        Pagination pagination = new Pagination(franchiseList, reqFr.getPageSize());

        // Model에 담기
        mv.addAttribute("FRANCHISE_LIST", franchiseList);
        mv.addAttribute("PAGINATION", pagination);

        return "manage/franchiseList";
    }

    @GetMapping("/franchise/{id}")
    public String getFranchiseDetail(@PathVariable("id") int id,
                                     @RequestParam(defaultValue = "1") int currentPage,
                                     Model mv){
        FranchiseResponse franchise = franchiseService.getFranchiseInfo(id);
        mv.addAttribute("REQ_FR", franchise);
        mv.addAttribute("currentPage", currentPage);
        return "manage/franchiseDetail";
    }

    /**
     * 게시판
     * @param mv
     * @param req
     * @return
     */
    @GetMapping("/board")
    public String manageBoard(Model mv,
                              BoardSearchRequest req) {
        // 게시판 목록 조회
        Page<BoardResponse> boardList = boardService.getAllBoard(req.pageable(), req.getType());

        // Pagenation
        Pagination pagination = new Pagination(boardList, req.getPageSize());

        // Model에 담기
        mv.addAttribute("BOARD_LIST", boardList);
        mv.addAttribute("PAGINATION", pagination);
        mv.addAttribute("CATEGORY", req.getType());

        return "manage/boardList";
    }

    @GetMapping("/board/create")
    public String getBoardCreate(Model mv){
        return "manage/boardNew";
    }

    @GetMapping("/board/{seq}")
    public String getBoardDetail(@PathVariable("seq") Integer seq,
                                 BoardSearchRequest req,
                                 Model mv){
        // 게시판 정보 조회
        BoardResponse board = boardService.getBoardDetail(seq);
        // Model에 담기
        mv.addAttribute("BOARD", board);
        mv.addAttribute("currentPage", req.getCurrentPage());
        mv.addAttribute("CATEGORY", req.getType());

        return "manage/boardDetail";
    }
}
