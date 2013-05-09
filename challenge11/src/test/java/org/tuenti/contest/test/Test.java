package org.tuenti.contest.test;

import org.tuenti.contest.solver.HiddenMessageSolver;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * User: robertcorujo
 */
public class Test {

    @org.junit.Test
    public void testSimple() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("ppbbpwwwbwwbw",solver.solve(Arrays.asList(
                "pppbwwwwbbbww",
                "pwbwpwwbw"
                )));
    }
    @org.junit.Test
    public void testSimple2() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("ppppbbbbwwwwbbbbw",solver.solve(Arrays.asList(
                "pwwwb",
                "pppppbbbwwwwbbbbwbbbw"
                )));
    }

    @org.junit.Test
    public void testSimple3() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("pbpppwwwbbbbwbbbw",solver.solve(Arrays.asList(
                "pbwww",
                "pppppbbbwwwwbbbbwbbbw"
        )));
    }

    @org.junit.Test
    public void testCopyString() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("pbbbppppppwwbpwwpwwwbppbbwbwbbbbwwwwbbbbwbbww",solver.solve(Arrays.asList(
                "ppppwbbbwwwwbwwwb",
                "pbbbppppppwwbpwwpwwwbppbbwbwbbbbwwwwbbbbwbbww"
        )));
    }

    @org.junit.Test
    public void testIgnore() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("b",solver.solve(Arrays.asList(
                "pbwwpwwwb",
                "ppbbpwwwbbbbw"
        )));
    }

    @org.junit.Test
    public void testCompact() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("b",solver.solve(Arrays.asList(
                "pwwwb",
                "pbbbw"
        )));
    }

    @org.junit.Test
    public void testCompactNested() throws Exception {

        HiddenMessageSolver solver = new HiddenMessageSolver();



        assertEquals("b",solver.solve(Arrays.asList(
                "pppppwwbbwwbbwwbbwwbb",
                "pppppbbwwbbwwbbwwbbww"
        )));
    }
}
