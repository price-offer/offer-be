package com.offer.offer.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.offer.application.OfferService;
import com.offer.offer.application.response.OffersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/api/posts/{postId}/offers")
    public OffersResponse getOffersByPost(@PathVariable Long postId, @AuthenticationPrincipal
        LoginMember loginMember) {
        return offerService.getOffersByPost(loginMember.getId(), postId);
    }
}
