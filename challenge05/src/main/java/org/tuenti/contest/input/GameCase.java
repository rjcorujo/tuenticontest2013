package org.tuenti.contest.input;

import java.util.List;

/**
 * User: robertcorujo
 */
public class GameCase {
    private int width;
    private int height;
    private Cell start;
    private List<GemCell> gems;
    private int seconds;

    public GameCase(int width, int height, Cell start, List<GemCell> gems, int seconds) {
        this.width = width;
        this.height = height;
        this.start = start;
        this.gems = gems;
        this.seconds = seconds;
    }

    public Cell getStart() {
        return start;
    }

    public List<GemCell> getGems() {
        return gems;
    }

    public int getSeconds() {
        return seconds;
    }

    public static class Cell {
        private int x;
        private int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int computeDistanceToCellAvoidingMiddle(Cell target, Cell middle) {
            int avoidMiddleTime = 0;
            if (middle != null) {
                if (isCellInMiddle(this,target,middle)) {
                    avoidMiddleTime = 2; // you spend to seconds to avoid use the previous cell
                }
            }
            return (Math.abs(this.x - target.x) + (Math.abs(this.y - target.y))) + avoidMiddleTime;
        }

        private static boolean isCellInMiddle(Cell sourceCell, Cell targetCell, Cell testCell) {
            boolean isInMiddle = false;
            if (sourceCell.x == targetCell.x && testCell.x == sourceCell.x) {
                isInMiddle = isValueInMiddle(sourceCell.y, targetCell.y, testCell.y);
            } else if (sourceCell.y == targetCell.y && testCell.y == sourceCell.y) {
                isInMiddle = isValueInMiddle(sourceCell.x, targetCell.x, testCell.x);
            }
            return isInMiddle;

        }

        private static boolean isValueInMiddle(int val1, int val2, int value) {
            int init = Math.min(val1,val2);
            int end = Math.max(val1, val2);
            return value > init && value < end;
        }


        public Path computePathToCellAvoidingMiddle(GemCell target, Cell previousCell) {
            int secondsToTarget = computeDistanceToCellAvoidingMiddle(target, previousCell);

            Cell newPreviousCell = computeNewPreviousCellToTarget(target);
            return new Path(newPreviousCell,secondsToTarget);
        }

        private Cell computeNewPreviousCellToTarget(Cell target) {
            int newX;
            int newY;
            if (this.x < target.x) {
                newX = target.x-1;
            } else if (this.x > target.x) {
                newX = target.x+1;
            } else {
                newX = this.x;
            }
            if (this.y < target.y) {
                newY = target.y-1;
            } else if (this.y > target.y) {
                newY = target.y+1;
            } else {
                newY = this.y;
            }

            return new Cell(newX, newY);

        }


    }

    public static class Path {
        private Cell previousCell;
        private int distance;

        public Path(Cell cell, int distance) {
            this.previousCell = cell;
            this.distance = distance;
        }

        public Cell getPreviousCell() {
            return previousCell;
        }

        public int getDistance() {
            return distance;
        }
    }

    public static class GemCell extends Cell implements Comparable<GemCell>{
        private int score;

        public GemCell(int x, int y, int score) {
            super(x, y);
            this.score = score;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(GemCell gemCell) {
            if (score < gemCell.score) {
                return -1;
            } else if (score > gemCell.score) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
