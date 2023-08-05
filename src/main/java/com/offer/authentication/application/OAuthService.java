package com.offer.authentication.application;

import com.offer.authentication.JwtTokenProvider;
import com.offer.authentication.KakaoOAuthGateway;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final RandomNicknameGenerator randomNicknameGenerator;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuthLoginUrlResponse getKakaoLoginUrl() {
        return new OAuthLoginUrlResponse(kakaoOAuthGateway.getLoginUrl());
    }

    public OAuthLoginResponse kakaoLogin(String authCode) {
        SocialProfile socialProfile = kakaoOAuthGateway.getUserProfile(authCode);
        boolean alreadyJoined = memberRepository.existsByOauthTypeAndOauthId(socialProfile.getOauthType(),
            socialProfile.getOauthId());
        Member member = findOrCreateMember(alreadyJoined, socialProfile);
        String token = jwtTokenProvider.createToken(String.valueOf(member.getId()));

        return OAuthLoginResponse.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .accessToken(token)
            .newMember(alreadyJoined)
            .build();
    }

    private Member findOrCreateMember(boolean alreadyJoined, SocialProfile socialProfileResponse) {
        if (alreadyJoined) {
            return memberRepository.getByOauthTypeAndOauthId(socialProfileResponse.getOauthType(),
                socialProfileResponse.getOauthId());
        }
        return createNewMember(socialProfileResponse);
    }

    private Member createNewMember(SocialProfile socialProfile) {
        String nickname = randomNicknameGenerator.generateRandomNickname();
        while (memberRepository.existsByNickname(nickname)) {
            nickname = randomNicknameGenerator.generateRandomNickname();
        }
        return memberRepository.save(Member.builder()
            .oauthId(socialProfile.getOauthId())
            .oauthType(socialProfile.getOauthType())
            .nickname(nickname)
            .profileImageUrl(socialProfile.getProfileImageUrl())
            .build());
    }
}
