package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: robertcorujo
 */
public class InputProcessor {

    public static List<TestCase> processInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        List<TestCase> testCases = new ArrayList<TestCase>();
        try {
            String line = bf.readLine();
            int cases = Integer.parseInt(line.trim());
            for (int i = 0; i < cases; i++) {
                line = bf.readLine();
                Integer budget = Integer.parseInt(line.trim());
                line = bf.readLine();
                String ratesStr[] = line.trim().split("\\s+");

                TestCase testCase = new TestCase(budget);
                for (String rateStr : ratesStr) {
                    testCase.addExchangeRate(Integer.parseInt(rateStr));
                }

                testCases.add(testCase);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error processing input");
        }
        return testCases;
    }
}
