package com.offer.client;

import lombok.Getter;

@Getter
public class KakaoAccessTokenRequest {

    private String grantType;
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String code;
}
