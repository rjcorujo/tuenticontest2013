package org.tuenti.contest.main;

import org.tuenti.contest.input.Boozzle;
import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.BoozzleSolver;
import org.tuenti.contest.solver.Dictionary;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary("boozzle-dict.txt");

        List<Boozzle> inputs = InputProcessor.processInput();


        BoozzleSolver solver = new BoozzleSolver(dictionary);
        for (Boozzle game : inputs) {
            System.out.println(solver.resolve(game));
        }

        dictionary.close();

    }
}
