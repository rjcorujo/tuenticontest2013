package org.tuenti.contest.tests;

import junit.framework.Assert;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.solver.WordSuggester;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * User: robertcorujo
 */
public class Test {

    @org.junit.Test
    public void testSimple() throws Exception {
        buildDictionary("gainly","laying","protectional","lactoprotein","elvis","lives","velis","nosuggestion");
        TestCase input = new TestCase("test_dictionary");
        input.addWord("elvis");
        input.addWord("lactoprotein");
        input.addWord("nosuggestion");

        WordSuggester suggester = WordSuggester.getInstance(input.getDictionaryName());
        LinkedHashMap<String,List<String>> results = suggester.getSuggestions(input);

        assertEquals(2, results.get("elvis").size());
        assertEquals(Arrays.asList("lives","velis"),results.get("elvis"));
    }

    private void buildDictionary(String ... words) throws Exception{
        File file = new File("test_dictionary");
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        for (String word: words) {
            writer.write(word);
            writer.newLine();
        }
        writer.close();

    }
}
