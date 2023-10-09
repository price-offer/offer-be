package com.offer.review.domain;

import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;

public enum Role {
    BUYER, SELLER, ALL

    ;

    public static Role of(String roleStr) {
        if (roleStr == null) return ALL;

        roleStr = roleStr.trim();

        if (roleStr.equalsIgnoreCase(BUYER.name())) {
            return BUYER;
        } else if (roleStr.equalsIgnoreCase(SELLER.name())) {
            return SELLER;
        } else {
            throw new BusinessException(ResponseMessage.INVALID_ROLE);
        }
    }
}
