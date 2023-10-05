package org.quiz.generator.sources.opentriviadb.fields;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.quiz.generator.sources.TestFilesUtil;
import org.quizstorage.generator.dto.SelectOption;
import org.quiz.generator.sources.opentriviadb.providers.select.QuestionCategoriesProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "quiz-sources.trivia.url=http://127.0.0.1:${wiremock.server.port}"
})
@AutoConfigureWireMock(port = 0)
class QuestionCategoriesProviderIntegrationTest {

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private QuestionCategoriesProvider categoriesProvider;

    @Test
    void test() {
        final String responseBody = TestFilesUtil.readResourceFile("responses/trivia-categories.json");
        String url = "/api_category.php";
        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(
                        WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(responseBody)
                )
        );

        List<SelectOption> selectOptions = categoriesProvider.get();
        assertThat(selectOptions)
                .isNotNull()
                .isNotEmpty();
    }

}