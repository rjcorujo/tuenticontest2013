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

    public static List<WarScenario> processInput() {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            int numWars = Integer.parseInt(bf.readLine().trim());
            List<WarScenario> wars = new ArrayList<WarScenario>();
            for (int i = 0; i < numWars; i++) {
                String[] line = bf.readLine().trim().split(("\\s+"));

                int width = Integer.parseInt(line[0]);
                int height = Integer.parseInt(line[1]);
                int soldierCost = Integer.parseInt(line[2]);
                int crematoriumCost = Integer.parseInt(line[3]);
                int budget = Integer.parseInt(line[4]);

                wars.add(new WarScenario(width, height, soldierCost, crematoriumCost, budget));
            }
            return wars;
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
}
