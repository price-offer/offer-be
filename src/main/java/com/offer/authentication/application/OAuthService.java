package com.offer.authentication.application;

import com.offer.authentication.KakaoOAuthGateway;
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.application.response.OAuthLoginUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuthService {

    private final KakaoOAuthGateway kakaoOAuthGateway;

    public OAuthLoginUrlResponse getKakaoLoginUrl() {
        return new OAuthLoginUrlResponse(kakaoOAuthGateway.getLoginUrl());
    }

    public OAuthLoginResponse kakaoLogin(String authCode) {
        return kakaoOAuthGateway.login(authCode);
    }
}
