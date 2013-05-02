package org.tuenti.contest.main;

import org.tuenti.contest.input.CaveCase;
import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.CaveSolver;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {
    public static void main(String[] args) {

        List<CaveCase> inputs = InputProcessor.processInput();


        CaveSolver solver = new CaveSolver();
        for (CaveCase game : inputs) {
            System.out.println(solver.computeExitTime(game));
        }

    }
}
