import org.junit.Assert;
import org.tuenti.contest.input.TestCase;
import org.tuenti.contest.solver.BitcoinBroker;


public class Test {

    @org.junit.Test
    public void testAlwaysDecreasing() throws Exception {
        TestCase input = new TestCase(1);
        input.addExchangeRates(10,9,8,7,6,5,4,3);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(1.0),broker.computeProfit(input));
    }

    @org.junit.Test
    public void testAlwaysIncreasing() throws Exception {
        TestCase input = new TestCase(1);
        input.addExchangeRates(1,2,3,4,5,6,7,8,9,10);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(10.0),broker.computeProfit(input));
    }

    @org.junit.Test
    public void testWaitForGoodTendency() throws Exception {
        TestCase input = new TestCase(1);
        input.addExchangeRates(1,2,3,2,2,1,5,6,7,8,1,1);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(24.0),broker.computeProfit(input));
    }

    @org.junit.Test
    public void testSimple() throws Exception {
        TestCase input = new TestCase(2);
        input.addExchangeRates(1,2,10,4,1,10);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(200.0),broker.computeProfit(input));
    }

    @org.junit.Test
    public void testSimple2() throws Exception {
        TestCase input = new TestCase(5);
        input.addExchangeRates(1,2,4,20,5,30,4,25,7);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(3750.0),broker.computeProfit(input));
    }

    @org.junit.Test
    public void testBignumbers() throws Exception {
        TestCase input = new TestCase(5);
        input.addExchangeRates(1,2,4,2000000,5,30000000,4,25,7);

        BitcoinBroker broker = BitcoinBroker.getInstance();
        Assert.assertEquals(Double.valueOf(3.75e14),broker.computeProfit(input));
    }
}
