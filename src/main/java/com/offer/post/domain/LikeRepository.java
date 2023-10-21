package com.offer.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Override
    <S extends Like> S save(S entity);

    Optional<Like> findByPostId(Long postId);

    List<Like> findAllByMemberId(Long memberId);
}
