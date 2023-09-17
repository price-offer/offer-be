package com.offer.post.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.offer.member.Member;
import com.offer.member.Member.OAuthType;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.sort.SortType;
import com.offer.post.domain.sort.SortGroup;
import com.offer.post.domain.sort.SortGroupRepository;
import com.offer.post.domain.sort.SortItem;
import com.offer.post.domain.sort.SortItemRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
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

    @Autowired
    private SortGroupRepository sortGroupRepository;

    @Autowired
    private SortItemRepository sortItemRepository;

    @AfterEach
    void tearDown() {
        sortItemRepository.deleteAll();
        sortGroupRepository.deleteAll();
    }

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

    @DisplayName("정렬 옵션 그룹에 맞는 정렬 옵션들을 반환한다.")
    @Test
    void getPostSortType() {
        // given
        SortItem sortItem1 = new SortItem("RECENT_CREATED", "최신순");
        SortItem sortItem2 = new SortItem("LOW_PRICE", "낮은 가격순");
        SortGroup sortGroup = new SortGroup(SortType.POST);
        sortGroup.addSortItem(sortItem1);
        sortGroup.addSortItem(sortItem2);
        sortGroupRepository.save(sortGroup);
        sortItemRepository.save(sortItem1);
        sortItemRepository.save(sortItem2);

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
}
