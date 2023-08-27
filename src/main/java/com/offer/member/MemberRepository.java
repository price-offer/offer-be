package com.offer.member;

import com.offer.member.Member.OAuthType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId);
    boolean existsByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId);
    boolean existsByNickname(String nickname);

    default Member getByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId) {
        return findByOauthTypeAndOauthId(oauthType, oauthId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    default Member getById(Long id) {
        return findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id: " + id));
    }
}
