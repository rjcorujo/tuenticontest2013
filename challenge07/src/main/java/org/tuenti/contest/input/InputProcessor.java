package org.tuenti.contest.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.tuenti.contest.input.Boozzle.Cell;


/**
 * User: robertcorujo
 */
public class InputProcessor {

    public static List<Boozzle> processInput() {
        BufferedReader bf = null;

        try {
            bf = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
            int numGames = Integer.parseInt(bf.readLine().trim());
            List<Boozzle> games = new ArrayList<Boozzle>();
            for (int i = 0; i < numGames; i++) {
                Map<Character,Integer> legend = parseCharactersLegend(bf.readLine().trim());

                int duration = Integer.parseInt(bf.readLine().trim());
                int width = Integer.parseInt(bf.readLine().trim());
                int height = Integer.parseInt(bf.readLine().trim());

                List<Cell> cells = processCells(width, height, legend, bf);

                games.add(new Boozzle(duration,width,height,legend,cells));
            }
            return games;
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

    private static List<Cell> processCells(int width, int height, Map<Character,Integer> legend, BufferedReader bf) throws IOException {
        List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < width; i++) {
            String[] row = bf.readLine().trim().split("\\s+");

            for (int j = 0; j < height; j++) {
                String cell = row[j];
                Character letter = row[j].charAt(0);
                int multType = Integer.parseInt(row[j].substring(1, 2));
                int mult = Integer.parseInt(row[j].substring(2, 3));

                int letterMult = 1;
                int wordMult = 1;
                if (multType == 1) {
                    letterMult = mult;
                } else {
                    wordMult = mult;
                }
                cells.add(new Cell(i,j,letter,legend.get(letter),wordMult,letterMult));
            }
        }
        return cells;
    }

    private static Map<Character, Integer> parseCharactersLegend(String line) {
        Map<Character,Integer> legend = new HashMap<Character, Integer>();

        Pattern pattern = Pattern.compile("'(\\w)'\\s*:\\s*(\\d+)");

        Matcher matcher = pattern.matcher(line);

        while (matcher.find()) {
            legend.put(matcher.group(1).charAt(0), Integer.parseInt(matcher.group(2)));
        }

        return legend;
    }

}
