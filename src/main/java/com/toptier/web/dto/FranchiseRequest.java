package com.toptier.web.dto;

public record FranchiseRequest(
        String reqName,
        String reqPhoneNum1,
        String reqPhoneNum2,
        String reqPhoneNum3,
        String emailId,
        String emailDomain,
        String reqAddr1,
        String reqAddr2,
        String isShop,
        String confirmYN,
        int shopSize,
        String operMethod,
        String operExp,
        String etc,
        String hidden
) {
}
