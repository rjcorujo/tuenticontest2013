package org.tuenti.contest.main;

import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.solver.IntegerFinder;

import java.util.List;

/**
 * User: robertcorujo
 */
public class Runner {
    public static void main(String[] args) {

        List<Integer> inputs = InputProcessor.processInput();


        IntegerFinder finder = new IntegerFinder();
        for (Integer nth : inputs) {
            System.out.println(finder.findNthInteger(nth));
        }

    }
}
