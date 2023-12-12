package com.offer.post.domain;

import com.offer.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    int countBySellerAndTradeStatus(Member member, TradeStatus tradeStatus);

    List<Post> findAllBySeller(Member member);

    default Post getById(Long id) {
        return findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 포스트입니다. id=" + id));
    }
}
