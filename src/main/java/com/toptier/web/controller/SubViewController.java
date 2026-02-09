package com.toptier.web.controller;

import com.toptier.web.dto.*;
import com.toptier.web.repository.BoardTypeRepository;
import com.toptier.web.service.BoardService;
import com.toptier.web.service.MenuService;
import com.toptier.web.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SubViewController {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final MenuService menuService;
    private final ShopService shopService;
    private final BoardService boardService;
    private final BoardTypeRepository boardTypeRepository;

    /**
     * 브랜드 소개
     * @return
     */
    @GetMapping("/introduce/introduceBrand")
    public String introduceBrand(){
        return "/introduce/introduceBrand";
    }

    @GetMapping("/introduce/introduceBI")
    public String introduceBI(){
        return "/introduce/introduceBI";
    }

    @GetMapping("/introduce/toptierLatte")
    public String toptierLatte(){
        return "/introduce/toptierLatte";
    }

    @GetMapping("/introduce/franchiseCenter")
    public String franchiseCenter(){
        return "/introduce/franchiseCenter";
    }

    /**
     * 메뉴 소개
     * @param cate
     * @param mv
     * @return
     */
    @GetMapping("/menu/{cate}")
    public String menuDetail(@PathVariable String cate, Model mv) {
        // 메뉴 목록 조회
        if(cate.equals("signature")){ // 시그니처 메뉴
            return "/menu/signature";
        }else if(cate.equals("5")){ // 캔라떼 메뉴
            return "/menu/canLatte";
        }else {
            Integer cateId = Integer.parseInt(cate);
            // 메뉴 목록 조회
            List<MenuResponse> menuList = menuService.getMenuInCategory(cateId);
            // 카테고리 이름 조회
            String categoryName = menuService.getCategoryName(cateId);
            // Model에 담기
            mv.addAttribute("MENU_LIST", menuList);
            mv.addAttribute("categoryName", categoryName);
            return "/menu/menuList";
        }
    }

    /**
     * 매장안내
     * @param shopId
     * @param mv
     * @return
     */
    @GetMapping("/shop/{shopId}")
    public String getShopDetail(@PathVariable Integer shopId, Model mv){
        // 매장 정보 조회
        ShopResponse shopInfo = shopService.getShopInfo(shopId);

        // Model에 담기
        mv.addAttribute("SHOP_INFO", shopInfo);
        return "/shop/shopDetail";
    }

    /**
     * 가맹문의
     * @param mv
     * @return
     */
    @GetMapping("/franchies/inquiry")
    public String requestInquiry(Model mv){

        return "/franchies/inquiry";
    }

    /**
     * Board
     * @param path
     * @param mv
     * @return
     * @throws Exception
     */
    @GetMapping("/{path}/list")
    public String notice(@PathVariable String path,
                         BoardSearchRequest searchRequest,
                         Model mv) throws Exception {
        Pageable pageable = searchRequest.pageable();

        // 게시물 목록 조회
        Page<BoardResponse> boardList = boardService.getBoardList(path, pageable);
        BoardTypeResponse boardType = boardTypeRepository.findByPathName(path)
                .map(BoardTypeResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다."));

        // Pagenation
        Pagination pagination = new Pagination(boardList, pageable.getPageSize());

        // Model에 담기
        mv.addAttribute("BOARD_LIST", boardList);
        mv.addAttribute("BOARD_TYPE", boardType);
        mv.addAttribute("PAGINATION", pagination);
        return "community/boardList";
    }

    @GetMapping("/{path}/detail/{id}")
    public String notice(@PathVariable String path, @PathVariable Integer id, HttpSession session, Model mv) throws Exception {
        // 게시물 조회수 증가
        String sessionKey = "VIEWED_BOARD_"+id;
        if(session.getAttribute(sessionKey) == null) {
            boardService.increaseHits(id);
            session.setAttribute(sessionKey, true);
        }

        // 게시물 상세 조회
        BoardResponse boardDetail = boardService.getBoardDetail(id);

        // Model에 담기
        mv.addAttribute("BOARD_DETAIL", boardDetail);
        mv.addAttribute("BOARD_TYPE", boardDetail.type());
        return "community/boardDetail";
    }
}
