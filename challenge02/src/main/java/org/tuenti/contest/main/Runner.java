package org.tuenti.contest.main;

import org.apache.commons.lang3.StringUtils;
import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.solver.WordSuggester;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {
    public static void main(String[] args) {
        TestCase input = InputProcessor.processInput();

        WordSuggester suggester = WordSuggester.getInstance(input.getDictionaryName());

        LinkedHashMap<String,List<String>> suggestions = suggester.getSuggestions(input);

        for (String word : suggestions.keySet()) {
            System.out.print(word + " -> ");
            List<String> candidates = suggestions.get(word);
            if (candidates.size() > 0) {
                System.out.println(StringUtils.join(candidates," "));
            } else {
                System.out.println("");
            }
        }
    }

}
