package org.tuenti.contest.main;

import org.tuenti.contest.input.GameCase;
import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.GameSolver;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {
    public static void main(String[] args) {

        List<GameCase> inputs = InputProcessor.processInput();


        GameSolver solver = new GameSolver();
        for (GameCase game : inputs) {
            System.out.println(solver.resolve(game));
        }

    }
}
