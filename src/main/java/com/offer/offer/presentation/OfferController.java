package com.offer.offer.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.offer.application.OfferService;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OfferSummaries;
import com.offer.offer.application.response.OffersResponse;
import com.offer.post.application.request.OfferReadParams;
import com.offer.post.application.response.PostSummaries;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @Operation(summary = "가격제안 생성", security = {@SecurityRequirement(name = "jwt")})
    @PostMapping("/api/posts/{postId}/offers")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> createOffer(
        @PathVariable Long postId, @RequestBody OfferCreateRequest request,
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember) {

        CommonCreationResponse response = offerService.createOffer(postId, request,
            loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "단건 게시글에 대한 가격제안 조회")
    @GetMapping("/api/posts/{postId}/offers")
    public ResponseEntity<ApiResponse<OffersResponse>> getAllOffersByPost(@PathVariable Long postId,
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestParam(required = false) String sort) {

        OffersResponse response = offerService.getAllOffersByPost(postId, loginMember.getId(), sort);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "내 가격제안 조회", security = {@SecurityRequirement(name = "jwt")})
    @GetMapping("/api/posts/offers")
    public ResponseEntity<ApiResponse<OfferSummaries>> showMyOfferPosts(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        OfferReadParams params) {

        OfferSummaries response = offerService.getAllOffersByMember(params, loginMember);
        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
