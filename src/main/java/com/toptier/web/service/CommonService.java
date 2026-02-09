package com.toptier.web.service;

import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.dto.ShopResponse;

import java.util.List;

public interface CommonService {
    List<CategoryResponse> findAllCategory();
    List<ShopResponse> findAllShop();
    List<ShopResponse> findAllNoneHiddenShop();
}
