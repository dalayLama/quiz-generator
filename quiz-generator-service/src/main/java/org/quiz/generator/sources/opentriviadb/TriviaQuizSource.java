package org.quiz.generator.sources.opentriviadb;

import lombok.AllArgsConstructor;
import org.quiz.generator.mappers.TriviaQuestionMapper;
import org.quiz.generator.sources.*;
import org.quiz.generator.sources.fields.InitFieldsProvider;
import org.quizstorage.generator.dto.InitField;
import org.quizstorage.generator.dto.Question;
import org.quizstorage.generator.dto.QuestionSet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TriviaQuizSource implements QuizSource<TriviaInitData> {

    private final TriviaClient triviaClient;

    private final InitFieldsProvider triviaInitFieldsProvider;

    private final InitDataNormalizer<TriviaInitData, Map<String, String>> normalizer;

    private final TriviaQuestionMapper questionMapper;

    @Override
    public String id() {
        return "opentdb.com";
    }

    @Override
    public String name() {
        return "Open Trivia Database";
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public List<InitField> initFields() {
        return triviaInitFieldsProvider.getInitFields().toList();
    }

    @Override
    public QuestionSet generate(TriviaInitData initData) {
        List<InitField> initFields = triviaInitFieldsProvider.getInitFields().toList();
        Map<String, String> params = normalizer.normalize(initFields, initData);
        List<Question> questions = triviaClient.questions(params).results().stream()
                .map(questionMapper::toQuestion)
                .toList();
        return new QuestionSet(id(), questions);
    }

    @Override
    public Class<TriviaInitData> initDataClass() {
        return TriviaInitData.class;
    }
}
