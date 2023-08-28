package org.quiz.generator.sources.quizapi;

import org.quiz.generator.sources.InitData;

import java.util.HashMap;
import java.util.Map;

public class QuizApiInitData extends HashMap<String, String> implements InitData {

    public QuizApiInitData(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public QuizApiInitData(int initialCapacity) {
        super(initialCapacity);
    }

    public QuizApiInitData() {
    }

    public QuizApiInitData(Map<? extends String, ? extends String> m) {
        super(m);
    }
}
