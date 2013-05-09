package org.tuenti.contest.input;

/**
 * User: robertcorujo
 */
public class WarScenario {

    private int width;
    private int height;
    private int soldierPrice;
    private int crematoriumUsagePrice;
    private int budget;

    public WarScenario(int width, int height, int soldierPrice, int crematoriumUsagePrice, int budget) {
        this.width = width;
        this.height = height;
        this.soldierPrice = soldierPrice;
        this.crematoriumUsagePrice = crematoriumUsagePrice;
        this.budget = budget;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSoldierPrice() {
        return soldierPrice;
    }

    public int getCrematoriumUsagePrice() {
        return crematoriumUsagePrice;
    }

    public int getBudget() {
        return budget;
    }
}
