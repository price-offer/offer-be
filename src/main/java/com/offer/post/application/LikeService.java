package com.offer.post.application;

import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.ToggleLikeRequest;
import com.offer.post.application.response.PostSummary;
import com.offer.post.domain.Like;
import com.offer.post.domain.LikeRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
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
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BusinessException(ResponseMessage.MEMBER_NOT_FOUND));
            Post post = postRepository.findById(postId)
                    .orElseThrow(() -> new BusinessException(ResponseMessage.POST_NOT_FOUND));

            Like like = new Like(member, post);
            likeRepository.save(like);
        }
    }

    @Transactional(readOnly = true)
    public List<PostSummary> findLikePosts(Long memberId) {
        List<Like> likes = likeRepository.findAllByMemberId(memberId);

        return likes.stream()
                .map(l -> PostSummary.from(l.getPost(), true))
                .collect(Collectors.toList());
    }
}
