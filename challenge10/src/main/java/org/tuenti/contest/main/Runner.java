package org.tuenti.contest.main;

import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.CheckingSolver;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {

    public static void main(String[] args) {

        List<String> inputs = InputProcessor.processInput();

        CheckingSolver solver = new CheckingSolver();
        for (String code : inputs) {
            System.out.println(solver.decode(code));
        }
    }
}
