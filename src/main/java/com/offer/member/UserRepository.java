package com.offer.member;

import com.offer.member.User.OAuthType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId);
    boolean existsByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId);
    boolean existsByNickname(String nickname);

    default User getByOauthTypeAndOauthId(OAuthType oauthType, Long oauthId) {
        return findByOauthTypeAndOauthId(oauthType, oauthId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
