package com.offer.review.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);

    boolean existsByReviewerAndPost(Member reviewer, Post post);

    List<Review> getAllByRevieweeIdAndIsRevieweeBuyer(Long revieweeId, boolean IsRevieweeBuyer);

    List<Review> getAllByRevieweeId(Long revieweeId);
}
