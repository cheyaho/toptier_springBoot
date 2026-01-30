package com.toptier.web.dto;

import com.toptier.web.entity.Shop;

import java.time.LocalDateTime;

public record ShopResponse(
        int id,
        String shopName,
        String addr,
        String addr2,
        String shopTel,
        String operTime,
        String takeoutYN,
        String seatYN,
        String wifiYN,
        String deliveryYN,
        String parkingYN,
        String desertYN,
        String easypayYN,
        String lat,
        String lng,
        String hidden,
        LocalDateTime insDate
) {
    public static ShopResponse from(Shop shop) {
        return new ShopResponse(
                shop.getId(),
                shop.getShopName(),
                shop.getAddr(),
                shop.getAddr2(),
                shop.getShopTel(),
                shop.getOperTime(),
                shop.getAmenity().getTakeOutYN(),
                shop.getAmenity().getSeatYN(),
                shop.getAmenity().getWifiYN(),
                shop.getAmenity().getDeliveryYN(),
                shop.getAmenity().getParkingYN(),
                shop.getAmenity().getDesertYN(),
                shop.getAmenity().getEasypayYN(),
                shop.getLat(),
                shop.getLng(),
                shop.getHidden(),
                shop.getInsDate()
        );
    }
}
