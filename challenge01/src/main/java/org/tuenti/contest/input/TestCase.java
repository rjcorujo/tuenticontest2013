package org.tuenti.contest.input;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCase {

    private int initialBudget;
    private List<Integer> exchangeRates;

    public TestCase(int initialBudget) {
        this.initialBudget = initialBudget;
        exchangeRates = new ArrayList<Integer>();
    }

    public void addExchangeRate(Integer rate) {
        exchangeRates.add(rate);
    }

    public void addExchangeRates(Integer... rates) {
        exchangeRates.addAll(Arrays.asList(rates));
    }

    public int getInitialBudget() {
        return initialBudget;
    }

    public List<Integer> getExchangeRates() {
        return exchangeRates;
    }
}
