package com.offer.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoAccessTokenRequest {

    @JsonProperty("grant_type")
    private final String grantType = "authorization_code";

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    @JsonProperty("redirect_uri")
    private final String redirectUri;
    private final String code;

    public KakaoAccessTokenRequest(String clientId, String clientSecret, String redirectUri, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.code = code;
    }
}
