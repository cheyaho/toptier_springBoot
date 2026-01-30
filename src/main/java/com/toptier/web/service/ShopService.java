package com.toptier.web.service;

import com.toptier.web.dto.ShopRequest;
import com.toptier.web.dto.ShopResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    ShopResponse getShopInfo(Integer id);

    void deleteShop(Integer id);

    void createShop(ShopRequest reqShop);

    void updateShop(Integer id, ShopRequest reqShop);

    Page<ShopResponse> getAllShopList(Pageable pageable);
}
