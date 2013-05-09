package org.tuenti.contest.test;

import org.tuenti.contest.solver.CheckingSolver;

import static org.junit.Assert.assertEquals;

/**
 * User: robertcorujo
 */
public class Test {

    @org.junit.Test
    public void testSimple1() throws Exception {
        CheckingSolver solver = new CheckingSolver();

        assertEquals("1b66558be2b2cd898ebc3383623a3677", solver.decode("10[abc]"));
    }

    @org.junit.Test
    public void testMultipliers() throws Exception {
        CheckingSolver solver = new CheckingSolver();

        assertEquals("8adbd18519be193db41dd5341a260963", solver.decode("1[2[3[4[5[6[7[8[9[10[11[12[13[a]]]]]]]]]]]]]"));
    }

    @org.junit.Test
    public void testComplex() throws Exception {
        CheckingSolver solver = new CheckingSolver();

        assertEquals("8dc50a44f9b41c4f3360c59aa5404914", solver.decode("e10[as51[fg20[dsafg]s65[asdf12[34[sadf]asd21[ash]jy]87[ghg92[sad]tr]]sdfg]jyu73[asdf]]f"));
    }

    @org.junit.Test
    public void testSimpleTail() throws Exception {
        CheckingSolver solver = new CheckingSolver();

        assertEquals("3528aa670ff0147f164f20c5d6ca2801", solver.decode("10[abc]d"));
    }

    @org.junit.Test
    public void testMultipleOp() throws Exception {
        CheckingSolver solver = new CheckingSolver();

        assertEquals("c53115f92f2cbee6cb25be4a42f03fba", solver.decode("a2[b3[c]]"));
    }
}
