package com.toptier.web.controller;

import com.toptier.web.dto.*;
import com.toptier.web.repository.BoardTypeRepository;
import com.toptier.web.service.BoardService;
import com.toptier.web.service.MenuService;
import com.toptier.web.service.ShopService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

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
            // 메뉴 목록 조회
            List<MenuResponse> menuList = menuService.getMenuInCategory(cate);
            // 카테고리 이름 조회
            String categoryName = menuService.getCategoryName(cate);
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
                         @RequestParam(defaultValue = "1") int currentPage,
                         @RequestParam(defaultValue = "10") int pageSize,
                         @RequestParam(defaultValue = "seq,desc") String sort,
                         Model mv) throws Exception {
        // 페이지 정보 설정
        currentPage = Math.max(currentPage-1, 0);
        String[] sorts = sort.split(",");
        Sort.Direction direction = (sorts.length > 1 && "asc".equalsIgnoreCase(sorts[1])) ? Sort.Direction.ASC :Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by(direction, "seq"));

        // 게시물 목록 조회
        Page<BoardResponse> boardList = boardService.getBoardList(path, pageable);
        BoardTypeResponse boardType = boardTypeRepository.findByPathName(path)
                .map(BoardTypeResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다."));

        // Pagenation
        Pagenation pagenation = new Pagenation(boardList, pageSize);

        // Model에 담기
        mv.addAttribute("BOARD_LIST", boardList);
        mv.addAttribute("BOARD_TYPE", boardType);
        mv.addAttribute("PAGENATION", pagenation);
        return "community/boardList";
    }

    @GetMapping("/{path}/detail/{seq}")
    public String notice(@PathVariable String path, @PathVariable Integer seq, HttpSession session, Model mv) throws Exception {
        // 게시물 조회수 증가
        String sessionKey = "VIEWED_BOARD_"+seq;
        if(session.getAttribute(sessionKey) == null) {
            boardService.increaseHits(seq);
            session.setAttribute(sessionKey, true);
        }

        // 게시물 상세 조회
        BoardResponse boardDetail = boardService.getBoardDetail(seq);

        // Model에 담기
        mv.addAttribute("BOARD_DETAIL", boardDetail);
        mv.addAttribute("BOARD_TYPE", boardDetail.type());
        return "community/boardDetail";
    }
}
