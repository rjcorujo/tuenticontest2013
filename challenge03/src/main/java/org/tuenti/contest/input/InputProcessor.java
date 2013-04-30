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
    public static List<TestCase> processInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {
            int numScripts = Integer.parseInt(bf.readLine().trim());
            List<TestCase> cases = new ArrayList<TestCase>();
            for (int i = 0; i < numScripts; i++) {
                TestCase input = buildScriptTestCase(bf.readLine().trim());
                cases.add(input);
            }
            return cases;
        } catch (Exception e) {
            throw new RuntimeException("Error processing input");
        }
    }

    private static TestCase buildScriptTestCase(String scripts) {
        String rest = scripts;
        TestCase testCase = new TestCase();
        while(rest.length() > 0) {
            int nextSpot = findNextSpot(rest.substring(1)) + 1; //ignoring current
            if (nextSpot == 0) {
                nextSpot = rest.length();
            }

            String sceneStr = rest.substring(1,nextSpot);
            switch (rest.charAt(0)) {
                case '.':
                    testCase.addOrderedScene(sceneStr);
                    break;
                case '<':
                    testCase.addFlashbackScene(sceneStr);
                    break;
                case '>':
                    testCase.addFlashforwardScene(sceneStr);
                    break;
                default:
                    throw new RuntimeException("Unexpected character processing scripts");
            }

             if (nextSpot == rest.length()) {
                 rest = "";
             } else {
                 rest = rest.substring(nextSpot);
             }
        }

        return testCase;
    }

    private static int findNextSpot(String str) {
        int dotPos = str.indexOf(".");
        int backPos = str.indexOf("<");
        int forwardPos = str.indexOf(">");



        int min = Math.min(Math.min(dotPos < 0 ? Integer.MAX_VALUE : dotPos,
                                            backPos < 0 ? Integer.MAX_VALUE : backPos),
                                forwardPos < 0 ? Integer.MAX_VALUE : forwardPos);
        if (min == Integer.MAX_VALUE) { //not found
            return -1;
        }

        return min;
    }

}
