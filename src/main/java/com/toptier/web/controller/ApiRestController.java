package com.toptier.web.controller;

import com.toptier.web.dto.*;
import com.toptier.web.service.BoardService;
import com.toptier.web.service.FranchiseService;
import com.toptier.web.service.MenuService;
import com.toptier.web.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiRestController {
    private final MenuService menuService;
    private final ShopService shopService;
    private final BoardService boardService;
    private final FranchiseService franchiseService;

    @GetMapping("/menu/category")
    public ResponseEntity<List<MenuResponse>> getMenusInCategory(@RequestParam("categoryId") String cate) {
        List<MenuResponse> menuList = menuService.getMenuInCategory(cate);
        return ResponseEntity.ok()
                .body(menuList);
    }

    @GetMapping("/menu/categories")
    public ResponseEntity<List<MenuResponse>> getMenusInCategories(@RequestParam("categoryIds") List<String> cates) {
        List<MenuResponse> menuList = menuService.getMenuInCategories(cates);
        return ResponseEntity.ok()
                .body(menuList);
    }

    @GetMapping("/menu/signature")
    public ResponseEntity<List<MenuResponse>> getSignitureMenu(@RequestParam(value = "signature", required = false) String signature) {
        String signatureYn = (signature == null) ? "Y" : signature;
        List<MenuResponse> menuList = menuService.getSignatureMenu(signatureYn);
        return ResponseEntity.ok()
                .body(menuList);
    }

    /**
     * Shop
     * @param reqShop
     * @return
     */
    @PostMapping("/shop/create")
    public ResponseEntity<String> createShop(@RequestBody ShopRequest reqShop){
        shopService.createShop(reqShop);
        return ResponseEntity.ok().body("success");
    }

    @PostMapping("/shop/update/{id}")
    public ResponseEntity<String> updateShop(@PathVariable Integer id, @RequestBody ShopRequest reqShop){
        shopService.updateShop(id, reqShop);
        return ResponseEntity.ok().body("success");
    }

    @DeleteMapping("/shop/delete/{id}")
    public ResponseEntity<String> deleteShop(@PathVariable Integer id){
        shopService.deleteShop(id);
        return ResponseEntity.ok().body("success");
    }

    /**
     * Franchies
     * @param reqFranchise
     * @return
     */
    @PostMapping("/franchiseInquriy/create")
    public ResponseEntity<ResultResponse> createFranchiesRequest(@Valid @RequestBody FranchiseRequest reqFranchise){
        Integer id = franchiseService.createFranchise(reqFranchise);
        return ResponseEntity.ok()
                .body(ResultResponse.success("가맹문의 등록이 완료되었습니다.", id));
    }

    /**
     * Board
     * @param @PathVariable
     * @return
     */
    @GetMapping("/board/{seq}")
    public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable Integer seq){
        BoardResponse board = boardService.getBoardDetail(seq);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping("/board/create")
    public ResponseEntity<ResultResponse> createBoard(@RequestBody AddBoardRequest reqBoard){
        Integer id = boardService.createBoard(reqBoard);
        return ResponseEntity.ok().body(ResultResponse.success("글을 등록하였습니다.", id));
    }

    @PostMapping("/board/update/{seq}")
    public ResponseEntity<ResultResponse> updateBoard(@PathVariable Integer seq, @RequestBody AddBoardRequest reqBoard){
        Integer id = boardService.updateBoard(seq, reqBoard);
        return ResponseEntity.ok().body(ResultResponse.success("글을 등록하였습니다.", id));
    }

    @DeleteMapping("/board/delete/{seq}")
    public ResponseEntity<String> deleteBoard(@PathVariable Integer seq){
        boardService.deleteBoard(seq);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/board/addHits/{seq}")
    public ResponseEntity<String> addHits(@PathVariable Integer seq){
        return ResponseEntity.ok().body("success");
    }
}
