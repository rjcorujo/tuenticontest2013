package org.tuenti.contest.solver;

import org.tuenti.contest.input.GameCase;

import java.util.LinkedList;
import java.util.List;

import static org.tuenti.contest.input.GameCase.Cell;
import static org.tuenti.contest.input.GameCase.GemCell;
import static org.tuenti.contest.input.GameCase.Path;

/**
 * User: robertcorujo
 */
public class GameSolver {

    public int resolve(GameCase game) {

        Cell start = game.getStart();
        List<GemCell> gems = game.getGems();

        Cell previousCell = null;
        return iterate(start,previousCell,game.getSeconds(),gems, 0, 0);
    }

    private int iterate(Cell start, Cell previousCell, int seconds, List<GemCell> gems, int bestScore, int acumScore) {

        //Check an upper bound function to avoid branching
        //we are supposing all gems used from now on will be the max score of then a REALLY optimistic guess
        //this can be improved so we can avoid branching more
        int max = getMaxGemValue(gems);
        int maxPossibleScore = acumScore + seconds * max; //supposing minimum movement time 1 second per movement

        if (maxPossibleScore <= bestScore) {
            return bestScore;
        }

        int maxLocalScore = acumScore;
        for (GemCell gem : gems) {
            Path path = start.computePathToCellAvoidingMiddle(gem,previousCell);
            int secondsLeft = seconds - path.getDistance();
            if (secondsLeft > 0) {
                List<GemCell> restGems = new LinkedList<GemCell>(gems);
                restGems.remove(gem);
                maxLocalScore = Math.max(iterate(gem, path.getPreviousCell(), secondsLeft, restGems, bestScore, acumScore + gem.getScore()) , maxLocalScore);
            } else if (secondsLeft == 0) {
                maxLocalScore = Math.max(acumScore + gem.getScore(), maxLocalScore);
            }
            bestScore = Math.max(bestScore, maxLocalScore);
        }
        return maxLocalScore;
    }

    private int getMaxGemValue(List<GemCell> gems) {
        int max = 1;
        for (GemCell gem : gems) {
            max = Math.max(max,gem.getScore());
        }
        return max;
    }


}
