package org.tuenti.contest.solver;

import org.tuenti.contest.input.TestCase;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BitcoinBroker {
    private List<Integer> minPositions;
    private Integer[] maxRanges;


    private static BitcoinBroker broker = new BitcoinBroker();

    private BitcoinBroker() {};

    public static BitcoinBroker getInstance() {
        return broker;
    }


    public Double computeProfit(TestCase input) {

        LinkedList<Double> operations = findOperations(input.getExchangeRates());
        Double profitRate = 1.0;
        for (Double operationRate : operations) {
            profitRate *= operationRate;
        }

        return profitRate * input.getInitialBudget();
    }

    private LinkedList<Double> findOperations(List<Integer> exchangeRates) {
        int index = 0;

        if (exchangeRates.size() == 0) {
            return new LinkedList<Double>();
        }

        //ignoring decreasing tendency
        while (index < exchangeRates.size()-1 && exchangeRates.get(index) >= exchangeRates.get(index+1)) {
            index++;
        }

        Integer buyValue = Integer.MAX_VALUE;
        while (index < exchangeRates.size()-1 && exchangeRates.get(index) <= exchangeRates.get(index+1)) {
            buyValue = Math.min(buyValue, exchangeRates.get(index));
            index++;
        }

        LinkedList<Double> operations = new LinkedList<Double>();

        if (buyValue < exchangeRates.get(index)) {
            operations.add(exchangeRates.get(index)/(buyValue*1.0));
        }
        operations.addAll(findOperations(exchangeRates.subList(index+1,exchangeRates.size())));
        return operations;
    }




}
