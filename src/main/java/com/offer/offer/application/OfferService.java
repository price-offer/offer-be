package com.offer.offer.application;

import com.offer.offer.application.response.OffersResponse;
import com.offer.offer.domain.Offer;
import com.offer.offer.domain.OfferRepository;
import com.offer.post.domain.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferService {

    private final PostRepository postRepository;
    private final OfferRepository offerRepository;

    public OffersResponse getOffersByPost(Long offererId, Long postId) {
        List<Offer> offersByPost = offerRepository.findAllByPostId(postId);
        if (offererId == null) {
            return OffersResponse.of(offersByPost, postId, 0);
        }

        List<Offer> offersByOfferer = offerRepository.findAllByOffererIdAndPostId(offererId, postId);
        return OffersResponse.of(offersByPost, postId, offersByOfferer.size());
    }
}
