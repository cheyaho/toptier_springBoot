package com.toptier.web.dto;

import com.toptier.web.entity.Franchise;

import java.time.LocalDateTime;

public record FranchiseResponse(
        int id,
        String reqName,
        String reqPhoneNum,
        String reqEmail,
        String reqAddr1,
        String reqAddr2,
        String isShop,
        String confirmYN,
        int shopSize,
        String operMethod,
        String operExp,
        String etc,
        String hidden,
        LocalDateTime insDate,
        LocalDateTime updDate
) {
    public static FranchiseResponse from(Franchise franchise) {
        return new FranchiseResponse(
                franchise.getId(),
                franchise.getReqName(),
                franchise.getReqPhoneNum(),
                franchise.getReqEmail(),
                franchise.getReqAddr1(),
                franchise.getReqAddr2(),
                franchise.getIsShop(),
                franchise.getConfirmYN(),
                franchise.getShopSize(),
                franchise.getOperMethod(),
                franchise.getOperExp(),
                franchise.getEtc(),
                franchise.getHidden(),
                franchise.getInsDate(),
                franchise.getUpdDate()
        );
    }
}
