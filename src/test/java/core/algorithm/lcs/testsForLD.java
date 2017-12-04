package core.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A test suite to test our Levenshtien Distance implementation.
 */
public class testsForLD {

    @Test
    public void testSameString() {
        String one = "thissyName";
        String two = "thissyName";

        assertEquals(0, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void testEmptyStrings() {
        String one = "";
        String two = "";

        assertEquals(0, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void testFirstStringIsEmpty() {
        String two = "thissyName";
        String one = "";

        assertEquals(10, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void testSecondStringIsEmpty() {
        String one = "thissyName";
        String two = "";

        assertEquals(10, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void stringsAreDifferentButOfEqualLength() {
        String one = "afive";
        String two = "zfour";

        assertEquals(4, new LevensthienDistance(one, two).getEditDistance());


    }

    @Test
    public void stringsAreDifferentFirstIsLonger() {
        String one = "thissyName";
        String two = "whissysas";

        assertEquals(4, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void stringsAreDifferentSecondIsLonger() {
        String two = "thissyName";
        String one = "whissysas";

        assertEquals(4, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void stringsDifferOnlyOnEndingCharacter() {
        String one = "helloL";
        String two = "helloo";

        assertEquals(1, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void stringsDifferOnlyOnBeginningCharacter() {
        String one = "Whello";
        String two = "Yhello";

        assertEquals(1, new LevensthienDistance(one, two).getEditDistance());


    }

    @Test
    public void testFirstNullSecondNotNull() {
        String one = null;
        String two = "why";

        assertEquals(3, new LevensthienDistance(one, two).getEditDistance());

    }

    @Test
    public void testOneNotNullSecondNull() {
        String two = null;
        String one = "why";

        assertEquals(3, new LevensthienDistance(one, two).getEditDistance());


    }

    @Test
    public void testBothNull() {
        String two = null;
        String one = null;

        assertEquals(0, new LevensthienDistance(one, two).getEditDistance());

    }


}
