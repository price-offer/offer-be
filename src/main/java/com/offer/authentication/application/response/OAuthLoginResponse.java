package com.offer.authentication.application.response;

import lombok.Getter;

@Getter
public class OAuthLoginResponse {

    private Long id;
    private String username;
    private String accessToken;
    private Boolean newUser;
}
