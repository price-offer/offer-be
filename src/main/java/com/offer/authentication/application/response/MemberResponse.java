package com.offer.authentication.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponse {
    private Long id;
    private String nickname;
    private String profileImageUrl;
    private long offerLevel;
    private long sellingProductCount;
    private long soldProductCount;
    private long reviewCount;
    private long likeProductCount;

    @Builder
    public MemberResponse(Long id, String nickname, String profileImageUrl,
        long offerLevel, long sellingProductCount,
        long soldProductCount, long reviewCount, long likeProductCount) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.offerLevel = offerLevel;
        this.sellingProductCount = sellingProductCount;
        this.soldProductCount = soldProductCount;
        this.reviewCount = reviewCount;
        this.likeProductCount = likeProductCount;
    }
}
