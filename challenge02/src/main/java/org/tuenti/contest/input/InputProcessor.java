package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: robertcorujo
 */
public class InputProcessor {
    public static TestCase processInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {

            String dictionaryName = readLineIgnoringComments(bf).trim();
            String numWordsStr = readLineIgnoringComments(bf).trim();
            int numWords = Integer.parseInt(numWordsStr);

            TestCase input = new TestCase(dictionaryName);
            for (int i = 0; i < numWords; i++) {
                String word = readLineIgnoringComments(bf).trim();
                input.addWord(word);
            }
            return input;
        } catch (Exception e) {
            throw new RuntimeException("Error processing input");
        }
    }

    private static String readLineIgnoringComments(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line.trim().startsWith("#")) {
            line = reader.readLine();
        }
        return line;
    }
}
