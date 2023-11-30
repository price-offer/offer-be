package com.offer;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offer.authentication.JwtTokenProvider;
import com.offer.authentication.application.OAuthService;
import com.offer.authentication.presentation.AuthController;
import com.offer.authentication.presentation.AuthenticationContext;
import com.offer.member.MemberController;
import com.offer.member.MemberRepository;
import com.offer.member.MemberService;
import com.offer.offer.application.OfferService;
import com.offer.offer.presentation.OfferController;
import com.offer.post.application.ImageService;
import com.offer.post.application.PostService;
import com.offer.post.presentation.ImageController;
import com.offer.post.presentation.PostController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@WebMvcTest({
    AuthController.class,
    PostController.class,
    ImageController.class,
    OfferController.class,
    MemberController.class
})
@Import(MockMvcConfig.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class DocumentationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected AuthenticationContext authenticationContext;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;

    @MockBean
    protected OAuthService oAuthService;

    @MockBean
    protected PostService postService;

    @MockBean
    protected ImageService imageService;

    @MockBean
    protected OfferService offerService;

    @MockBean
    protected MemberRepository memberRepository;

    @MockBean
    protected MemberService memberService;

    protected OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    protected OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(prettyPrint());
    }

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .apply(documentationConfiguration(restDocumentation).uris()
                .withScheme("https")
                .withHost("offer-be.kro.kr")
                .withPort(443))
            .build();
    }
}
