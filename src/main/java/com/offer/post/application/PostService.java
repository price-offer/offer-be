package com.offer.post.application;

import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.request.PostReadParams;
import com.offer.post.application.response.CategoryResponse;
import com.offer.post.application.response.PostDetail;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.PostSummary;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.PostQueryRepository;
import com.offer.post.domain.category.Category;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final SortItemRepository sortItemRepository;
    private final SortGroupRepository sortGroupRepository;
    private final CategoryRepository categoryRepository;
    private final PostQueryRepository postQueryRepository;

    @Transactional
    public Long createPost(PostCreateRequest request, Long memberId) {
        Member member = memberRepository.getById(memberId);
        Post post = request.toEntity(member);
        return postRepository.save(post).getId();
    }

    public PostSummaries getPosts(PostReadParams params) {
        return postQueryRepository.searchPost(params);
    }

    public PostDetail getPost(Long postId) {
        Post post = postRepository.getById(postId);
        return PostDetail.from(post);
    }

    public List<SortResponse> getSortItems(SortType type) {
        SortGroup sortGroup = sortGroupRepository.findBySortType(type);
        List<SortItem> sortItems = sortGroup.getSortItems();
        return sortItems.stream()
            .map(SortResponse::from)
            .collect(Collectors.toList());
    }

    public List<CategoryResponse> getCategoryItems() {
        return categoryRepository.findAll().stream()
            .map(CategoryResponse::from)
            .collect(Collectors.toList());
    }
}
