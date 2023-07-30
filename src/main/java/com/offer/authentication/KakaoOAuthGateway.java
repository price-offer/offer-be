package com.offer.authentication;

import com.offer.authentication.application.response.OAuthLoginResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String clientSecret;
    private String redirectUrl;
    private String oauthServerUrl;
    private String accessTokenUrl;
    private String userProfileUrl;

    public String getLoginUrl() {
        String oauthLoginUrl = oauthServerUrl + OAUTH_LOGIN_URL_SUFFIX;
        return String.format(oauthLoginUrl, clientId, redirectUrl);
    }

    public OAuthLoginResponse login(String authCode) {
        // TODO: 2023/07/30 not implemented yet
        return null;
    }
}
