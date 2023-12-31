package com.offer.review.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.review.application.ReviewService;
import com.offer.review.application.request.ReviewCreateRequest;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.application.response.ReviewInfoResponses;
import com.offer.review.domain.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "리뷰 생성", security = {@SecurityRequirement(name = "jwt")})
    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> createReview(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody ReviewCreateRequest request) {

        CommonCreationResponse response = reviewService.createReview(request, loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "리뷰 조회", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<ReviewInfoResponses>> getReviews(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestParam Long memberId,
        @RequestParam(defaultValue = "ALL") String role,
        @RequestParam(defaultValue = "1") Long lastId,
        @RequestParam(defaultValue = "10") int limit
        ) {

        ReviewInfoResponses response = reviewService.getReviews(memberId, Role.of(role), lastId, limit);

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
