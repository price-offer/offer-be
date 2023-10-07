package com.offer.client;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoTokenClient {

    private final WebClient.Builder webClientBuilder;

    public String inquireToken(String clientId, String clientSecret, String redirectUrl,
        String authCode) {
        WebClient webClient = webClientBuilder.baseUrl("https://kauth.kakao.com/oauth/token")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUrl);
        formData.add("code", authCode);


        return webClient.post()
            .body(BodyInserters.fromFormData(formData))
            .retrieve()
            .bodyToMono(KakaoAccessTokenResponse.class)
            .block()
            .getAccessToken();
    }
}
