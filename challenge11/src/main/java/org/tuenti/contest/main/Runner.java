package org.tuenti.contest.main;

import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.HiddenMessageSolver;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {

    public static void main(String[] args) {

        List<List<String>> inputs = InputProcessor.processInput();


        HiddenMessageSolver solver = new HiddenMessageSolver();
        for (List<String> input : inputs) {
            System.out.println(solver.solve(input));
        }
    }
}
