package com.offer.client;

import feign.Headers;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakaoProfileClient", url = "https://kapi.kakao.com")
public interface KakaoProfileClient {

    @Headers("Authorization: Bearer {accessToken}")
    @GetMapping("/v2/user/me")
    KakaoSocialProfileResponse getSocialProfile(@Param("accessToken") String accessToken);
}
