package org.tuenti.contest.main;

import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.input.WarScenario;
import org.tuenti.contest.solver.WarSolver;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {

    public static void main(String[] args) {
        List<WarScenario> inputs = InputProcessor.processInput();


        WarSolver solver = new WarSolver();
        for (WarScenario war : inputs) {
            System.out.println(solver.fight(war));
        }
    }
}
