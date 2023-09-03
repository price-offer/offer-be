package com.offer.post.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.post.application.PostService;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.SortType;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@AuthenticationPrincipal LoginMember loginMember,
        @RequestBody PostCreateRequest request) {
        Long postId = postService.createPost(request, loginMember.getId());
        return ResponseEntity.created(URI.create("/api/posts/" + postId)).build();
    }

    // TODO: #14 추후 분리
    @GetMapping("/sorts")
    public List<SortResponse> showSortItems(String type) {
        return postService.getSortItems(SortType.from(type));
    }
}
