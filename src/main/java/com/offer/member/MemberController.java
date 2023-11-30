package com.offer.member;

import com.offer.authentication.application.response.MemberResponse;
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.member.request.MemberUpdateRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "닉네임 중복 조회")
    @PostMapping("/nickname-duplicate")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> getNicknameDuplication(String nickname) {
        boolean exists = memberService.hasNickname(nickname);
        HashMap<String, Boolean> result = new HashMap<>();
        result.put("duplicate", exists);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, result)
        );
    }

    @Operation(summary = "회원정보 조회(토큰으로)", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/member/access-token/me")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember) {
        log.info("getMember = {}", loginMember);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, memberService.getMember(loginMember.getId()))
        );
    }

    @Operation(summary = "회원정보 수정", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/member/{memberId}")
    public ResponseEntity<ApiResponse<Long>> updateMember(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long memberId, MemberUpdateRequest request) {
        log.info("getMember = {}", loginMember);
        if (loginMember.getId() == null) {
            throw new IllegalArgumentException("잘못된 토큰");
        }
        if (!loginMember.getId().equals(memberId)) {
            throw new IllegalArgumentException("잘못된 회원번호");
        }
        Long updateMemberId = memberService.updateMember(memberId, request);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, updateMemberId)
        );
    }
}
