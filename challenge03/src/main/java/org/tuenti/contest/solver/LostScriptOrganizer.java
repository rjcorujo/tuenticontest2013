package org.tuenti.contest.solver;

import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.input.TestCase.Scene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: robertcorujo
 */
public class LostScriptOrganizer {

    public List<String> organize(List<TestCase> testCases) {

        List<String> scripts = new ArrayList<String>();
        for (TestCase test : testCases) {
            try {
                scripts.add(solve(test));
            } catch (InvalidScriptException e) {
                scripts.add("invalid");
            }
        }
        return scripts;
    }

    private String solve(TestCase test) {
        //clean fuzzy references
        Map<String,Scene> unlinkedScenes = test.getUnlinkedScenes();

        List<String> order = new ArrayList<String>();
        for (Scene scene : test.getOrderedScenes().values()) {
            sc
        }

        for (Scene scene : unlinkedScenes.values()) {
            Scene orderdScene = test.getOrderedScene(scene.getName());

        }

    }

    private boolean isValid(TestCase test) {
        if (test.getUnlinkedScenes().size() == 0) {
            return true;
        }
        for (Scene scene : test.getUnlinkedScenes()) {

        }
    }


    private class InvalidScriptException extends RuntimeException {

    }

}
