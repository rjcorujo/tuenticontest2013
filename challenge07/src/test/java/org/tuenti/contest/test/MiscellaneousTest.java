package org.tuenti.contest.test;

import org.junit.Test;
import org.tuenti.contest.solver.Dictionary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * User: robertcorujo
 */
public class MiscellaneousTest {

    @Test
    public void testSearchContains() throws Exception {
        Dictionary dict = new Dictionary(".boozzle-dict.txt");

        assertTrue(dict.anyWordStartsWith("BIT"));
        assertFalse(dict.anyWordStartsWith("BAE"));
    }

    @Test
    public void testSearchWord() throws Exception {
        Dictionary dict = new Dictionary(".boozzle-dict.txt");

        assertTrue(dict.containsWord("BIT"));
        assertFalse(dict.containsWord("AZOTEMICO"));
    }

    @Test
    public void testRegExp() throws Exception {
        Pattern pattern = Pattern.compile("'(\\w)'\\s*:\\s*(\\d+)");

        Matcher matcher = pattern.matcher("{'A': 1, 'C': 3, 'B': 3, 'E': 1, 'D': 2, 'G': 2, 'F': 4, 'I': 1, 'H': 4, 'K': 5, 'J': 8, 'M': 3, 'L': 1, 'O': 1, 'N': 1, 'Q': 5, 'P': 3, 'S': 1, 'R': 1, 'U': 1, 'T': 1, 'W': 4, 'V': 4, 'Y': 4, 'X': 8, 'Z': 10}");

        assertTrue(matcher.find());

        assertEquals(2, matcher.groupCount());
        assertEquals("A", matcher.group(1));
        assertEquals("1", matcher.group(2));


    }
}
