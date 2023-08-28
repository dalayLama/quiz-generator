package org.quiz.generator.sources.opentriviadb;

import org.quiz.generator.sources.InitData;

import java.util.HashMap;
import java.util.Map;

public class TriviaInitData extends HashMap<String, String> implements InitData {

    public TriviaInitData(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public TriviaInitData(int initialCapacity) {
        super(initialCapacity);
    }

    public TriviaInitData() {
    }

    public TriviaInitData(Map<? extends String, ? extends String> m) {
        super(m);
    }
}
