package org.quiz.generator.sources;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class QuizSourcesProvider {

    private final Map<String, QuizSource<InitData>> sourceMap;

    public Stream<QuizSource<InitData>> sources() {
        return sourceMap.values().stream();
    }

    public Optional<QuizSource<InitData>> getSource(String id) {
        return Optional.ofNullable(sourceMap.get(id));
    }

}
