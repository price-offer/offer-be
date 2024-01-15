package com.offer.post.application;

import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.SortPageReadParam;
import com.offer.post.application.request.ToggleLikeRequest;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.PostSummary;
import com.offer.post.domain.Like;
import com.offer.post.domain.LikeRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void toggleStatus(ToggleLikeRequest request, Long memberId) {
        Long postId = request.getPostId();
        Optional<Like> likeOpt = likeRepository.findByPostId(postId);

        if (likeOpt.isPresent()) {
            likeRepository.delete(likeOpt.get());
        } else {
            Member member = memberRepository.getById(memberId);
            Post post = postRepository.getById(postId);

            Like like = new Like(member, post);
            likeRepository.save(like);
        }
    }

    @Transactional(readOnly = true)
    public PostSummaries findLikePosts(SortPageReadParam params, Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("잘못된 토큰입니다");
        }

        Member member = memberRepository.getById(memberId);
        List<Like> likes = likeRepository.findAllByMemberOrderByIdDesc(member);
        List<Post> posts = likes.stream()
            .map(Like::getPost)
            .collect(Collectors.toList());

        if (params.getSort() != null && params.getSort().equals("PRICE_DESC")) {
            posts = posts.stream()
                .sorted((o1, o2) -> -Integer.compare(o1.getPrice(), o2.getPrice()))
                .collect(Collectors.toList());
        }

        if (posts.size() > params.getLimit()) {
            posts.remove(params.getLimit());
            return PostSummaries.builder()
                .posts(posts.stream()
                    .map(post -> {
                        int likeCount = likeRepository.countByPost(post);
                        return PostSummary.from(post, true, likeCount);
                    })
                    .collect(Collectors.toList()))
                .hasNext(true)
                .build();
        }


        return PostSummaries.builder()
            .posts(posts.stream()
                .map(post -> {
                    int likeCount = likeRepository.countByPost(post);
                    return PostSummary.from(post, true, likeCount);
                })
                .collect(Collectors.toList()))
            .hasNext(false)
            .build();
    }
}
