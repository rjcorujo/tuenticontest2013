package org.tuenti.contest.input;

import java.util.ArrayList;
import java.util.List;

public class TestCase {

    private String dictionaryName;
    private List<String> words;

    public TestCase(String dictionaryName) {
        this.dictionaryName = dictionaryName;
        words = new ArrayList<String>();
    }

    public void addWord(String word) {
        words.add(word);
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public List<String> getWords() {
        return words;
    }
}
