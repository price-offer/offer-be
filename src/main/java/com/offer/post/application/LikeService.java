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
import com.offer.utils.SliceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    private final int DEFAULT_SLICE_SIZE;

    public LikeService(LikeRepository likeRepository,
                       MemberRepository memberRepository,
                       PostRepository postRepository,
                       @Value("${slice.default-size}") int DEFAULT_SLICE_SIZE) {
        this.likeRepository = likeRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.DEFAULT_SLICE_SIZE = DEFAULT_SLICE_SIZE;
    }

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
    public List<PostSummary> findLikePosts(int page, Long memberId) {
        PageRequest pageRequest = PageRequest.of(SliceUtils.getSliceNumber(page), DEFAULT_SLICE_SIZE);

        Slice<Like> likes = likeRepository.findSliceByMemberId(pageRequest, memberId);

        return likes.stream()
                .map(l -> PostSummary.from(l.getPost(), true))
                .collect(Collectors.toList());
    }
}
