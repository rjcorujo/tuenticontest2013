package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * User: robertcorujo
 */
public class InputProcessor {

    public static List<List<String>> processInput() {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            int numTests =  Integer.parseInt(bf.readLine().trim());
            List<List<String>> inputs = new ArrayList<List<String>>();
            for (int i = 0; i < numTests; i++) {
                List<String> list = Arrays.asList(bf.readLine().trim().split("\\s+"));
                inputs.add(list);
            }
            return inputs;
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
