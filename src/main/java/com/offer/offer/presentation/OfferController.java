package com.offer.offer.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.offer.application.OfferService;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OffersResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/api/posts/{postId}/offers/me")  // TODO: refactor REST API
    public ResponseEntity<ApiResponse> getOffersByPost(@PathVariable Long postId,
                                                       @AuthenticationPrincipal LoginMember loginMember) {
        OffersResponse response = offerService.getOffersByPost(loginMember.getId(), postId);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "가격제안 생성")
    @PostMapping("/api/posts/{postId}/offers")
    public ResponseEntity<ApiResponse> createOffer(@PathVariable Long postId,
                                                   @RequestBody OfferCreateRequest request,
                                                   @AuthenticationPrincipal LoginMember loginMember) {
        CommonCreationResponse response = offerService.createOffer(postId, request, loginMember.getId());

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "단건 게시글에 대한 가격제안 조회")
    @GetMapping("/api/posts/{postId}/offers")
    public ResponseEntity<ApiResponse> getAllOffersByPost(@PathVariable Long postId,
                                                          @AuthenticationPrincipal LoginMember loginMember) {
        OffersResponse response = offerService.getAllOffersByPost(postId);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
