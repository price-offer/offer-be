package com.offer.offer.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OfferResponse;
import com.offer.offer.application.response.OffererResponse;
import com.offer.offer.application.response.OffersResponse;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

@Disabled
class OfferControllerTest {

//    @DisplayName("게시글에 대한 가격제안 목록 조회")
//    @Test
//    void getOffersByPost() throws Exception {
//
//        OffererResponse offererResponse1 = OffererResponse.builder()
//                .id(1L)
//                .nickname("심심한 냉장고 1호")
//                .level("1")
//                .location("동작구 사당동")
//                .tradeType("직거래")
//                .profileImageUrl("http://profileImage.jpg")
//                .build();
//
//        OffererResponse offererResponse2 = OffererResponse.builder()
//                .id(2L)
//                .nickname("행복한 청바지 1호")
//                .level("1")
//                .location("동작구 사당동")
//                .tradeType("직거래")
//                .profileImageUrl("http://profileImage.jpg")
//                .build();
//
//        OfferResponse offerResponse1 = OfferResponse.builder()
//                .id(1L)
//                .offerer(offererResponse1)
//                .price(10000)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        OfferResponse offerResponse2 = OfferResponse.builder()
//                .id(2L)
//                .offerer(offererResponse2)
//                .price(12000)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        OffersResponse response = OffersResponse.builder()
//                .totalSize(2)
//                .postId(1L)
//                .offers(List.of(offerResponse1, offerResponse2))
//                .offerCountOfCurrentMember(0)
//                .build();
//
//        ApiResponse<OffersResponse> httpResponse = ApiResponse.of(ResponseMessage.SUCCESS, response);
//
//        given(offerService.getAllOffersByPost(anyInt(), any()))
//                .willReturn(response);
//
//        // when && then
//        ResultActions resultActions = mockMvc.perform(
//                        get("/api/posts/{postId}/offers", 1L)
//                                .param("page", "1")
//                                .header("Authorization", "Bearer jwt.token.here")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(print())
//                .andExpectAll(
//                        status().isOk(),
//                        content().string(objectMapper.writeValueAsString(httpResponse)));
//
//        resultActions.andDo(
//                document("offers/get-offers-by-post",
//                        getRequestPreprocessor(),
//                        getResponsePreprocessor(),
//                        responseFields(
//                                // common filed of response
//                                fieldWithPath("code").type(NUMBER).description("응답코드"),
//                                fieldWithPath("message").type(STRING).description("응답 메시지"),
//                                // body of response
//                                fieldWithPath("data.totalSize").type(NUMBER).description("전체 가격제안 개수"),
//                                fieldWithPath("data.postId").type(NUMBER).description("게시글 아이디"),
//                                fieldWithPath("data.offerCountOfCurrentMember").type(NUMBER).description("게시글에 대한 로그인 유저의 가격제안 횟수(비로그인 유저의 경우 0)"),
//                                fieldWithPath("data.maximumOfferCount").type(NUMBER).description("최대 가격제안 가능 횟수"),
//                                fieldWithPath("data.offers[].id").type(NUMBER).description("가격제안 아이디"),
//                                fieldWithPath("data.offers[].price").type(NUMBER).description("제안 가격"),
//                                fieldWithPath("data.offers[].offerer.id").type(NUMBER).description("가격제안자 아이디"),
//                                fieldWithPath("data.offers[].offerer.nickname").type(STRING).description("가격제안자 아이디"),
//                                fieldWithPath("data.offers[].offerer.location").type(STRING).description("가격제안자 거래위치"),
//                                fieldWithPath("data.offers[].offerer.tradeType").type(STRING).description("가격제안자 거래방식"),
//                                fieldWithPath("data.offers[].offerer.level").type(STRING).description("가격제안자 오퍼레벨"),
//                                fieldWithPath("data.offers[].offerer.profileImageUrl").type(STRING).description("가격제안자 프로필이미지"),
//                                fieldWithPath("data.offers[].createdAt").type(STRING).description("가격제안 생성 시간")
//                        )
//                )
//        );
//    }
//
//    @DisplayName("가격제안 생성")
//    @Test
//    void createOffer() throws Exception {
//        // given
//        OfferCreateRequest request = OfferCreateRequest.builder()
//                .price(10000)
//                .location("동작구 사당동")
//                .tradeType("직거래")
//                .build();
//
//        CommonCreationResponse commonCreationResponse = CommonCreationResponse.of(1L, LocalDateTime.now());
//
//        given(offerService.createOffer(any(), any(), any()))
//                .willReturn(commonCreationResponse);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(
//                        post("/api/posts/{postId}/offers", 1L)
//                                .header("Authorization", "Bearer jwt.token.here")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(request))
//                )
//                .andDo(print())
//                .andExpectAll(
//                        status().isOk()
//                );
//
//        // then
//        resultActions.andDo(
//                document("offers/create-offer",
//                        getRequestPreprocessor(),
//                        getResponsePreprocessor(),
//                        requestHeaders(
//                                headerWithName(HttpHeaders.AUTHORIZATION).description("token")
//                        ),
//                        requestFields(
//                                fieldWithPath("price").type(NUMBER).description("제안 가격"),
//                                fieldWithPath("location").type(STRING).description("거래 지역"),
//                                fieldWithPath("tradeType").type(STRING).description("거래 방식")
//                        )
//                )
//        );
//    }
}
