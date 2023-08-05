package com.offer.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;

@Getter
public class KakaoSocialProfileResponse {

    private Long id;
    private String profileImageUrl;

    @JsonProperty("kakao_account")
    private void unpackNested(Map<String,Object> kakaoAccount) {
        Map<String,String> profile = (Map<String,String>) kakaoAccount.get("profile");
        this.profileImageUrl = profile.get("profile_image_url");
    }
}
