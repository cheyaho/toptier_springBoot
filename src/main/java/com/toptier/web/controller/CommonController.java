package com.toptier.web.controller;

import com.toptier.web.dto.HeaderInfo;
import com.toptier.web.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackages = "com.toptier.web.controller")
@RequiredArgsConstructor
public class CommonController {
    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final CommonService commonService;

    @ModelAttribute("headerInfo")
    public HeaderInfo headerInfo() {
        HeaderInfo headerInfo = new HeaderInfo(
                commonService.findAllCategory(),
                commonService.findAllNoneHiddenShop());
        return headerInfo;
    }
}
