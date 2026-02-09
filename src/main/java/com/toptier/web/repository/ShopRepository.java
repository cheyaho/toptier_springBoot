package com.toptier.web.repository;

import com.toptier.web.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    int findIdByShopName(String shopName);
    List<Shop> findAllByHidden(String n);
}
