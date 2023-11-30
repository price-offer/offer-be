package com.offer.authentication.application.response;

import lombok.Builder;
import lombok.Getter;

/**
 * 타 사용자 프로필 조회
 * */
@Getter
public class MemberProfileResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;
    private long offerLevel;
    private long sellingProductCount;
    private long soldProductCount;
    private long reviewCount;

    @Builder
    public MemberProfileResponse(Long id, String nickname, String profileImageUrl,
        long offerLevel, long sellingProductCount, long soldProductCount, long reviewCount) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.offerLevel = offerLevel;
        this.sellingProductCount = sellingProductCount;
        this.soldProductCount = soldProductCount;
        this.reviewCount = reviewCount;
    }
}
