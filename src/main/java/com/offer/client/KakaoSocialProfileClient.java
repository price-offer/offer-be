package com.offer.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoSocialProfileClient {

    private final WebClient.Builder webClientBuilder;

    public KakaoSocialProfileResponse inquireProfile(String token) {
        WebClient webClient = webClientBuilder.baseUrl("https://kapi.kakao.com/v2/user/me")
            .build();

        return webClient.get()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .bodyToMono(KakaoSocialProfileResponse.class)
            .block();
    }
}
