package com.offer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "kakaoTokenClient", url = "https://kauth.kakao.com")
public interface KakaoTokenClient {

    @PostMapping("/oauth/token")
    KakaoAccessTokenResponse getAccessToken(KakaoAccessTokenRequest request);
}
