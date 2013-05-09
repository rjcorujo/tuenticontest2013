package org.tuenti.contest.test;

import org.junit.Test;
import org.tuenti.contest.solver.Dictionary;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: robertcorujo
 */
public class MiscellaneousTest {

    @Test
    public void testSearchContains() throws Exception {
        Dictionary dict = new Dictionary("boozzle-dict.txt");

        assertTrue(dict.anyWordStartsWith("BIT"));
        assertFalse(dict.anyWordStartsWith("BAE"));
    }

    @Test
    public void testSearchWord() throws Exception {
        Dictionary dict = new Dictionary("boozzle-dict.txt");

        assertTrue(dict.containsWord("BIT"));
        assertFalse(dict.containsWord("AZOTEMICO"));
    }
}
