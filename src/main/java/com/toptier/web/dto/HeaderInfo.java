package com.toptier.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HeaderInfo {
    List<CategoryResponse> categoryList;
    List<ShopResponse> shopList;
    public HeaderInfo(List<CategoryResponse> categoryList, List<ShopResponse> shopList) {
        this.categoryList = categoryList;
        this.shopList = shopList;
    }
}
