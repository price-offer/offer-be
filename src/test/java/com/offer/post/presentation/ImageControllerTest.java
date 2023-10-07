package com.offer.post.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.offer.DocumentationTest;
import com.offer.post.application.response.ImageUploadResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

class ImageControllerTest extends DocumentationTest {

    @DisplayName("이미지를 업로드한다")
    @Test
    void uploadImage() throws Exception {
        // given
        given(imageService.saveImage(any()))
            .willReturn(new ImageUploadResponse("image.jpg"));

        MockMultipartFile image = new MockMultipartFile("image",
            "test.jpg",
            "images/jpg",
            new byte[]{});

        // when
        ResultActions resultActions = mockMvc.perform(
                multipart("/api/upload-image")
                    .file(image)
            )
            .andDo(print())
            .andExpectAll(
                status().isOk()
            );

        resultActions.andDo(
            document("images/upload-image",
                getRequestPreprocessor(),
                getResponsePreprocessor(),
                requestPartBody("image"),
                responseFields(
                    fieldWithPath("imageUrl").type(JsonFieldType.STRING).description("저장된 Image Url")
                )
            )
        );
    }
}
