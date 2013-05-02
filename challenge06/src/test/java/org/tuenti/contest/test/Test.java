package org.tuenti.contest.test;

import org.tuenti.contest.input.CaveCase;
import org.tuenti.contest.solver.CaveSolver;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.tuenti.contest.input.CaveCase.CaveMap;
import static org.tuenti.contest.input.CaveCase.Location;
import static org.tuenti.contest.input.CaveCase.Obstacle;

/**
 * User: robertcorujo
 */
public class Test {



    @org.junit.Test
    public void testExample1() throws Exception {

        CaveSolver solver = new CaveSolver();

        assertEquals(10,solver.computeExitTime(basicCaveOnlyWalls()));

    }
    @org.junit.Test
    public void testExample2() throws Exception {

        CaveSolver solver = new CaveSolver();

        assertEquals(19,solver.computeExitTime(basicCaveWalls()));

    }


    // inputs
    private CaveCase basicCaveOnlyWalls() {
        CaveMap map = new CaveMap(new Location(1,1),
                    new Location(3,3),
                Arrays.asList(new Obstacle(0,0), new Obstacle(0,1), new Obstacle(0,2), new Obstacle(0,3),
                        new Obstacle(1,0), new Obstacle(1,3),
                        new Obstacle(2,0), new Obstacle(2,3),
                        new Obstacle(3,0),
                        new Obstacle(4,0), new Obstacle(4,1), new Obstacle(4,2), new Obstacle(4,3)));

        return new CaveCase(4,5,1,3,map);
    }

    private CaveCase basicCaveWalls() {
        CaveMap map = new CaveMap(new Location(1,1),
                    new Location(6,5),
                Arrays.asList(new Obstacle(0,0), new Obstacle(0,1), new Obstacle(0,2), new Obstacle(0,3), new Obstacle(0,4), new Obstacle(0,5),
                        new Obstacle(1,0), new Obstacle(1,2), new Obstacle(1,5),
                        new Obstacle(2,0), new Obstacle(2,5),
                        new Obstacle(3,0), new Obstacle(3,5),
                        new Obstacle(4,0), new Obstacle(4,1), new Obstacle(4,5),
                        new Obstacle(5,0), new Obstacle(5,3), new Obstacle(5,4),new Obstacle(5,5),
                        new Obstacle(6,0),
                        new Obstacle(7,0), new Obstacle(7,1), new Obstacle(7,2), new Obstacle(7,3), new Obstacle(7,4), new Obstacle(7,5)
                ));

        return new CaveCase(6,8,1,1,map);
    }



}
