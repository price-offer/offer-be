package com.offer.authentication.presentation;

import com.offer.authentication.JwtTokenProvider;
import com.offer.authentication.application.OAuthService;
import com.offer.authentication.application.response.MemberResponse;
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.application.response.OAuthLoginUrlResponse;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final OAuthService oAuthService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Operation(summary = "카카오 로그인 페이지 url 조회")
    @GetMapping("authorization/kakao-url")
    public OAuthLoginUrlResponse showKakaoLoginUrl() {
        return oAuthService.getKakaoLoginUrl();
    }

    @Operation(summary = "로그인 (카카오 인가코드로)")
    @GetMapping("login/kakao")
    public ResponseEntity<ApiResponse<OAuthLoginResponse>> kakaoLogin(@RequestParam String code) {
        log.info("auth code = {}", code);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, oAuthService.kakaoLogin(code))
        );
    }

    @Operation(summary = "회원정보 조회(토큰으로)", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("member/access-token/me")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember) {
        log.info("getMember = {}", loginMember);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, oAuthService.getMember(loginMember.getId()))
        );
    }

    /**
     * 토큰 조회(개발용)
     * */
    @Operation(summary = "토큰 조회(개발용)")
    @GetMapping("/token/{memberId}")
    public Map<String, String> token(@PathVariable Long memberId) {
        HashMap<String, String> result = new HashMap<>();
        result.put("token", String.valueOf(jwtTokenProvider.createToken(String.valueOf(memberId))));
        return result;
    }

    /**
     * 회원 삭제 (개발용)
     */
    @Operation(summary = "회원 삭제(개발용)", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/delete")
    public ResponseEntity<Void> deleteMember(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember) {

        Long id = loginMember.getId();
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        memberRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
