package com.offer.review.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.review.application.ReviewService;
import com.offer.review.application.request.ReviewCreateRequest;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> createReview(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody ReviewCreateRequest request) {

        CommonCreationResponse response = reviewService.createReview(request, loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<ReviewInfoResponse>>> getReviews(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestParam(value = "memberId", required = true) Long memberId,
        @RequestParam(value = "role", required = false) String role,
        @RequestParam(required = true) int page) {

        List<ReviewInfoResponse> response = reviewService.getReviews(page, memberId, Role.of(role));

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
