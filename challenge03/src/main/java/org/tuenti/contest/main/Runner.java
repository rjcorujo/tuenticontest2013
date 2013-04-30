package org.tuenti.contest.main;

import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.solver.LostScriptOrganizer;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {
    public static void main(String[] args) {
        List<TestCase> inputs = InputProcessor.processInput();

        LostScriptOrganizer organizer = new LostScriptOrganizer();

        List<String> outputs =  organizer.organize(inputs);
        for (String output : outputs) {
            System.out.println(output);
        }
    }
}
