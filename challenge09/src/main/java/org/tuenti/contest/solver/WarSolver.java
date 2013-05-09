package org.tuenti.contest.solver;

import org.tuenti.contest.input.WarScenario;

/**
 * User: robertcorujo
 */
public class WarSolver {


    public int fight(WarScenario scenario) {

        //simple cases, we have as soldiers as canyon width, resist forever!!
        if (scenario.getBudget()/scenario.getSoldierPrice() >= scenario.getWidth()) {
            return -1;
        }

        return computeTimeAlive(scenario);

    }

    private int computeTimeAlive(WarScenario scenario) {
        int maxSoldier = scenario.getBudget()/scenario.getSoldierPrice();

        int maxTime = 0;
        for (int soldiers = maxSoldier; soldiers >= 0; soldiers--) {
            int crematoriums = (scenario.getBudget() - soldiers*scenario.getSoldierPrice())/scenario.getCrematoriumUsagePrice();

            maxTime = Math.max(maxTime,timeAlive(scenario,soldiers,crematoriums));
        }

        return maxTime;
    }

    private int timeAlive(WarScenario scenario, int soldiers, int crematoriums) {
        int numerator = scenario.getWidth()*(scenario.getHeight()-1) + 1;
        int denominator = scenario.getWidth() - soldiers;

        int extra = numerator % denominator > 0 ? 1 : 0;

        return (numerator/denominator + extra) * (crematoriums + 1);
    }
}
