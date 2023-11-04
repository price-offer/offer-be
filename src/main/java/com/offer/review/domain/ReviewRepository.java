package com.offer.review.domain;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Override
    <S extends Review> S save(S entity);

    boolean existsByReviewerAndPost(Member reviewer, Post post);

    Slice<Review> findSliceByRevieweeIdAndIsRevieweeBuyer(PageRequest pageRequest, Long revieweeId, boolean IsRevieweeBuyer);

    Slice<Review> findSliceByRevieweeId(PageRequest pageRequest, Long revieweeId);
}
