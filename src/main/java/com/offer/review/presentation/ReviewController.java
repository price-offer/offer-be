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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "리뷰")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @ApiOperation(value = "특정 유저에게 리뷰 추가")
    @PostMapping("/reviews")
    public ResponseEntity<ApiResponse> createReview(@AuthenticationPrincipal LoginMember loginMember,
                                                    @RequestBody ReviewCreateRequest request) {
        CommonCreationResponse response = reviewService.createReview(request, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @ApiOperation(value = "특정 유저가 받은 리뷰 전체 조회")
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse> getReviews(@AuthenticationPrincipal LoginMember loginMember,
                                                  @RequestParam(value = "memberId", required = true) Long memberId,
                                                  @RequestParam(value = "role", required = false) String role) {

        List<ReviewInfoResponse> response = reviewService.getReviews(memberId, Role.of(role));

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
