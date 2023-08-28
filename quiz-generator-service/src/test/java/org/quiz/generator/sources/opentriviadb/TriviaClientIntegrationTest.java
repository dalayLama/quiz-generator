package org.quiz.generator.sources.opentriviadb;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.quizstorage.generator.dto.Difficulty;
import org.quiz.generator.sources.TestFilesUtil;
import org.quiz.generator.sources.opentriviadb.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "quiz-sources.trivia.url=http://127.0.0.1:${wiremock.server.port}"
})
@AutoConfigureWireMock(port = 0)
class TriviaClientIntegrationTest {

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private TriviaClient triviaClient;

    @Test
    void shouldReturnCategories() {
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

        TriviaQuestionCategoryResponse expectedResult = new TriviaQuestionCategoryResponse(List.of(
                new TriviaQuestionCategory(9, "General Knowledge"))
        );


        TriviaQuestionCategoryResponse response = triviaClient.cagetories();
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnQuestions() {
        final String responseBody = TestFilesUtil.readResourceFile("responses/trivia-questions-response.json");
        Map<String, String> params = Map.of(
                "p1", "v1"
        );
        String requestParams = params.entrySet().stream()
                .map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&"));
        String url = "/api.php?%s".formatted(requestParams);
        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(
                        WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(responseBody)
                )
        );

        TriviaQuestion question = TriviaQuestion.builder()
                .category("Entertainment: Books")
                .type(TriviaQuestionType.MULTIPLE)
                .difficulty(Difficulty.HARD)
                .question("Who wrote the novel &quot;Moby-Dick&quot;?")
                .correctAnswer("Herman Melville")
                .incorrectAnswers(Set.of("William Golding"))
                .build();
        TriviaQuestionResponse expectedResult = new TriviaQuestionResponse(0, List.of(question));

        TriviaQuestionResponse response = triviaClient.questions(params);
        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}