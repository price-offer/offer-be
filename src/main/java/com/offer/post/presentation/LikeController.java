package com.offer.post.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.post.application.LikeService;
import com.offer.post.application.request.ToggleLikeRequest;
import com.offer.post.application.response.PostSummary;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @Operation(summary = "좋아요 상태 변환")
    @PutMapping("/posts/likes")
    public ResponseEntity<ApiResponse> toggle(@AuthenticationPrincipal LoginMember loginMember,
                                              @RequestBody ToggleLikeRequest request) {
        likeService.toggleStatus(request, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS)
        );
    }

    @Operation(summary = "내가 좋아한 모든 게시물")
    @GetMapping("/posts/likes")
    public ResponseEntity<ApiResponse> getAll(@AuthenticationPrincipal LoginMember loginMember) {

        List<PostSummary> response = likeService.findLikePosts(loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
