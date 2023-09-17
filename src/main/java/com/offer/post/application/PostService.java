package com.offer.post.application;

import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.CategoryResponse;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.CategoryRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import com.offer.post.domain.SortItemRepository;
import com.offer.post.domain.SortType;
import java.util.List;
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
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long createPost(PostCreateRequest request, Long memberId) {
        Member member = memberRepository.getById(memberId);
        Post post = request.toEntity(member);
        return postRepository.save(post).getId();
    }

    public List<SortResponse> getSortItems(SortType type) {
        return sortItemRepository.get(type);
    }

    public List<CategoryResponse> getCategoryItems() {
        return categoryRepository.getCategories();
    }
}
