package core.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class constructed for purposes of testing the implementation of the LCS algorithm.
 */
public class LCSTests {


    @Test
    public void testSameString() {
        String one = "thissy Name";
        String two = "thissy Name";

        assertEquals(11, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void testEmptyStrings() {
        String one = "";
        String two = "";

        assertEquals(0, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void testFirstStringIsEmpty() {
        String two = "thissyName";
        String one = "";

        assertEquals(0, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void testSecondStringIsEmpty() {
        String one = "thissyName";
        String two = "";

        assertEquals(0, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void stringsAreDifferentButOfEqualLength() {
        String one = "afive";
        String two = "zfour";

        assertEquals(1, new LongestCommonSubsequence(one, two).getLengthOfLCS());


    }

    @Test
    public void stringsAreDifferentFirstIsLonger() {
        String one = "thissyName";
        String two = "whissysas";

        assertEquals(6, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void stringsAreDifferentSecondIsLonger() {
        String two = "thissyName";
        String one = "whissysas";

        assertEquals(6, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void stringsDifferOnlyOnEndingCharacter() {
        String one = "helloL";
        String two = "helloo";

        assertEquals(5, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }

    @Test
    public void stringsDifferOnlyOnBeginningCharacter() {
        String one = "Whello";
        String two = "Yhello";

        assertEquals(5, new LongestCommonSubsequence(one, two).getLengthOfLCS());


    }

    @Test
    public void testNullException() {
        String one = null;
        String two = "why";

        assertEquals(0, new LongestCommonSubsequence(one, two).getLengthOfLCS());

    }


}
