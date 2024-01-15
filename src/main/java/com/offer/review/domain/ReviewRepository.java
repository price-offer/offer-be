package com.offer.review.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);

    boolean existsByReviewerAndPost(Member reviewer, Post post);

    int countByRevieweeId(Long revieweeId);

    Optional<Review> findByPost(Post post);

    Optional<Review> findByPostAndReviewer(Post post, Member reviewer);

    List<Review> findTop10ByRevieweeAndIdGreaterThanEqual(Member reviewee, Long id);

    int countByReviewerAndRevieweeIsBuyer(Member reviewer, boolean revieweeIsBuyer);

    List<Review> findTop10ByRevieweeAndRevieweeIsBuyerAndIdGreaterThanEqual(Member reviewee, boolean revieweeIsBuyer, Long id);
    int countByRevieweeAndRevieweeIsBuyer(Member reviewee, boolean revieweeIsBuyer);
}
