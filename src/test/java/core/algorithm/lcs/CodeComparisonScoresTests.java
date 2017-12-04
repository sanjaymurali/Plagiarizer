package core.algorithm.lcs;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * A class designed to test the ComparisonScoresTest class.
 */
public class CodeComparisonScoresTests {

  /**
   * Tests that two single files can be seen as identical.
   * @throws IOException from the normalization method, encapsulated in CodeComparisonScores,
   * is not able to find the file path.
   */
  @Test
  public void testTwoSingleFilesWhichAreIdentical() throws IOException {
    String pathOne = "src/main/resources/tests/rectangle.java";
    String pathTwo = "src/main/resources/tests/rectangle.java";

    String[] folderOne = new String[1];
    String[] folderTwo = new String[1];

    folderOne[0] = pathOne;
    folderTwo[0] = pathTwo;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(100.00, codeComparisonScores.getOverallTextDiffScore(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLCS(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLD(), 0.1);

  }

  /**
   * Tests that two identical sets of files, regardless of the order submitted, report
   * that there is a direct copy.
   * @throws IOException
   */
  @Test
  public void testThatOrderDoesNotMatterForIdenticalSetsOfFiles() throws IOException {
    String pathOne = "src/main/resources/tests/rectangle.java";
    String pathTwo = "src/main/resources/tests/mergesorter.java";
    String pathThree = "src/main/resources/tests/quicksorter.java";

    String[] folderOne = new String[3];
    String[] folderTwo = new String[3];

    folderOne[0] = pathOne;
    folderTwo[0] = pathTwo;
    folderOne[1] = pathThree;
    folderTwo[1] = pathOne;
    folderOne[2] = pathTwo;
    folderTwo[2] = pathThree;


    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(100.00, codeComparisonScores.getOverallTextDiffScore(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLCS(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLD(), 0.1);

  }

  /**
   * Tests that plagiarism is detected within the set of files, one of which is directly
   * copied, one that is not.
   *
   * As of now the implementation we have, showed us the max file match. This could be altered
   * in future expansions. This test would have to change accordingly.
   *
   * @throws IOException
   */
  @Test
  public void testTwoFilesOnePlagiarizedFileOneAuthentic() throws IOException {
    String pathOne = "src/main/resources/tests/rectangle.java";
    String pathTwo = "src/main/resources/tests/testCompare.java";
    String pathThree = "src/main/resources/tests/quicksorter.java";

    String[] folderOne = new String[2];
    String[] folderTwo = new String[2];

    folderOne[0] = pathOne;
    folderOne[1] = pathThree;
    folderTwo[0] = pathTwo;
    folderTwo[1] = pathOne;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(100.00, codeComparisonScores.getOverallTextDiffScore(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLCS(), 0.1);
    assertEquals(100.00, codeComparisonScores.getScoreForLD(), 0.1);


  }

  /**
   * Tests that two unique implemntations of a sorter, do not report a high score.
   * @throws IOException from the normalization method not locating a file path.
   */
  @Test
  public void testNonPlagiarizedFiles() throws IOException {
    String pathOne = "src/main/resources/tests/mergesorter.java";
    String pathTwo = "src/main/resources/tests/quicksorter.java";

    String[] folderOne = new String[1];
    String[] folderTwo = new String[1];

    folderOne[0] = pathOne;
    folderTwo[0] = pathTwo;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(44.20, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(52.5092936802974, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(36.152416356877325, codeComparisonScores.getScoreForLD(), 0.2);

  }

  /**
   * Tests that unique files report a low score.
   * @throws IOException from the normalization not finding the file path.
   */
  @Test
  public void testNonPlagiarizedFilesSecondCase() throws IOException {
    String pathThree = "src/main/resources/tests/rectangle.java";
    String pathFour = "src/main/resources/tests/testCompare.java";

    String[] folderOne = new String[1];
    String[] folderTwo = new String[1];

    folderOne[0] = pathThree;
    folderTwo[0] = pathFour;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(16.57, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(11.14, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(22.00, codeComparisonScores.getScoreForLD(), 0.2);

  }


  /**
   * Looking at the two tests directly above here, we should see that the scores from
   * test one (testNonPlagiarizedFiles) should hold here, not from the
   * testNonPlagiarizedFilesSecondCase.
   *
   * @throws IOException from the path not being found from the Normalizer.
   */
  @Test
  public void testNonPlagiarizedFilesWithTwoFiles() throws IOException {
    String pathOne = "src/main/resources/tests/mergesorter.java";
    String pathTwo = "src/main/resources/tests/quicksorter.java";
    String pathThree = "src/main/resources/tests/rectangle.java";
    String pathFour = "src/main/resources/tests/testCompare.java";

    String[] folderOne = new String[2];
    String[] folderTwo = new String[2];

    folderOne[0] = pathOne;
    folderTwo[0] = pathTwo;
    folderOne[1] = pathThree;
    folderTwo[1] = pathFour;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(44.20, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(52.5092936802974, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(36.152416356877325, codeComparisonScores.getScoreForLD(), 0.2);

  }

  /**
   * Looking at the two tests directly above here, we should see that the scores from
   * test one (testNonPlagiarizedFiles) should hold here, not from the
   * testNonPlagiarizedFilesSecondCase.
   * Reversing these is a test to ensure branch coverage is obtained.
   *
   * @throws IOException from the path not being found from the Normalizer.
   */
  @Test
  public void testNonPlagiarizedFilesWithTwoFilesFlipped() throws IOException {
    String pathOne = "src/main/resources/tests/mergesorter.java";
    String pathTwo = "src/main/resources/tests/quicksorter.java";
    String pathThree = "src/main/resources/tests/rectangle.java";
    String pathFour = "src/main/resources/tests/testCompare.java";

    String[] folderOne = new String[2];
    String[] folderTwo = new String[2];

    folderOne[0] = pathThree;
    folderTwo[0] = pathFour;
    folderOne[1] = pathOne;
    folderTwo[1] = pathTwo;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(44.20, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(52.5092936802974, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(36.152416356877325, codeComparisonScores.getScoreForLD(), 0.2);

  }

  /**
   * Tests that there has been a direct copy within a set of files between two students.
   * The amount of files between each student is no longer equal.
   *
   * @throws IOException is thrown if the file path is invalid.
   */
  @Test
  public void nonEqualAmountsOfFiles() throws IOException {
    String pathOne = "src/main/resources/tests/mergesorter.java";
    String pathTwo = "src/main/resources/tests/quicksorter.java";
    String pathThree = "src/main/resources/tests/rectangle.java";
    String pathFour = "src/main/resources/tests/testCompare.java";

    String[] folderOne = new String[2];
    String[] folderTwo = new String[3];

    folderOne[0] = pathThree;
    folderTwo[0] = pathFour;
    folderOne[1] = pathOne;
    folderTwo[1] = pathTwo;
    folderTwo[2] = pathOne;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(100, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(100, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(100, codeComparisonScores.getScoreForLD(), 0.2);

  }

  /**
   * Tests that the exception is properly thrown.
   * @throws IOException from the path not being found from the Normalizer.
   */
  @Test(expected = IOException.class)
  public void pathBroken() throws IOException {
    String pathOne = "src/main/resources/tests/mergesort";
    String pathTwo = "src/main/resources/tests/quicksorter.java";
    String pathThree = "src/main/resources/tests/rectangle.java";
    String pathFour = "src/main/resources/tests/testCompare.java";

    String[] folderOne = new String[2];
    String[] folderTwo = new String[2];

    folderOne[0] = pathOne;
    folderTwo[0] = pathTwo;
    folderOne[1] = pathThree;
    folderTwo[1] = pathFour;

    CodeComparisonScores codeComparisonScores = new CodeComparisonScores(folderOne, folderTwo);
    assertEquals(44.20, codeComparisonScores.getOverallTextDiffScore(), 0.2);
    assertEquals(52.91, codeComparisonScores.getScoreForLCS(), 0.2);
    assertEquals(35.49, codeComparisonScores.getScoreForLD(), 0.2);

  }


}

