package com.offer.authentication.application;

import com.offer.authentication.JwtTokenProvider;
import com.offer.authentication.KakaoOAuthGateway;
import com.offer.member.User;
import com.offer.member.UserRepository;
import com.offer.member.RandomNicknameGenerator;
import com.offer.authentication.SocialProfile;
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
    private final UserRepository userRepository;
    private final RandomNicknameGenerator randomNicknameGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuthLoginUrlResponse getKakaoLoginUrl() {
        return new OAuthLoginUrlResponse(kakaoOAuthGateway.getLoginUrl());
    }

    public OAuthLoginResponse kakaoLogin(String authCode) {
        SocialProfile socialProfile = kakaoOAuthGateway.getUserProfile(authCode);
        boolean alreadyJoined = userRepository.existsByOauthTypeAndOauthId(socialProfile.getOauthType(),
            socialProfile.getOauthId());
        User user = findOrCreateUser(alreadyJoined, socialProfile);
        String token = jwtTokenProvider.createToken(String.valueOf(user.getId()));

        return OAuthLoginResponse.builder()
            .id(user.getId())
            .nickname(user.getNickname())
            .accessToken(token)
            .newUser(alreadyJoined)
            .build();
    }

    private User findOrCreateUser(boolean alreadyJoined, SocialProfile socialProfileResponse) {
        if (alreadyJoined) {
            return userRepository.getByOauthTypeAndOauthId(socialProfileResponse.getOauthType(),
                socialProfileResponse.getOauthId());
        }
        return createNewUser(socialProfileResponse);
    }

    private User createNewUser(SocialProfile socialProfile) {
        String nickname = randomNicknameGenerator.generateRandomNickname();
        while (userRepository.existsByNickname(nickname)) {
            nickname = randomNicknameGenerator.generateRandomNickname();
        }
        return userRepository.save(User.builder()
            .oauthId(socialProfile.getOauthId())
            .oauthType(socialProfile.getOauthType())
            .nickname(nickname)
            .profileImageUrl(socialProfile.getProfileImageUrl())
            .build());
    }
}
