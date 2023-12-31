package com.offer.offer.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findAllByOffererIdAndPostId(Long offererId, Long postId);

    List<Offer> findAllByPostOrderByIdDesc(Post post);
    List<Offer> findAllByPostOrderByPriceDesc(Post post);

    List<Offer> findAllByOfferer(Member offerer);

    List<Offer> findAllByOffererOrderByIdDesc(Member offerer);

    int countByPostAndOfferer(Post post, Member offerer);

    List<Offer> findAllByOffererOrderByPriceDesc(Member offerer);

    List<Offer> findAllByOffererOrderByPriceAsc(Member offerer);
}
