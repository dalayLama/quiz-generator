package org.quiz.generator.sources.quizapi;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.quizstorage.generator.dto.Difficulty;
import org.quiz.generator.sources.TestFilesUtil;
import org.quiz.generator.sources.quizapi.dto.QuizApiQuestion;
import org.quiz.generator.sources.quizapi.dto.QuizApiTag;
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
        "quiz-sources.quiz-api.url=http://127.0.0.1:${wiremock.server.port}",
        "quiz-sources.quiz-api.enable=true"
})
@AutoConfigureWireMock(port = 0)
class QuizApiClientIntegrationTest {

    private static final QuizApiQuestion QUIZ_API_QUESTION = QuizApiQuestion.builder()
            .id(1069L)
            .question("What will happen If you run the command \"init 0\" in your terminal")
            .answers(Map.of(
                    "answer_a", "Shut down the system",
                    "answer_b", "Reboot the system"
            ))
            .multiplyCorrectAnswers(false)
            .correctAnswers(Map.of(
                    "answer_a_correct", true,
                    "answer_b_correct", false
            ))
            .tags(Set.of(new QuizApiTag("Linux")))
            .category("Linux")
            .difficulty(Difficulty.MEDIUM)
            .build();

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private QuizApiClient quizApiClient;

    @Test
    void shouldReturnQuestions() {
        final String responseBody = TestFilesUtil.readResourceFile("responses/quiz-api-questions.json");
        Map<String, String> params = Map.of(
                "p1", "v1",
                "p2", "v2");
        String requestParams = params.entrySet().stream()
                .map(entry -> "%s=%s".formatted(entry.getKey(), entry.getValue()))
                .collect(Collectors.joining("&"));
        String url = "/api/v1/questions?%s".formatted(requestParams);
        mockServer.stubFor(WireMock.get(WireMock.urlEqualTo(url))
                .willReturn(
                        WireMock.aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBody(responseBody)
                )
        );

        List<QuizApiQuestion> result = quizApiClient.questions(params);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .usingRecursiveComparison()
                .isEqualTo(List.of(QUIZ_API_QUESTION));

    }

}