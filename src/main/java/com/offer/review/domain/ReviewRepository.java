package com.offer.review.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);

    boolean existsByReviewerAndPost(Member reviewer, Post post);

    int countByRevieweeIdOrReviewerId(Long revieweeId, Long reviewerId);

    Optional<Review> findByPost(Post post);

    Optional<Review> findByPostAndReviewer(Post post, Member reviewer);

    List<Review> findTop10ByReviewerOrRevieweeAndIdGreaterThanEqual(Member reviewer, Member reviewee, Long id);

    List<Review> findTop10ByReviewerAndRevieweeIsBuyerAndIdGreaterThanEqual(Member reviewer, boolean revieweeIsBuyer, Long id);

    List<Review> findTop10ByRevieweeAndRevieweeIsBuyerAndIdGreaterThanEqual(Member reviewee, boolean revieweeIsBuyer, Long id);
}
