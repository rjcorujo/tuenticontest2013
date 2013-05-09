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

    public static List<String> processInput() {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            String line = bf.readLine();
            List<String> inputs = new ArrayList<String>();
            while (line != null) {
                inputs.add(line.trim());
                line = bf.readLine();
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
