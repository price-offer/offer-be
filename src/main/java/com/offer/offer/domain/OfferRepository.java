package com.offer.offer.domain;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByOffererIdAndPostId(Long offererId, Long postId);

    Slice<Offer> findSliceByPostId(PageRequest pageRequest, Long postId);
}
