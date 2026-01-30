package com.toptier.web.dto;

import lombok.*;

public record ShopRequest (
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
    String easyPayYN,
    String lat,
    String lng,
    String hidden
){
}
