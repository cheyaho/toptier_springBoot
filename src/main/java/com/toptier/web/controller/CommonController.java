package com.toptier.web.controller;

import com.toptier.web.dto.HeaderInfo;
import com.toptier.web.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "com.toptier.web.controller")
@RequiredArgsConstructor
@Slf4j
public class CommonController {
    private final CommonService commonService;

    @ModelAttribute("headerInfo")
    public HeaderInfo getHeaderInfo() {
        return new HeaderInfo(
                commonService.findAllCategory(),
                commonService.findAllNoneHiddenShop());
    }
}
