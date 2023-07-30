package com.offer.authentication.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OAuthLoginUrlResponse {

    private final String url;
}
