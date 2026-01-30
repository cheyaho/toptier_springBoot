package com.toptier.web.repository;

import com.toptier.web.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    int findIdByShopName(String shopName);
}
