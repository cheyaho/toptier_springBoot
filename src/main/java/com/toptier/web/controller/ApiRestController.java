package com.toptier.web.controller;

import com.toptier.web.common.utils.FileUtil;
import com.toptier.web.dto.*;
import com.toptier.web.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class ApiRestController {
    private final MenuService menuService;
    private final ShopService shopService;
    private final BoardService boardService;
    private final FranchiseService franchiseService;
    private final SiteUserService siteUserService;

    /**
     * User
     */
    @PostMapping("/user/create")
    public ResponseEntity<ResultResponse> createSiteUser(@Valid SiteUserRequest reqUser, BindingResult bindingResult) {
        ResultResponse result = null;
        if(bindingResult.hasErrors()) {
            result = ResultResponse.fail(bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
            return ResponseEntity.ok()
                    .body(result);
        } else if(!reqUser.getPassword().equals(reqUser.getPasswordConfirm())) {
            result = ResultResponse.fail("비밀번호가 일치하지 않습니다.", "E201");
            return ResponseEntity.ok()
                    .body(result);
        } else {
            siteUserService.createSiteUser(reqUser);
            result = ResultResponse.success("사용자 등록이 완료되었습니다.", null);
        }
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping(value = "/user/checkDupId")
    public ResponseEntity<ResultResponse<String>> checkDuplicateId(@RequestParam("userId") String userId) {
        log.info("userId : {}", userId);
        ResultResponse<String> result;
        if(!siteUserService.checkDuplicateId(userId)) {
            result = ResultResponse.success("사용 가능한 아이디입니다.", userId);
        } else {
            result = ResultResponse.fail("이미 사용중인 아이디입니다.", userId);
        }
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping(value = "/user/checkDupEmail")
    public ResponseEntity<ResultResponse<String>> checkDuplicateEmail(@RequestParam("email") String email) {
        ResultResponse<String> result = null;
        if(!siteUserService.checkDuplicateEmail(email)) {
            result = result.success("사용 가능한 이메일입니다.", email);
        } else {
            result = result.fail("이미 사용중인 이메일입니다.", email);
        }
        return ResponseEntity.ok()
                .body(result);
    }

    /**
     * Menu
     * @param cateId
     * @return
     */
    @GetMapping("/menu/category")
    public ResponseEntity<List<MenuResponse>> getMenusInCategory(@RequestParam("categoryId") Integer cateId) {
        List<MenuResponse> menuList = menuService.getMenuInCategory(cateId);
        return ResponseEntity.ok()
                .body(menuList);
    }

    @GetMapping("/menu/categories")
    public ResponseEntity<ResultResponse<List<MenuResponse>>> getMenusInCategories(@RequestParam("categoryIds") List<Integer> cates) {
        List<MenuResponse> menuList = menuService.getMenuInCategories(cates);
        ResultResponse result = ResultResponse.success("", menuList);
        return ResponseEntity.ok()
                .body(result);
    }

    @GetMapping("/menu/signature")
    public ResponseEntity<List<MenuResponse>> getSignitureMenu(@RequestParam(value = "signature", required = false) String signature) {
        String signatureYn = (signature == null) ? "Y" : signature;
        List<MenuResponse> menuList = menuService.getSignatureMenu(signatureYn);
        return ResponseEntity.ok()
                .body(menuList);
    }

    @PostMapping("/menu/create")
    public ResponseEntity<ResultResponse<Integer>> createMenu(
            @ModelAttribute MenuRequest reqMenu,
            @RequestParam(value = "imagePathFile", required = false) MultipartFile file) throws Exception {
        ResultResponse<Integer> result = null;

        menuService.createMenu(reqMenu, file);
        result = ResultResponse.success("메뉴를 등록하였습니다.", reqMenu.id());

        return ResponseEntity.ok().body(result);
    }

    @PostMapping(value="/menu/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse<Integer>> updateMenu(
            @ModelAttribute MenuRequest reqMenu,
            @RequestParam(value = "imagePathNew", required = false)MultipartFile file) throws Exception {
        ResultResponse<Integer> result = null;

        menuService.updateMenu(reqMenu, file);

        result = ResultResponse.success("메뉴를 수정하였습니다.", reqMenu.id());

        return ResponseEntity.ok().body(result);
    }


    /**
     * Shop
     * @param reqShop
     * @return
     */
    @PostMapping("/shop/create")
    public ResponseEntity<ResultResponse> createShop(@RequestBody ShopRequest reqShop) throws Exception{
        shopService.createShop(reqShop);
        ResultResponse result = ResultResponse.success("신규 매장을 등록하였습니다.", null);
        return ResponseEntity.ok().
                body(result);
    }

    @PutMapping(value = "/shop/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse<Integer>> updateShop(@RequestBody ShopRequest reqShop) throws Exception{
        shopService.updateShop(reqShop);
        ResultResponse result = ResultResponse.success("매장 내용을 수정하였습니다.", reqShop.id());
        return ResponseEntity.ok().body(result);
    }

    @PutMapping(value = "/shop/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultResponse<Integer>> updateShop(@PathVariable Integer id,
                                                              @RequestBody Map<String, String> reqMap) throws Exception{
        shopService.updateShopState(id, reqMap.get("hidden"));
        ResultResponse result = ResultResponse.success("매장 내용을 수정하였습니다.", id);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/shop/delete/{id}")
    public ResponseEntity<ResultResponse<Integer>> deleteShop(@PathVariable Integer id){
        shopService.deleteShop(id);
        ResultResponse result = ResultResponse.success("매장을 삭제하였습니다.", id);
        return ResponseEntity.ok().body(result);
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

    @PutMapping("/franchise/update/{id}")
    public ResponseEntity<ResultResponse> updateFranchiseUpdate(@PathVariable Integer id,
                                                                @RequestBody Map<String, String> reqMap){
        franchiseService.updateFranchiseUpdate(id, reqMap.get("confirmYN"));
        return ResponseEntity.ok()
                .body(ResultResponse.success("가맹문의 처리가 완료되었습니다.", id));
    }

    @DeleteMapping("/franchise/delete/{id}")
    public ResponseEntity<ResultResponse> deleteFranchiseUpdate(@PathVariable Integer id,
                                                                @RequestBody FranchiseRequest reqFranchise){
        log.info("path variable id : {}", id);
        log.info("RequestBody : {}", reqFranchise.toString());
        if(reqFranchise.hidden() == null) throw new NullPointerException();
        franchiseService.deleteFranchiseUpdate(id, reqFranchise.hidden());
        return ResponseEntity.ok()
                .body(ResultResponse.success("가맹문의가 삭제되었습니다.", id));
    }


    /**
     * Board
     * @param @PathVariable
     * @return
     */
    @GetMapping("/board/{id}")
    public ResponseEntity<BoardResponse> getBoardDetail(@PathVariable Integer id){
        BoardResponse board = boardService.getBoardDetail(id);
        return ResponseEntity.ok().body(board);
    }

    @PostMapping(value="/board/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> createBoard(@ModelAttribute BoardRequest reqBoard,
                                                      @RequestParam(value = "attachFile", required = false) MultipartFile file) throws Exception {
        Integer id = boardService.createBoard(reqBoard, file);
        return ResponseEntity.ok().body(ResultResponse.success("글을 등록하였습니다.", id));
    }

    @PostMapping(value = "/board/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResultResponse> updateBoard(@PathVariable Integer id,
                                                      @ModelAttribute BoardRequest reqBoard,
                                                      @RequestParam(value = "attachFile", required = false) MultipartFile attachFile) throws Exception {
        boardService.updateBoard(id, reqBoard, attachFile);
        return ResponseEntity.ok()
                .body(ResultResponse.success("글을 수정하였습니다.", id));
    }

    @DeleteMapping("/board/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Integer id){
        boardService.deleteBoard(id);
        return ResponseEntity.ok().body("success");
    }

    @GetMapping("/board/addHits/{seq}")
    public ResponseEntity<String> addHits(@PathVariable Integer seq){
        return ResponseEntity.ok().body("success");
    }

    /**
     * File Upload
     * @param request
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping(value="/fileUploader")
    public ResponseEntity<FileUploadResponse> fileUploader(MultipartHttpServletRequest request,
        @RequestParam(defaultValue = "files") String path) throws Exception {
        log.info("file upload path: {}", path);

        String year = LocalDateTime.now().getYear() + "";
        String Month = LocalDateTime.now().getMonthValue() + "";
        String day = LocalDateTime.now().getDayOfMonth() + "";
        path = "/files/" + path + "/" + year + "/" + Month + "/" + day + "/";

        MultipartFile uploadFile = request.getFile("upload");
        FileUploadResponse uploadResponse = FileUtil.fileUploader(uploadFile, path);
        log.info("file upload response: {}", uploadResponse.toString());

        return ResponseEntity.ok().body(uploadResponse);
    }
}
