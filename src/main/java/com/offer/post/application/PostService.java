package com.offer.post.application;

import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.CommonCreationResponse;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.request.PostReadParams;
import com.offer.post.application.request.PostUpdateRequest;
import com.offer.post.application.request.TradeStatusUpdateRequest;
import com.offer.post.application.response.CategoryResponse;
import com.offer.post.application.response.PostDetail;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.PostImage;
import com.offer.post.domain.PostQueryRepository;
import com.offer.post.domain.TradeStatus;
import com.offer.post.domain.category.CategoryRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import com.offer.post.domain.sort.SortGroup;
import com.offer.post.domain.sort.SortGroupRepository;
import com.offer.post.domain.sort.SortItem;
import com.offer.post.domain.sort.SortItemRepository;
import com.offer.post.domain.sort.SortType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final SortItemRepository sortItemRepository;
    private final SortGroupRepository sortGroupRepository;
    private final CategoryRepository categoryRepository;
    private final PostQueryRepository postQueryRepository;

    @Transactional
    public CommonCreationResponse createPost(PostCreateRequest request, Long memberId) {
        Member member = memberRepository.getById(memberId);
        Post post = request.toEntity(member);

        List<String> imageUrls = request.getImageUrls();
        for (String imageUrl : imageUrls) {
            PostImage postImage = new PostImage(imageUrl);
            post.addImage(postImage);
        }
        post = postRepository.save(post);

        return CommonCreationResponse.of(post.getId(), post.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public PostSummaries getPosts(PostReadParams params, LoginMember loginMember) {
        return postQueryRepository.searchPost(params, loginMember.getId());
    }

    @Transactional(readOnly = true)
    public PostDetail getPost(Long postId) {
        Post post = postRepository.getById(postId);
        return PostDetail.from(post, categoryRepository.findByName(post.getCategory()));
    }

    @Transactional(readOnly = true)
    public List<SortResponse> getSortItems(SortType type) {
        SortGroup sortGroup = sortGroupRepository.findBySortType(type);
        List<SortItem> sortItems = sortGroup.getSortItems();
        return sortItems.stream()
            .map(SortResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategoryItems() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public Long updateTradeStatus(Long postId, TradeStatusUpdateRequest request, Long memberId) {
        Post post = postRepository.getById(postId);
        if (!post.getSeller().getId().equals(memberId)) {
            throw new IllegalArgumentException("토큰 정보가 없거나 게시글 작성자가 아닙니다.");
        }
        TradeStatus ts = TradeStatus.from(request.getTradeStatus().toUpperCase());
        if (ts == TradeStatus.UNKNOWN) {
            throw new IllegalArgumentException("Illegal trade status code = " + request.getTradeStatus());
        }
        post.updateTradeStatus(ts);
        return post.getId();
    }

    @Transactional
    public PostDetail updatePost(Long postId, PostUpdateRequest request, Long memberId) {
        Member member = memberRepository.getById(memberId);
        Post post = postRepository.getById(postId);
        if (!post.getSeller().equals(member)) {
            throw new IllegalArgumentException(
                "판매자와 토큰값이 일치하지 않음. memberId = " + member + " SellerId = " + post.getSeller()
                    .getId());
        }
        post.update(request);
        return PostDetail.from(post, categoryRepository.findByName(post.getCategory()));
    }

    @Transactional
    public Long deletePost(Long postId, Long memberId) {
        Member member = memberRepository.getById(memberId);
        Post post = postRepository.getById(postId);

        if (!post.getSeller().equals(member)) {
            throw new IllegalArgumentException(
                "판매자와 토큰값이 일치하지 않음. memberId = " + member + " SellerId = " + post.getSeller()
                    .getId());
        }
        postRepository.delete(post);
        return postId;
    }
}
