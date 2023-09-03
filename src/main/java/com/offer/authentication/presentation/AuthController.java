package com.offer.authentication.presentation;

import com.offer.authentication.application.OAuthService;
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.application.response.OAuthLoginUrlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthService oAuthService;

    @GetMapping("authorization/kakao-url")
    public OAuthLoginUrlResponse showKakaoLoginUrl() {
        return oAuthService.getKakaoLoginUrl();
    }

    // kakao redirect-url 경로와 일치 해야함.
    @GetMapping("login/kakao")
    public OAuthLoginResponse kakaoLogin(@RequestParam String code) {
        return oAuthService.kakaoLogin(code);
    }
}
