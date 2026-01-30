package com.toptier.web.controller;

import com.toptier.web.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainViewController {
    private final MenuService menuService;

    @RequestMapping(value="/")
    public String main(Model mv) {
        mv.addAttribute("MENU_LIST", menuService.getSignatureMenu("Y"));
        return "index.html";
    }
}
