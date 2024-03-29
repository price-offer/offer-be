package com.offer.post.domain;

import static com.offer.post.domain.QPost.post;

import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostReadParams;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.PostSummary;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.domain.ReviewRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory query;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public PostSummaries searchPost(PostReadParams params, Long loginMemberId) {
        Long sellerId = params.getSellerId();
        Member seller = sellerId != null && memberRepository.existsById(sellerId) ?
            memberRepository.getById(sellerId) : null;

        Set<Long> likePostIds = getLikePostId(loginMemberId);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (params.getLastId() != null) {
            booleanBuilder.and(post.id.lt(params.getLastId()));
        }

        List<Post> posts = query.selectFrom(post)
            .where(lastIdLt(params.getLastId()),
                categoryEq(params.getCategory()),
                priceBetween(params.getMinPrice(), params.getMaxPrice()),
                sellerEq(seller),
                keywordContains(params.getSearchKeyword()),
                tradeStatus(TradeStatus.from(params.getTradeStatus())),
                tradeType(TradeType.from(params.getTradeType()))
            )
            .orderBy(order(params.getSort()))
            .limit(params.getLimit() + 1)
            .fetch();

        int totalCount = query.selectFrom(post)
          .where(lastIdLt(params.getLastId()),
            categoryEq(params.getCategory()),
            priceBetween(params.getMinPrice(), params.getMaxPrice()),
            sellerEq(seller),
            keywordContains(params.getSearchKeyword()),
            tradeStatus(TradeStatus.from(params.getTradeStatus())),
            tradeType(TradeType.from(params.getTradeType()))
          )
          .fetch()
          .size();

        if (posts.size() > params.getLimit()) {
            posts.remove(params.getLimit());
            return PostSummaries.builder()
                .totalCount(totalCount)
                .posts(posts.stream()
                    .map(post -> {
                        int likeCount = likeRepository.countByPost(post);
                        ReviewInfoResponse review = getReviewInfoResponse(post, seller);
                        return PostSummary.from(post, likePostIds, likeCount, review);
                    })
                    .collect(Collectors.toList()))
                .hasNext(true)
                .build();
        }

        return PostSummaries.builder()
            .totalCount(totalCount)
            .posts(posts.stream()
                .map(post -> {
                    int likeCount = likeRepository.countByPost(post);
                    ReviewInfoResponse review = getReviewInfoResponse(post, seller);
                    return PostSummary.from(post, likePostIds, likeCount, review);
                })
                .collect(Collectors.toList()))
            .hasNext(false)
            .build();
    }

    private OrderSpecifier<?> order(String sort) {
        if (sort == null || sort.equals("CREATED_DATE_DESC")) {
            return post.id.desc();
        }
        if (sort.equals("PRICE_DESC")) {
            return post.price.desc();
        }
        return post.price.asc();
    }

    private ReviewInfoResponse getReviewInfoResponse(Post post, Member seller) {
        if (seller == null) {
            return null;
        }

        ReviewInfoResponse review = reviewRepository.findByPostAndReviewer(post, seller)
            .map(it -> ReviewInfoResponse.buildReviewInfoResponse(it, seller))
            .orElse(null);
        return review;
    }

    private Set<Long> getLikePostId(Long memberId) {
        if (memberId == null) {
            return Collections.emptySet();
        }
        return likeRepository.findAllByMemberId(memberId).stream()
            .map(like -> like.getPost().getId())
            .collect(Collectors.toSet());
    }

    private BooleanExpression lastIdLt(Long lastId) {
        if (lastId == null) {
            return null;
        }
        return post.id.lt(lastId);
    }

    private BooleanExpression categoryEq(String category) {
        return StringUtils.hasText(category) ? post.category.eq(category) : null;
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice != null ? post.price.goe(minPrice) : null;
    }

    private BooleanExpression sellerEq(Member seller) {
        return seller != null ? post.seller.eq(seller) : null;
    }

    private BooleanExpression tradeStatus(TradeStatus tradeStatus) {
        return tradeStatus != TradeStatus.UNKNOWN ? post.tradeStatus.eq(tradeStatus) : null;
    }

    private BooleanExpression tradeType(TradeType tradeType) {
        return tradeType == TradeType.UNKNOWN ? null :
          post.tradeType.eq(tradeType).or(post.tradeType.eq(TradeType.ALL));
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ? post.price.loe(maxPrice) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        return priceGoe(minPrice).and(priceLoe(maxPrice));
    }

    private BooleanExpression keywordContains(String searchKeyword) {
        return StringUtils.hasText(searchKeyword) ? post.title.contains(searchKeyword) : null;
    }
}
