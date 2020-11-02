package hirooka.dev.cdcanddocs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class HelloControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(
            WebApplicationContext context,
            RestDocumentationContextProvider provider
    ) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
    }

    @Test
    void HelloControllerのOKテスト() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": 10}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("OK"))
                .andDo(
                        WireMockRestDocs.verify()
                                .contentType(MediaType.APPLICATION_JSON)
                                .jsonPath("$[?(@.value >= 10)]"))
                .andDo(document("okPattern", SpringCloudContractRestDocs.dslContract()));
    }


    @Test
    void HelloControllerのNGテスト() throws Exception {
        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"value\": 9}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("NG"))
                .andDo(
                        WireMockRestDocs.verify()
                                .contentType(MediaType.APPLICATION_JSON)
                                .jsonPath("$[?(@.value < 10)]"))
                .andDo(document("ngPattern", SpringCloudContractRestDocs.dslContract()));
    }
}