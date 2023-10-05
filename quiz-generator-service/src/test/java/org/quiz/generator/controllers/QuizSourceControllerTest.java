package org.quiz.generator.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.quiz.generator.sources.InitData;
import org.quiz.generator.sources.QuizSource;
import org.quiz.generator.sources.QuizSourcesProvider;
import org.quiz.generator.sources.TestFilesUtil;
import org.quizstorage.generator.dto.NumberFormat;
import org.quizstorage.generator.dto.SelectOption;
import org.quizstorage.generator.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.only;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {QuizSourceController.class})
class QuizSourceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizSourcesProvider quizSourcesProvider;

    @Test
    void shouldReturnSources() throws Exception {
        willReturn(Stream.of(quizSource)).given(quizSourcesProvider).sources();

        String response = TestFilesUtil.readResourceFile("responses/quiz-sources.json");

        mockMvc.perform(get("/api/v1/quiz/sources"))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());

        then(quizSourcesProvider).should(only()).sources();
    }

    @Spy
    private SimpleQuizSource quizSource = new SimpleQuizSource();

    @Test
    void shouldReturnInitData() throws Exception {
        String sourceId = "simple-quiz-source";
        willReturn(Optional.of(quizSource)).given(quizSourcesProvider).getSource(sourceId);

        String response = TestFilesUtil.readResourceFile("responses/quiz-source-init-data.json");

        mockMvc.perform(get("/api/v1/quiz/sources/{id}/init-data-definition", sourceId))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());

        then(quizSourcesProvider).should(only()).getSource(sourceId);
    }

    @Test
    void shouldReturnQuestions() throws Exception {
        String sourceId = "simple-quiz-source";

        willReturn(Optional.of(quizSource)).given(quizSourcesProvider).getSource(sourceId);

        String request = TestFilesUtil.readResourceFile("requests/quiz-source-questions-request.json");
        String response = TestFilesUtil.readResourceFile("responses/quiz-source-questions.json");
        SimpleInitData expectedInitData = new SimpleInitData("value");

        mockMvc.perform(post("/api/v1/quiz/sources/{id}/questions", sourceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(content().json(response))
                .andDo(print());

        then(quizSourcesProvider).should(only()).getSource(sourceId);
        then(quizSource).should().generate(expectedInitData);
    }

    @Test
    void shouldReturn404WhenAskingNotExistedSourceForInitData() throws Exception {
        String sourceId = "not-existed-source-id";
        willReturn(Optional.empty()).given(quizSourcesProvider).getSource(sourceId);

        mockMvc.perform(get("/api/v1/quiz/sources/{id}/init-data-definition", sourceId))
                .andExpect(status().isNotFound())
                .andDo(print());

        then(quizSourcesProvider).should(only()).getSource(sourceId);
    }

    @Test
    void shouldReturn404WhenAskingNotExistedSourceForQuestions() throws Exception {
        String sourceId = "not-existed-source-id";
        willReturn(Optional.empty()).given(quizSourcesProvider).getSource(sourceId);
        String request = TestFilesUtil.readResourceFile("requests/quiz-source-questions-request.json");

        mockMvc.perform(post("/api/v1/quiz/sources/{id}/questions", sourceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isNotFound())
                .andDo(print());

        then(quizSourcesProvider).should(only()).getSource(sourceId);
        then(quizSource).should(never()).generate(any());
    }

    @Test
    void shouldReturn400WhenNotPresentInitDataForSource() throws Exception {
        String sourceId = "simple-quiz-source";

        mockMvc.perform(post("/api/v1/quiz/sources/{id}/questions", sourceId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        then(quizSourcesProvider).should(never()).getSource(sourceId);
        then(quizSource).should(never()).generate(any());
    }

    @Test
    void shouldReturn400WhenPresentNotValidInitDataForSource() throws Exception {
        String sourceId = "simple-quiz-source";

        willReturn(Optional.of(quizSource)).given(quizSourcesProvider).getSource(sourceId);

        String request = TestFilesUtil.readResourceFile("requests/not-valid-quiz-source-questions-request.json");

        mockMvc.perform(post("/api/v1/quiz/sources/{id}/questions", sourceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest())
                .andDo(print());

        then(quizSourcesProvider).should(only()).getSource(sourceId);
        then(quizSource).should(never()).generate(any());
    }

    private record SimpleInitData(
            @JsonProperty(required = true)
            String someValue
    ) implements InitData {

    }

    private static class SimpleQuizSource implements QuizSource<SimpleInitData> {

        @Override
        public String id() {
            return "simple-quiz-source";
        }

        @Override
        public String name() {
            return "Simple Quiz Source";
        }

        @Override
        public String description() {
            return "Simple Quiz Source Description";
        }

        @Override
        public List<InitField<?>> initFields() {
            InitField<NumberFormat> numberInitField = FieldType.NUMBER.createInitField(
                    "f1", "description", false, new NumberFormat(5, 20));
            SelectFormat selectFormat = new SelectFormat(false, List.of(
                    new SelectOption("o1", "d1")
            ));
            InitField<SelectFormat> selectInitField = FieldType.SELECT
                    .createInitField("f2", "description", true, selectFormat);
            return List.of(numberInitField, selectInitField);
        }

        @Override
        public QuestionSet generate(SimpleInitData initData) {
            Question question = Question.builder()
                    .question("question")
                    .answers(Set.of("answer1", "answer2"))
                    .correctAnswers(Set.of("answer1"))
                    .category("category")
                    .multiplyAnswers(true)
                    .difficulty(Difficulty.EASY)
                    .build();
            return new QuestionSet(id(), List.of(question));
        }

        @Override
        public Class<SimpleInitData> initDataClass() {
            return SimpleInitData.class;
        }

    }

}