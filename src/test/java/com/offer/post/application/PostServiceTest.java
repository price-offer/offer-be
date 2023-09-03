package com.offer.post.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.offer.member.Member;
import com.offer.member.Member.OAuthType;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.SortType;
import java.util.Collections;
import java.util.List;
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

    @DisplayName("게시글용 정렬 옵션들을 반환한다.")
    @Test
    void getPostSortType() {
        // given
        SortResponse recentItem = SortResponse.builder()
            .name("RECENT_CREATED")
            .exposureTitle("최신순")
            .build();
        SortResponse lowPrice = SortResponse.builder()
            .name("LOW_PRICE")
            .exposureTitle("낮은 가격순")
            .build();
        List<SortResponse> expect = List.of(recentItem, lowPrice);

        // when
        List<SortResponse> result = sut.getSortItems(SortType.POST);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expect);
    }

    @DisplayName("가격제안용 정렬 옵션들을 반환한다.")
    @Test
    void getOfferSortType() {
        // given
        SortResponse highPrice = SortResponse.builder()
            .name("HIGH_PRICE")
            .exposureTitle("높은 가격순")
            .build();
        SortResponse recentItem = SortResponse.builder()
            .name("RECENT_CREATED")
            .exposureTitle("최신순")
            .build();
        List<SortResponse> expect = List.of(highPrice, recentItem);


        // when
        List<SortResponse> result = sut.getSortItems(SortType.OFFER);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expect);
    }
}
