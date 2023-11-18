package com.offer.post.presentation;

import com.offer.authentication.presentation.AuthenticationPrincipal;
import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.post.application.PostService;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.request.PostReadParams;
import com.offer.post.application.response.CategoryResponse;
import com.offer.post.application.response.PostDetail;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.sort.SortType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "게시글 생성")
    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<CommonCreationResponse>> createPost(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @RequestBody PostCreateRequest request) {

        CommonCreationResponse response = postService.createPost(request, loginMember.getId());

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "게시글 목록 조회", security = {@SecurityRequirement(name = "bearer-key")})
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostSummaries>> showPosts(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        PostReadParams params) {

        PostSummaries response = postService.getPosts(params, loginMember);

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "게시글 단건 조회")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDetail>> showPost(
        @Schema(hidden = true) @AuthenticationPrincipal LoginMember loginMember,
        @PathVariable Long postId) {

        PostDetail response = postService.getPost(postId);

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    // TODO: #14 추후 분리
    @Operation(summary = "정렬 옵션 목록 조회")
    @GetMapping("/sorts")
    public ResponseEntity<ApiResponse<List<SortResponse>>> showSortItems(String type) {
        List<SortResponse> response = postService.getSortItems(SortType.from(type));

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "카테고리 목록 조회")
    @GetMapping("/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> showCategories() {
        List<CategoryResponse> response = postService.getCategoryItems();

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }
}
