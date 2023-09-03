package com.offer.post.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.offer.member.Member;
import com.offer.member.Member.OAuthType;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService sut;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("포스트를 생성하고 저장한다.")
    @Test
    void createPost() {
        // given
        Member member = memberRepository.save(Member.builder()
            .oauthId(1L)
            .oauthType(OAuthType.KAKAO)
            .nickname("행복한 냉장고 3호")
            .build());

        PostCreateRequest request = PostCreateRequest.builder()
            .title("청바지 판매")
            .category("MALE_FASHION")
            .price(10000)
            .location("동작구 사당동")
            .productCondition("NEW")
            .description("남성 청바지 판매합니다.")
            .tradeType("FACE_TO_FACE")
            .thumbnailImageUrl("image.com")
            .imageUrls(Collections.emptyList())
            .build();

        // when
        Long postId = sut.createPost(request, member.getId());

        // then
        assertThat(postId).isNotNull();
    }
}
