package org.tuenti.contest.input;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: robertcorujo
 */
public class Boozzle {
    private int duration;
    private int width;
    private int height;

    private Map<Character,Integer> letters;
    private List<Cell> cells;


    public Boozzle(int duration, int width, int height, Map<Character, Integer> letters, List<Cell> cells) {
        this.duration = duration;
        this.width = width;
        this.height = height;
        this.letters = letters;
        this.cells = cells;
    }


    public int getDuration() {
        return duration;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<Character, Integer> getLetters() {
        return letters;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public LinkedList<Cell> getNeighbourCells(Cell cell) {
        int x = cell.getX();
        int y = cell.getY();

        LinkedList<Cell> neighbours = new LinkedList<Cell>();


        Cell leftCell = getCell(x,y-1);
        Cell upperLeftCell = getCell(x-1,y-1);
        Cell lowerLeftCell = getCell(x+1,y-1);

        Cell rightCell = getCell(x,y+1);
        Cell upperRightCell = getCell(x-1,y+1);
        Cell lowerRightCell = getCell(x+1,y+1);

        Cell upperCell = getCell(x-1,y);
        Cell lowerCell = getCell(x+1,y);


        return createListWithNotNullElements(leftCell, upperLeftCell, lowerLeftCell, rightCell, lowerRightCell, upperRightCell, lowerCell, upperCell);
    }

    private LinkedList<Cell> createListWithNotNullElements(Cell... cells2Add) {
        LinkedList<Cell> cells = new LinkedList<Cell>();
        for (Cell cell : cells2Add) {
            if (cell != null) {
                cells.add(cell);
            }
        }
        return cells;
    }


    public Cell getCell(int x, int y) {
        if (x < width && x >= 0 && y >=0 && y < height) {
            return cells.get(x*height+y);
        }
        return null;
    }

    public static class Cell implements Comparable<Cell> {
        private int x;
        private int y;
        private Character letter;
        private int cellValue;
        private int wordMultiplier;

        public Cell(int x, int y, Character letter, int letterValue, int wordMultiplier, int letterMultiplier) {
            this.x = x;
            this.y = y;
            this.letter = letter;
            this.wordMultiplier = wordMultiplier;
            this.cellValue = letterValue * letterMultiplier;
        }

        public int getCellValue() {
            return cellValue;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public Character getLetter() {
            return letter;
        }

        public int getWordMultiplier() {
            return wordMultiplier;
        }

        @Override
        public String toString() {
            return letter+"("+x+","+y+")";
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Cell) {
                Cell other = (Cell)o;
                return x == other.x && y == other.y;
            }
            return false;
        }

        @Override
        public int compareTo(Cell cell) {
            if (wordMultiplier < cell.wordMultiplier) {
                return -1;
            } else if (wordMultiplier > cell.wordMultiplier) {
                return 1;
            } else {
                if (cellValue == cell.cellValue) {
                    return 0;
                } else {
                    return cellValue < cell.cellValue ? -1 : 1;
                }
            }
        }
    }

}
