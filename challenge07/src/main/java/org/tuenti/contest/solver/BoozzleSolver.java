package org.tuenti.contest.solver;

import org.tuenti.contest.input.Boozzle;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.tuenti.contest.input.Boozzle.Cell;

/**
 * User: robertcorujo
 */
public class BoozzleSolver {

    private Dictionary dictionary;
    private List<Word> candidates;
    private Boozzle boozzle;
    private KnapsackSolver wordsChooser;

    public BoozzleSolver(Dictionary dictionary) {
        this.dictionary = dictionary;
        wordsChooser = new KnapsackSolver();

    }

    public int resolve(Boozzle boozzle) {
        this.candidates = new ArrayList<Word>();
        this.boozzle = boozzle;

        LinkedList<Cell> cells = new LinkedList<Cell>(boozzle.getCells());

        Collections.sort(cells);

        Iterator<Cell> cellIterator = cells.descendingIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            traverse(cell, new ArrayList<Cell>(),boozzle.getDuration());
        }


        return wordsChooser.resolve(candidates, boozzle.getDuration());
    }

    private void traverse(Cell cell, List<Cell> fixedCells, int duration) {
        BuildingWord word = buildWord(fixedCells, cell);

        boolean exists = checkExistsWord(word);
        if (exists) {
            addCandidateWord(word);
        }

        if (word.getWordStr().length() < duration - 1) {
            boolean existsLongerWord = checkExistsLongerWord(word);

            if (existsLongerWord) {
                LinkedList<Cell> neighbours = boozzle.getNeighbourCells(cell);
                Collections.sort(neighbours);

                Iterator<Cell> iterator = neighbours.descendingIterator();
                while (iterator.hasNext()) {
                    Cell neighbour = iterator.next();
                    if (!word.cells.contains(neighbour)) {
                        traverse(neighbour, word.cells, duration);
                    }
                }
            }
        }
    }

    private void addCandidateWord(BuildingWord buildingWord) {
        Word word = new Word(buildingWord.cells, buildingWord.getScore());
        int index = candidates.indexOf(word);
        if (index >= 0) {
            Word old = candidates.get(index);
            if (old.getScore() < word.getScore()) {
                candidates.set(index,word);
            }
        } else {
            candidates.add(word);
        }
    }


    private boolean checkExistsLongerWord(BuildingWord word) {
        return dictionary.anyWordStartsWith(word.getWordStr());
    }

    private boolean checkExistsWord(BuildingWord word) {
        return dictionary.containsWord(word.getWordStr());

    }


    private BuildingWord buildWord(List<Cell> fixedCells, Cell cell) {
        BuildingWord word = new BuildingWord();
        for (Cell cellAux : fixedCells) {
            word.addLetter(cellAux);
        }
        word.addLetter(cell);
        return word;
    }


    private class BuildingWord {
        private List<Cell> cells;
        private int internalScore;
        private int wordMultiplier;


        public BuildingWord() {
            cells = new LinkedList<Cell>();
            internalScore = 0;
            wordMultiplier = 1;
        }

        public void addLetter(Cell cell) {
            cells.add(cell);
            internalScore += cell.getCellValue();
            wordMultiplier = Math.max(wordMultiplier, cell.getWordMultiplier());
        }

        public int getScore() {
            return internalScore * wordMultiplier + cells.size();
        }

        public String getWordStr() {
            StringBuilder builder = new StringBuilder();
            for (Cell cell : cells) {
                builder.append(cell.getLetter());
            }
            return builder.toString();
        }

    }

    private class Word {
        private List<Boozzle.Cell> cells;
        private int score;
        private int timeConsumed;
        private String str;

        private Word(List<Boozzle.Cell> cells, int score) {
            this.cells = cells;
            this.score = score;
            this.timeConsumed = cells.size()+1;
            this.str = getWordStr();
        }

        private int getTimeConsumed() {
            return timeConsumed;
        }

        private int getScore() {
            return score;
        }

        private String getWordStr() {
            StringBuilder builder = new StringBuilder();
            for (Cell cell : cells) {
                builder.append(cell.getLetter());
            }
            return builder.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o != null && o instanceof Word) {
                Word other = (Word)o;
                return str.equals(other.str);
            }
            return false;
        }
    }

    private class KnapsackSolver {


        public int resolve(List<Word> words, int duration) {
            int previous[] = new int[duration+1];
            int current[] = new int[duration+1];

            for (int i = 0; i <= duration; i++) {
                previous[i] = 0;
            }

            current[0] = 0;
            for (int i = 0; i < words.size(); i++) {
                int costWord = words.get(i).getTimeConsumed();
                int score = words.get(i).getScore();
                for (int j = 0; j <= duration; j++) {
                    if (j >= costWord) {
                        current[j] = Math.max(previous[j], previous[j-costWord] + score);
                    } else {
                        current[j] = previous[j];
                    }
                }
                int[] aux = current;
                current = previous;
                previous = aux;
            }

            return previous[duration];
        }
    }
}
