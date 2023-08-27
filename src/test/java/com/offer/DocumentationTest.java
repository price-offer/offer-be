package com.offer;


import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.offer.authentication.presentation.AuthController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({
    AuthController.class,
})
@Import(MockMvcConfig.class)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class DocumentationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected OperationResponsePreprocessor getResponsePreprocessor() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    protected OperationRequestPreprocessor getRequestPreprocessor() {
        return Preprocessors.preprocessRequest(prettyPrint());
    }
}
