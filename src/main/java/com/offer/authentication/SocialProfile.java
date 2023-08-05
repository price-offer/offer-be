package com.offer.authentication;

import com.offer.member.User.OAuthType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SocialProfile {

    private final OAuthType oauthType;
    private final Long oauthId;
    private final String profileImageUrl;
}
