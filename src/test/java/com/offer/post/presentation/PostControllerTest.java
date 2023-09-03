package com.offer.post.presentation;

import static org.assertj.core.api.Assertions.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.post.application.request.PostCreateRequest;
import com.offer.post.application.response.SortResponse;
import com.offer.post.domain.SortType;
import java.util.List;
import org.assertj.core.api.Assertions;
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

        given(postService.createPost(any(), any()))
            .willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/posts")
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpectAll(
                status().isCreated(),
                header().string("Location", "/api/posts/1")
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

        // when && then
        ResultActions resultActions = mockMvc.perform(
                get("/api/sorts")
                    .queryParam("type", type)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(response)));

        resultActions.andDo(
            document("posts/read-sortItems",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                queryParameters(
                    parameterWithName("type").description("정렬 옵션 그룹 타입")
                ),
                responseFields(
                    fieldWithPath("[].name").type(STRING).description("정렬옵션 이름"),
                    fieldWithPath("[].exposureTitle").type(STRING).description("화면에 표시될 타이틀")
                )
            )
        );
    }
}
