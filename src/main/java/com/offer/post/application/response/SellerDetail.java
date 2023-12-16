package com.offer.post.application.response;

import com.offer.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SellerDetail {
    private Long id;
    private String profileImageUrl;
    private String nickname;
    private int offerLevel;

    @Builder
    public SellerDetail(Long id, String profileImageUrl, String nickname, int offerLevel) {
        this.id = id;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
        this.offerLevel = offerLevel;
    }

    public static SellerDetail from(Member seller) {
        return SellerDetail.builder()
                .id(seller.getId())
                .profileImageUrl(seller.getProfileImageUrl())
                .nickname(seller.getNickname())
                .offerLevel(seller.getOfferLevel())
                .build();
    }
}
