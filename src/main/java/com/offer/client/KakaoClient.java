package com.offer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoClient {

    public String getAccessToken(KakaoAccessTokenRequest request) {
        // TODO: 2023/07/30 not implemented yet
        return null;
    }

    public KakaoSocialProfileResponse getSocialProfile(String accessToken) {
        // TODO: 2023/07/30 not implemented yet
        return null;
    }
}
