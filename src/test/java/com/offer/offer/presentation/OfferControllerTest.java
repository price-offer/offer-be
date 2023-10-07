package com.offer.offer.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OfferResponse;
import com.offer.offer.application.response.OffererResponse;
import com.offer.offer.application.response.OffersResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class OfferControllerTest extends DocumentationTest {

    @DisplayName("게시글에 대한 가격제안 목록 조회")
    @Test
    void getOffersByPost() throws Exception {

        OffererResponse offererResponse1 = OffererResponse.builder()
            .id(1L)
            .nickname("심심한 냉장고 1호")
            .level("1")
            .location("동작구 사당동")
            .tradeType("직거래")
            .profileImageUrl("http://profileImage.jpg")
            .build();

        OffererResponse offererResponse2 = OffererResponse.builder()
            .id(2L)
            .nickname("행복한 청바지 1호")
            .level("1")
            .location("동작구 사당동")
            .tradeType("직거래")
            .profileImageUrl("http://profileImage.jpg")
            .build();

        OfferResponse offerResponse1 = OfferResponse.builder()
            .id(1L)
            .offerer(offererResponse1)
            .price(10000)
            .createdAt(LocalDateTime.now())
            .build();

        OfferResponse offerResponse2 = OfferResponse.builder()
            .id(2L)
            .offerer(offererResponse2)
            .price(12000)
            .createdAt(LocalDateTime.now())
            .build();

        OffersResponse response = OffersResponse.builder()
            .totalSize(2)
            .postId(1L)
            .offers(List.of(offerResponse1, offerResponse2))
            .offerCountOfCurrentMember(0)
            .build();

        given(offerService.getOffersByPost(any(), any()))
            .willReturn(response);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                get("/api/posts/{postId}/offers", 1L)
                    .header("Authorization", "Bearer jwt.token.here")
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(response)));

        resultActions.andDo(
            document("offers/get-offers-by-post",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                    fieldWithPath("totalSize").type(NUMBER).description("전체 가격제안 개수"),
                    fieldWithPath("postId").type(NUMBER).description("게시글 아이디"),
                    fieldWithPath("offerCountOfCurrentMember").type(NUMBER).description("게시글에 대한 로그인 유저의 가격제안 횟수(비로그인 유저의 경우 0)"),
                    fieldWithPath("maximumOfferCount").type(NUMBER).description("최대 가격제안 가능 횟수"),
                    fieldWithPath("offers[].id").type(NUMBER).description("가격제안 아이디"),
                    fieldWithPath("offers[].price").type(NUMBER).description("제안 가격"),
                    fieldWithPath("offers[].offerer.id").type(NUMBER).description("가격제안자 아이디"),
                    fieldWithPath("offers[].offerer.nickname").type(STRING).description("가격제안자 아이디"),
                    fieldWithPath("offers[].offerer.location").type(STRING).description("가격제안자 거래위치"),
                    fieldWithPath("offers[].offerer.tradeType").type(STRING).description("가격제안자 거래방식"),
                    fieldWithPath("offers[].offerer.level").type(STRING).description("가격제안자 오퍼레벨"),
                    fieldWithPath("offers[].offerer.profileImageUrl").type(STRING).description("가격제안자 프로필이미지"),
                    fieldWithPath("offers[].createdAt").type(STRING).description("가격제안 생성 시간")
                )
            )
        );
    }

    @DisplayName("가격제안 생성")
    @Test
    void createOffer() throws Exception {
        // given
        OfferCreateRequest request = OfferCreateRequest.builder()
            .price(10000)
            .location("동작구 사당동")
            .tradeType("직거래")
            .build();

        given(offerService.createOffer(any(), any(), any()))
            .willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/posts/{postId}/offers", 1L)
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
            document("offers/create-offer",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION).description("token")
                ),
                requestFields(
                    fieldWithPath("price").type(NUMBER).description("제안 가격"),
                    fieldWithPath("location").type(STRING).description("거래 지역"),
                    fieldWithPath("tradeType").type(STRING).description("거래 방식")
                )
            )
        );
    }
}
