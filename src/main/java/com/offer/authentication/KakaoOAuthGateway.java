package com.offer.authentication;

import com.offer.client.KakaoSocialProfileClient;
import com.offer.client.KakaoSocialProfileResponse;
import com.offer.client.KakaoTokenClient;
import com.offer.member.Member.OAuthType;

import java.util.HashMap;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ConfigurationProperties("oauth2.kakao")
public class KakaoOAuthGateway {

    private static final String OAUTH_LOGIN_URL_SUFFIX = "?client_id=%s&redirect_uri=%s&response_type=code";

    private String clientId;
    private String redirectUrl;
    private String oauthServerUrl;

    @Autowired
    private KakaoTokenClient kakaoTokenClient;

    @Autowired
    private KakaoSocialProfileClient socialProfileClient;

    public String getLoginUrl() {
        String oauthLoginUrl = oauthServerUrl + OAUTH_LOGIN_URL_SUFFIX;
        return String.format(oauthLoginUrl, clientId, redirectUrl);
    }

    public SocialProfile getUserProfile(String authCode) {
        HashMap<String, String> request = new HashMap<>();
        request.put("grant_type", "authorization_code");
        request.put("client_id", clientId);
        request.put("redirect_uri", redirectUrl);
        request.put("code", authCode);

        String token = kakaoTokenClient.inquireToken(clientId, redirectUrl, authCode);
        KakaoSocialProfileResponse profileResponse = socialProfileClient.inquireProfile(token);

        return new SocialProfile(OAuthType.KAKAO, profileResponse.getId(), profileResponse.getProfileImageUrl());
    }
}
