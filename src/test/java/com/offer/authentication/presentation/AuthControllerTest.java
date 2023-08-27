package com.offer.authentication.presentation;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.authentication.application.OAuthService;
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.application.response.OAuthLoginUrlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;


class AuthControllerTest extends DocumentationTest {

    @MockBean
    private OAuthService oAuthService;


    @DisplayName("카카오 로그인 페이지 url 조회")
    @Test
    void getKakaoLoginUrl() throws Exception {
        // given
        OAuthLoginUrlResponse urlResponse = new OAuthLoginUrlResponse("https://kakao.login.url");
        given(oAuthService.getKakaoLoginUrl())
            .willReturn(urlResponse);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                get("/api/authorization/kakao-url")
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(urlResponse))
            );

        resultActions.andDo(
            document("authentication/kakao-login-url",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                    fieldWithPath("url").type(STRING).description("카카오 로그인 페이지 url")
                )
            )
        );
    }

    @DisplayName("카카오 로그인")
    @Test
    void kakaoLogin() throws Exception {
        // given
        String authCode = "kakao.token.authCode";
        OAuthLoginResponse loginResponse = OAuthLoginResponse.builder()
            .id(1L)
            .nickname("행복한 냉장고 1호")
            .profileImageUrl("profile.image.url")
            .accessToken("jwt.token.here")
            .newMember(true)
            .build();
        given(oAuthService.kakaoLogin(authCode))
            .willReturn(loginResponse);

        // when && then
        ResultActions resultActions = mockMvc.perform(
                get("/api/login/kakao")
                    .queryParam("code", authCode)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(loginResponse)));

        resultActions.andDo(
            document("authentication/kakao-login",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                queryParameters(
                    parameterWithName("code").description("인가 코드")
                ),
                responseFields(
                    fieldWithPath("id").type(NUMBER).description("회원 ID"),
                    fieldWithPath("nickname").type(STRING).description("회원 닉네임"),
                    fieldWithPath("profileImageUrl").type(STRING).description("프로필 이미지 url"),
                    fieldWithPath("accessToken").type(STRING).description("엑세스 토큰"),
                    fieldWithPath("newMember").type(BOOLEAN).description("신규 가입 유저 여부")
                )
            )
        );
    }

}