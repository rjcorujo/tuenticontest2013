package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.tuenti.contest.input.CaveCase.CaveMap;
import static org.tuenti.contest.input.CaveCase.Location;
import static org.tuenti.contest.input.CaveCase.Obstacle;

/**
 * User: robertcorujo
 */
public class InputProcessor {

    public static List<CaveCase> processInput() {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            int numCaves = Integer.parseInt(bf.readLine().trim());
            List<CaveCase> caves = new ArrayList<CaveCase>();
            for (int i = 0; i < numCaves; i++) {
                String[] description = bf.readLine().trim().split("\\s+");
                int width = Integer.parseInt(description[0]);
                int height = Integer.parseInt(description[1]);
                float speed = Float.parseFloat(description[2]);
                float secondsWaiting = Float.parseFloat(description[3]);

                CaveMap map = processMap(bf, width, height);

                caves.add(new CaveCase(width,height,speed,secondsWaiting,map));
            }
            return caves;
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

    private static CaveMap processMap(BufferedReader bf, int width, int height) throws IOException {
        List<Obstacle> obstacles = new ArrayList<Obstacle>();
        Location startLocation = null;
        Location exitLocation = null;

        for (int row = 0; row < height; row++) {
            String elements = bf.readLine().trim();
            for (int col = 0; col < width; col++) {
                switch (elements.charAt(col)) {
                    case '#':
                        obstacles.add(new Obstacle(row,col));
                        break;
                    case 'O':
                        exitLocation = new Location(row,col);
                        break;
                    case 'X':
                        startLocation = new Location(row,col);
                        break;
                }
            }
        }
        return new CaveMap(startLocation, exitLocation, obstacles);
    }


}
