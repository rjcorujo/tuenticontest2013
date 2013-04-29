package org.tuenti.contest.main;


import org.tuenti.contest.input.InputProcessor;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.solver.BitcoinBroker;

import java.math.BigInteger;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        List<TestCase> cases = InputProcessor.processInput();

        for (TestCase input : cases) {
            System.out.println(BitcoinBroker.getInstance().computeProfit(input).longValue());
        }
    }
}
