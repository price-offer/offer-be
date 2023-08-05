package com.offer.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoAccessTokenResponse {

    @JsonProperty("access_token")
    private String accessToken;
}
