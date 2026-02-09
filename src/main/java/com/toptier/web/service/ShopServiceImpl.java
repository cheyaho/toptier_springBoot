package com.toptier.web.service;

import com.toptier.web.dto.ResultResponse;
import com.toptier.web.dto.ShopRequest;
import com.toptier.web.dto.ShopResponse;
import com.toptier.web.entity.Shop;
import com.toptier.web.entity.ShopAmenity;
import com.toptier.web.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;

    @Override
    public ShopResponse getShopInfo(Integer id) {
        Shop shopInfo = shopRepository.findById(id).orElse(null);
        return ShopResponse.from(shopInfo);
    }

    @Override
    public void deleteShop(Integer id) {
        shopRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createShop(ShopRequest reqShop) {
        Shop shop = shopRequestToShop(reqShop);
        shopRepository.save(shop);
    }

    @Override
    @Transactional
    public void updateShop(ShopRequest reqShop) {
        Shop shop = shopRepository.findById(reqShop.id())
                .orElseThrow(() -> new IllegalArgumentException("Shop not found"));
        shop.update(reqShop);
    }

    private Shop shopRequestToShop(ShopRequest reqShop) {
        ShopAmenity amenity = ShopAmenity.builder()
                .takeOutYN(reqShop.takeoutYN())
                .seatYN(reqShop.seatYN())
                .wifiYN(reqShop.wifiYN())
                .deliveryYN(reqShop.deliveryYN())
                .parkingYN(reqShop.parkingYN())
                .desertYN(reqShop.desertYN())
                .easypayYN(reqShop.easyPayYN())
                .build();
        Shop shop = Shop.builder()
                .shopName(reqShop.shopName())
                .addr(reqShop.addr())
                .addr2(reqShop.addr2())
                .shopTel(reqShop.shopTel())
                .operTime(reqShop.operTime())
                .lat(reqShop.lat())
                .lng(reqShop.lng())
                .hidden(reqShop.hidden())
                .amenity(amenity)
                .build();
        return shop;
    }


    public Page<ShopResponse> getAllShopList(Pageable pageable){
        Page<ShopResponse> shopList = shopRepository.findAll(pageable)
                                            .map(ShopResponse::from);
        return shopList;
    }

    @Override
    @Transactional
    public void updateShopState(Integer id, String hidden) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Shop not found"));
        shop.setHidden(hidden);
        shopRepository.save(shop);
    }
}
