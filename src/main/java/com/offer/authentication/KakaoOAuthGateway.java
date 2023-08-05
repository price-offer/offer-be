package com.offer.authentication;

import com.offer.member.Member.OAuthType;
import com.offer.client.KakaoAccessTokenRequest;
import com.offer.client.KakaoProfileClient;
import com.offer.client.KakaoTokenClient;
import com.offer.client.KakaoSocialProfileResponse;
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

    @Autowired
    private KakaoTokenClient kakaoTokenClient;

    @Autowired
    private KakaoProfileClient kakaoProfileClient;

    private String clientId;
    private String clientSecret;
    private String redirectUrl;
    private String oauthServerUrl;

    public String getLoginUrl() {
        String oauthLoginUrl = oauthServerUrl + OAUTH_LOGIN_URL_SUFFIX;
        return String.format(oauthLoginUrl, clientId, redirectUrl);
    }

    public SocialProfile getUserProfile(String authCode) {
        String accessToken = kakaoTokenClient.getAccessToken(
            new KakaoAccessTokenRequest(clientId, clientSecret, redirectUrl, authCode))
            .getAccessToken();
        KakaoSocialProfileResponse profileResponse = kakaoProfileClient.getSocialProfile(accessToken);
        return new SocialProfile(OAuthType.KAKAO, profileResponse.getId(), profileResponse.getProfileImageUrl());
    }
}
