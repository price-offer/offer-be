package com.offer.authentication.application.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthLoginResponse {

    private Long id;
    private String nickname;
    private String profileImageUrl;
    private String accessToken;
    private Boolean newMember;
}
