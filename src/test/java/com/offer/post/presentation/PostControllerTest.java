package com.offer.post.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.authentication.presentation.LoginMember;
import com.offer.authentication.presentation.LoginMember.Authority;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.CategoryResponse;
import com.offer.post.application.response.PostDetail;
import com.offer.post.application.response.PostSummaries;
import com.offer.post.application.response.PostSummary;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.sort.SortType;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class PostControllerTest extends DocumentationTest {

    @DisplayName("판매게시글 생성")
    @Test
    void createPost() throws Exception {
        // given
        PostCreateRequest request = PostCreateRequest.builder()
                .title("청바지 판매")
                .category("MALE_FASHION")
                .price(10000)
                .location("동작구 사당동")
                .productCondition("NEW")
                .description("남성 청바지 판매합니다.")
                .tradeType("FACE_TO_FACE")
                .thumbnailImageUrl("http://thumbnail.image.url")
                .imageUrls(List.of("http://image1.url", "http://image2.url"))
                .build();

        CommonCreationResponse commonCreationResponse = CommonCreationResponse.of(1L, LocalDateTime.now());

        given(postService.createPost(any(), any()))
                .willReturn(commonCreationResponse);

        // when
        ResultActions resultActions = mockMvc.perform(
                        post("/api/posts")
                                .header("Authorization", "Bearer jwt.token.here")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk()
                );

        // then
        resultActions.andDo(
                document("posts/create-post",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                        ),
                        requestFields(
                                fieldWithPath("title").type(STRING).description("판매 게시글 제목"),
                                fieldWithPath("description").type(STRING).description("상품 설명"),
                                fieldWithPath("category").type(STRING).description("카테고리"),
                                fieldWithPath("price").type(NUMBER).description("시작 가격"),
                                fieldWithPath("location").type(STRING).description("거래 위치"),
                                fieldWithPath("productCondition").type(STRING).description("상품 상태"),
                                fieldWithPath("tradeType").type(STRING).description("거래 방식"),
                                fieldWithPath("thumbnailImageUrl").type(STRING).description("대표 이미지 url"),
                                fieldWithPath("imageUrls").type(ARRAY).description("상품 이미지 url(대표 이미지 url을 제외한 나머지 url")
                        )
                )
        );
    }

    @DisplayName("게시글 목록 조회")
    @Disabled
    @Test
    void showPosts() throws Exception {
        // given
        PostSummary postSummary1 = PostSummary.builder()
                .id(1L)
                .title("청바지 팔아요")
                .price(10000)
                .location("동작구 사당동")
                .thumbnailImageUrl("http://thumbnail.image.url")
                .liked(true)
                .createdAt(LocalDateTime.of(2023, 9, 10, 14, 30))
                .build();

        PostSummary postSummary2 = PostSummary.builder()
                .id(2L)
                .title("티셔츠 팔아요")
                .price(11000)
                .location("동작구 사당동")
                .thumbnailImageUrl("http://thumbnail.image.url")
                .liked(true)
                .createdAt(LocalDateTime.of(2023, 9, 11, 14, 30))
                .build();

        PostSummaries response = PostSummaries.builder()
                .posts(List.of(postSummary1, postSummary2))
                .hasNext(true)
                .build();

        ApiResponse<PostSummaries> httpResponse = ApiResponse.of(ResponseMessage.SUCCESS, response);

        given(postService.getPosts(any(), new LoginMember(1L, Authority.MEMBER)))
                .willReturn(response);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                        get("/api/posts")
                                .queryParam("sort", "RECENT_CREATED")
                                .queryParam("tradeType", "FACE_TO_FACE")
                                .queryParam("category", "MAN_FASHION")
                                .queryParam("minPrice", "0")
                                .queryParam("maxPrice", "999999")
                                .queryParam("limit", "10")
                                .header("Authorization", "Bearer jwt.token.here")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(httpResponse)));

        resultActions.andDo(
                document("posts/get-posts",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        queryParameters(
                                parameterWithName("sort").optional().description("정렬 필터 name"),
                                parameterWithName("category").optional().description("카테고리 name"),
                                parameterWithName("tradeType").optional().description("거래방식 name"),
                                parameterWithName("minPrice").description("최소 가격"),
                                parameterWithName("maxPrice").description("최대 가격"),
                                parameterWithName("limit").optional().description("요청 게시글 수")
                        ),
                        responseFields(
                                // common filed of response
                                fieldWithPath("code").type(NUMBER).description("응답코드"),
                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                // body of response
                                fieldWithPath("data.posts[].id").type(NUMBER).description("게시글 아이디"),
                                fieldWithPath("data.posts[].title").type(STRING).description("게시글 제목"),
                                fieldWithPath("data.posts[].price").type(NUMBER).description("시작 가격"),
                                fieldWithPath("data.posts[].location").type(STRING).description("판매자 거래 위치"),
                                fieldWithPath("data.posts[].thumbnailImageUrl").type(STRING).description("썸네일 이미지 url"),
                                fieldWithPath("data.posts[].liked").type(BOOLEAN).description("사용자 좋아요 여부"),
                                fieldWithPath("data.posts[].createdAt").type(STRING).description("게시글 생성 시간"),
                                fieldWithPath("data.hasNext").type(BOOLEAN).description("다음 페이지 존재 여부")
                        )
                )
        );
    }

    @DisplayName("게시글 단건 조회")
    @Test
    void getPost() throws Exception {
        // given
        PostDetail response = PostDetail.builder()
                .id(1L)
                .title("티셔츠 팔아요")
                .description("티셔츠 싸게 팝니다.")
                .price(11000)
                .location("동작구 사당동")
                .imageUrls(List.of("http://thumbnail.image1.url", "http://thumbnail.image2.url"))
                .tradeType("직거래")
                .productCondition("새상품")
                .createdAt(LocalDateTime.of(2023, 9, 11, 14, 30))
                .build();

        ApiResponse<PostDetail> httpResponse = ApiResponse.of(ResponseMessage.SUCCESS, response);

        given(postService.getPost(1L))
                .willReturn(response);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                        get("/api/posts/{postId}", 1L)
                                .header("Authorization", "Bearer jwt.token.here")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(httpResponse)));

        resultActions.andDo(
                document("posts/get-single-post",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        responseFields(
                                // common filed of response
                                fieldWithPath("code").type(NUMBER).description("응답코드"),
                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                // body of response
                                fieldWithPath("data.id").type(NUMBER).description("게시글 아이디"),
                                fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                                fieldWithPath("data.description").type(STRING).description("게시글 상세 내용"),
                                fieldWithPath("data.price").type(NUMBER).description("시작 가격"),
                                fieldWithPath("data.location").type(STRING).description("판매자 거래 위치"),
                                fieldWithPath("data.imageUrls").type(ARRAY).description("상품 이미지 url"),
                                fieldWithPath("data.tradeType").type(STRING).description("거래 방식"),
                                fieldWithPath("data.productCondition").type(STRING).description("상품 상태"),
                                fieldWithPath("data.createdAt").type(STRING).description("게시글 생성 시간")
                        )
                )
        );
    }

    @DisplayName("정렬 조회")
    @Test
    void showSortItems() throws Exception {
        // given
        String type = "post";
        SortResponse recentItem = SortResponse.builder()
                .name("RECENT_CREATED")
                .exposureTitle("최신순")
                .build();
        SortResponse lowPrice = SortResponse.builder()
                .name("LOW_PRICE")
                .exposureTitle("낮은 가격순")
                .build();
        List<SortResponse> response = List.of(recentItem, lowPrice);

        given(postService.getSortItems(SortType.POST))
                .willReturn(response);

        ApiResponse<List<SortResponse>> httpResponse = ApiResponse.of(ResponseMessage.SUCCESS, response);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                        get("/api/sorts")
                                .queryParam("type", type)
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(httpResponse)));

        resultActions.andDo(
                document("posts/read-sortItems",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        queryParameters(
                                parameterWithName("type").description("정렬 옵션 그룹 타입")
                        ),
                        responseFields(
                                // common filed of response
                                fieldWithPath("code").type(NUMBER).description("응답코드"),
                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                // body of response
                                fieldWithPath("data.[].name").type(STRING).description("정렬옵션 이름"),
                                fieldWithPath("data.[].exposureTitle").type(STRING).description("화면에 표시될 타이틀")
                        )
                )
        );
    }

    @DisplayName("카테고리 목록 조회")
    @Test
    void showCategories() throws Exception {
        // given
        CategoryResponse category1 = CategoryResponse.builder()
                .name("MAN_FASHION")
                .exposureTitle("남성패션/잡화")
                .build();
        CategoryResponse category2 = CategoryResponse.builder()
                .name("WOMAN_FASHION")
                .exposureTitle("여성패션/잡화")
                .build();
        CategoryResponse category3 = CategoryResponse.builder()
                .name("GAME")
                .exposureTitle("게임")
                .build();

        List<CategoryResponse> response = List.of(category1, category2, category3);

        ApiResponse<List<CategoryResponse>> httpResponse = ApiResponse.of(ResponseMessage.SUCCESS, response);

        given(postService.getCategoryItems())
                .willReturn(response);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                        get("/api/categories")
                )
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        content().string(objectMapper.writeValueAsString(httpResponse)));

        resultActions.andDo(
                document("posts/read-category-items",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        responseFields(
                                // common filed of response
                                fieldWithPath("code").type(NUMBER).description("응답코드"),
                                fieldWithPath("message").type(STRING).description("응답 메시지"),
                                // body of response
                                fieldWithPath("data.[].name").type(STRING).description("카테고리 옵션 이름"),
                                fieldWithPath("data.[].exposureTitle").type(STRING).description("화면에 표시될 타이틀")
                        )
                )
        );
    }
}
