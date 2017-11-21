package core.algorithm.lcs;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class constructed for purposes of testing the implementation of the LCS algorithm.
 */
public class testsForLCS {



  @Test
  public void testSameString() {
    String one = "thissyName";
    String two = "thissyName";

    assertEquals("thissyName", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void testEmptyStrings() {
    String one = "";
    String two = "";

    assertEquals("", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void testFirstStringIsEmpty() {
    String two = "thissyName";
    String one = "";

    assertEquals("", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void testSecondStringIsEmpty() {
    String one = "thissyName";
    String two = "";

    assertEquals("", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void stringsAreDifferentButOfEqualLength() {
    String one = "afive";
    String two = "zfour";

    assertEquals("f", LongestCommonSubsequence.longestCommonSubsequence(one, two));


  }

  @Test
  public void stringsAreDifferentFirstIsLonger() {
    String one = "thissyName";
    String two = "whissysas";

    assertEquals("hissya", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void stringsAreDifferentSecondIsLonger() {
    String two = "thissyName";
    String one = "whissysas";

    assertEquals("hissya", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void stringsDifferOnlyOnEndingCharacter() {
    String one = "helloL";
    String two = "helloo";

    assertEquals("hello", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }

  @Test
  public void stringsDifferOnlyOnBeginningCharacter() {
    String one = "Whello";
    String two = "Yhello";

    assertEquals("hello", LongestCommonSubsequence.longestCommonSubsequence(one, two));


  }

  @Test
  public void testNullException() {
    String one = null;
    String two = "why";

    assertEquals("", LongestCommonSubsequence.longestCommonSubsequence(one, two));

  }


}
