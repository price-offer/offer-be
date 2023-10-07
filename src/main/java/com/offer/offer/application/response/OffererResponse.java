package com.offer.offer.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OffererResponse {

    private Long id;
    private String nickname;
    private String location;
    private String level;
    private String tradeType;
    private String profileImageUrl;

    @Builder
    public OffererResponse(Long id, String nickname, String location, String level,
        String tradeType, String profileImageUrl) {
        this.id = id;
        this.nickname = nickname;
        this.location = location;
        this.level = level;
        this.tradeType = tradeType;
        this.profileImageUrl = profileImageUrl;
    }
}
