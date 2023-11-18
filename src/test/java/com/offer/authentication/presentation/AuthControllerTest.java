package com.offer.authentication.presentation;

import static org.mockito.ArgumentMatchers.any;
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
import com.offer.authentication.application.response.OAuthLoginResponse;
import com.offer.authentication.application.response.OAuthLoginUrlResponse;
import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import java.util.HashMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;


class AuthControllerTest extends DocumentationTest {

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
                content().string(objectMapper.writeValueAsString(ApiResponse.of(ResponseMessage.SUCCESS, loginResponse))));

        resultActions.andDo(
            document("authentication/kakao-login",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                queryParameters(
                    parameterWithName("code").description("인가 코드")
                ),
                responseFields(
                    fieldWithPath("code").type(NUMBER).description("응답코드"),
                    fieldWithPath("message").type(STRING).description("응답 메시지"),

                    fieldWithPath("data.id").type(NUMBER).description("회원 ID"),
                    fieldWithPath("data.nickname").type(STRING).description("회원 닉네임"),
                    fieldWithPath("data.profileImageUrl").type(STRING).description("프로필 이미지 url"),
                    fieldWithPath("data.accessToken").type(STRING).description("엑세스 토큰"),
                    fieldWithPath("data.newMember").type(BOOLEAN).description("신규 가입 유저 여부")
                )
            )
        );
    }

    @DisplayName("멤버 아이디로 토큰 조회(개발용)")
    @Test
    void createToken() throws Exception {
        String token = "jwt.token.here";
        given(jwtTokenProvider.createToken(any()))
            .willReturn(token);
        HashMap<String, String> result = new HashMap<>();
        result.put("token", token);

        ResultActions resultActions = mockMvc.perform(
                get("/api/token/{memberId}", 1L)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().string(objectMapper.writeValueAsString(result)));

        resultActions.andDo(
            document("authentication/get-token",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                responseFields(
                    fieldWithPath("token").type(STRING).description("토큰")
                )
            )
        );
    }

}
