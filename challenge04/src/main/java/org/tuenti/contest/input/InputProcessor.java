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

    public static List<Integer> processInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {
            int numTests = Integer.parseInt(bf.readLine().trim());
            List<Integer> cases = new ArrayList<Integer>();
            for (int i = 0; i < numTests; i++) {
                cases.add(Integer.parseInt(bf.readLine().trim()));
            }
            return cases;
        } catch (Exception e) {
            throw new RuntimeException("Error processing input",e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing input",e);
                }
            }
        }
    }
}
