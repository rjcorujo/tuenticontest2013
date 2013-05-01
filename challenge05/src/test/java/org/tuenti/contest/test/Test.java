package org.tuenti.contest.test;

import org.junit.Ignore;
import org.tuenti.contest.input.GameCase;
import org.tuenti.contest.solver.GameSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.tuenti.contest.input.GameCase.Cell;
import static org.tuenti.contest.input.GameCase.GemCell;

/**
 * User: robertcorujo
 */
public class Test {

    private GameSolver solver;

    public Test() {
        solver = new GameSolver();
    }

    @org.junit.Test
    public void testNoTime() throws Exception {
        GameCase game = new GameCase(4,4, new Cell(2,2),
                Arrays.asList(new GemCell(1,0,1), new GemCell(1,1,1)),1);


        assertEquals(0,solver.resolve(game));
    }

    @org.junit.Test
    public void testSpareTime() throws Exception {
        GameCase game = new GameCase(4,4, new Cell(2,2),
                Arrays.asList(new GemCell(1,0,1), new GemCell(1,1,1)),20);


        assertEquals(2,solver.resolve(game));
    }

    @org.junit.Test
    public void testExample1() throws Exception {
        GameCase game = new GameCase(5,5, new Cell(2,2),
                Arrays.asList(new GemCell(0,0,2),
                        new GemCell(1,0,2),
                        new GemCell(2,0,2),
                        new GemCell(1,1,2),
                        new GemCell(1,2,2),
                        new GemCell(3,2,5),
                        new GemCell(3,3,5)
                        )
                ,6);


        assertEquals(14,solver.resolve(game));
    }
    @org.junit.Test
    public void testExample2CanNotGoBack() throws Exception {
        GameCase game = new GameCase(4,4, new Cell(2,2),
                Arrays.asList(new GemCell(1,0,5),
                        new GemCell(1,1,1),
                        new GemCell(2,1,2),
                        new GemCell(1,2,1),
                        new GemCell(3,2,2),
                        new GemCell(2,3,5),
                        new GemCell(3,3,2)
                        )
                ,5);


        assertEquals(12,solver.resolve(game));
    }

    @org.junit.Test
    public void testBig() throws Exception {
        LinkedList<GemCell> gemCells = new LinkedList<GemCell>();
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                gemCells.add(new GemCell(i,j,1));
            }
        }

        gemCells.removeFirst();

        GameCase game = new GameCase(100,100, new Cell(0,0),
                gemCells
                ,20);


        assertEquals(20,solver.resolve(game));
    }
}
