package com.offer.post.domain;

import static com.offer.post.domain.QPost.post;

import com.offer.post.application.request.PostReadParams;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.PostSummary;
import com.querydsl.core.BooleanBuilder;
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

    public PostSummaries searchPost(PostReadParams params, Long memberId) {

        Set<Long> likePostIds = getLikePostId(memberId);

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (params.getLastId() != null) {
            booleanBuilder.and(post.id.lt(params.getLastId()));
        }

        List<Post> posts = query.selectFrom(post)
            .where(lastIdLt(params.getLastId()),
                categoryEq(params.getCategory()),
                priceBetween(params.getMinPrice(), params.getMaxPrice())
            )
            .orderBy(post.id.desc())
            .limit(params.getLimit() + 1)
            .fetch();

        if (posts.size() > params.getLimit()) {
            posts.remove(params.getLimit());
            return PostSummaries.builder()
                .posts(posts.stream()
                    .map(post -> PostSummary.from(post, likePostIds))
                    .collect(Collectors.toList()))
                .hasNext(true)
                .build();
        }

        return PostSummaries.builder()
            .posts(posts.stream()
                .map(post -> PostSummary.from(post, likePostIds))
                .collect(Collectors.toList()))
            .hasNext(false)
            .build();
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

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ? post.price.loe(maxPrice) : null;
    }

    private BooleanExpression priceBetween(Integer minPrice, Integer maxPrice) {
        return priceGoe(minPrice).and(priceLoe(maxPrice));
    }
}
