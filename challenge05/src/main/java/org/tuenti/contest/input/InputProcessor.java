package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: robertcorujo
 */
public class InputProcessor {

    public static List<GameCase> processInput() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        try {
            int numGames = Integer.parseInt(bf.readLine().trim());
            List<GameCase> cases = new ArrayList<GameCase>();
            for (int i = 0; i < numGames; i++) {
                String dimensions = bf.readLine().trim();
                String initialPosition = bf.readLine().trim();
                int seconds = Integer.parseInt(bf.readLine().trim());
                int numGems = Integer.parseInt(bf.readLine().trim());
                String gems = bf.readLine().trim();
                cases.add(buildGame(dimensions, initialPosition, seconds, numGems, gems));
            }
            return cases;
        } catch (Exception e) {
            throw new RuntimeException("Error processing input",e);
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error closing input",e);
                }
            }
        }
    }

    private static GameCase buildGame(String dimensions, String initialPosition, int seconds, int numGems, String gemsStr) {
        int width = Integer.parseInt(dimensions.split(",")[0]);
        int height = Integer.parseInt(dimensions.split(",")[1]);

        int initX = Integer.parseInt(initialPosition.split(",")[0]);
        int initY = Integer.parseInt(initialPosition.split(",")[1]);

        List<GameCase.GemCell> gems = parseGems(gemsStr);

        return new GameCase(width, height, new GameCase.Cell(initX,initY),gems, seconds);
    }

    private static List<GameCase.GemCell> parseGems(String gemsStr) {
        List<GameCase.GemCell> gems = new ArrayList<GameCase.GemCell>();
        for (String gemStr : gemsStr.split("#")) {
            int x = Integer.parseInt(gemStr.split(",")[0]);
            int y = Integer.parseInt(gemStr.split(",")[1]);
            int score = Integer.parseInt(gemStr.split(",")[2]);

            gems.add(new GameCase.GemCell(x,y,score));
        }
        return gems;
    }
}
