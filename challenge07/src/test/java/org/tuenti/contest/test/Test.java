package org.tuenti.contest.test;

import org.tuenti.contest.input.Boozzle;
import org.tuenti.contest.solver.BoozzleSolver;
import org.tuenti.contest.solver.Dictionary;


import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.tuenti.contest.input.Boozzle.Cell;

/**
 * User: robertcorujo
 */
public class Test {

    private Dictionary dict = new Dictionary("boozzle-dict.txt");

    @org.junit.Test
    public void testSimple() throws Exception {
        BoozzleSolver solver = new BoozzleSolver(dict);

        assertEquals(16, solver.resolve(buildSimple()));
    }

    @org.junit.Test
    public void testBig() throws Exception {
        BoozzleSolver solver = new BoozzleSolver(dict);

        solver.resolve(buildBig());
    }


    private Boozzle buildSimple() {
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        map.put('B',3);
        map.put('I',1);
        map.put('P',3);


        List<Cell> cells = new ArrayList<Cell>(Arrays.asList(
                new Cell(0,0,'B',map.get('B'),1,1),
                new Cell(0,1,'B',map.get('B'),1,1),
                new Cell(1,0,'I',map.get('I'),1,1),
                new Cell(1,1,'P',map.get('P'),1,1)
        ));


        return new Boozzle(9,2,2,map,cells);
    }

    private Boozzle buildBig() {
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        map.put('A',1);
        map.put('C',3);
        map.put('B',3);
        map.put('E',1);
        map.put('D',2);
        map.put('G',2);
        map.put('F',4);
        map.put('H',4);
        map.put('I',1);
        map.put('K',5);
        map.put('J',8);
        map.put('M',3);
        map.put('L',1);
        map.put('O',1);
        map.put('N',1);
        map.put('Q',5);
        map.put('P',3);
        map.put('S',1);
        map.put('R',1);
        map.put('U',1);
        map.put('T',1);
        map.put('W',4);
        map.put('V',4);
        map.put('Y',4);
        map.put('X',4);
        map.put('Z',10);

        List<Character> characters = new LinkedList<Character>(map.keySet());

        List<Cell> cells = new ArrayList<Cell>();

        int width = 10;
        int height = 10;
        Random random = new Random();
        for (int i = 0; i < width ; i++) {
            for (int j = 0; j < height; j++) {
                Character character = characters.get(random.nextInt(characters.size()));
                int wordMult = 1;
                int letterMult = 1;
                if (random.nextBoolean()) {
                    letterMult = (j % 3) + 1;
                } else {
                    wordMult = (j % 3) + 1;
                }
                Cell cell = new Cell(i,j,character,map.get(character),wordMult,letterMult);
                cells.add(cell);
            }
        }

        return new Boozzle(100,width,height,map,cells);
    }
}


