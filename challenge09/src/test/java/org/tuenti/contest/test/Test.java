package org.tuenti.contest.test;

import org.tuenti.contest.input.WarScenario;
import org.tuenti.contest.solver.WarSolver;

import static org.junit.Assert.assertEquals;

/**
 * User: robertcorujo
 */
public class Test {

    @org.junit.Test
    public void testSimpleForever() throws Exception {
        WarSolver solver = new WarSolver();

        assertEquals(-1, solver.fight(new WarScenario(10,20,50,100,500)));
    }

    @org.junit.Test
    public void testSimple() throws Exception {
        WarSolver solver = new WarSolver();

        assertEquals(100, solver.fight(new WarScenario(10,20,50,100,400)));
    }
}
