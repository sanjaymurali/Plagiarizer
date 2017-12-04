package core.algorithm.lcs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A class designed to calculate the scores upon comparing two files.
 */
public class CodeComparisonScores {

    private double scoreForLD;
    private double scoreForLCS;
    private double overallTextDiffScore;

    /**
     * The constructor which essentially calculates the textual similarities between two files.
     *
     * @param filesOne an array containing the file paths for the first set of files.
     * @param filesTwo an array containing the file paths for the second set of files.
     * @throws FileNotFoundException
     */
    public CodeComparisonScores(String[] filesOne, String[] filesTwo) throws IOException {

        scoreForLD = 0;
        scoreForLCS = 0;
        overallTextDiffScore = 0;

        List<String> normalizedOfFilesOne = new ArrayList<String>();
        List<String> normalizedOfFilesTwo = new ArrayList<String>();

        for (int a = 0; a < filesOne.length; a++) {
            Normalizer normalizer = new Normalizer(filesOne[a]);
            normalizer.runNormalization();
            normalizedOfFilesOne.add(normalizer.getNormalized());
        }

        for (int b = 0; b < filesTwo.length; b++) {
            Normalizer normalizer = new Normalizer(filesTwo[b]);
            normalizer.runNormalization();
            normalizedOfFilesTwo.add(normalizer.getNormalized());
        }

        for (int i = 0; i < normalizedOfFilesOne.size(); i++) {
            for (int j = 0; j < normalizedOfFilesTwo.size(); j++) {
                CodeComparisonScoresHelper(normalizedOfFilesOne.get(i), normalizedOfFilesTwo.get(j));
            }
        }

    }


    /**
     * A helper function which computes the similarity levels between two files.
     * It updates the scores which are otherwise utilized in the constructor.
     *
     * @param fileOne first String to compare
     * @param fileTwo second String to compare
     */
    private void CodeComparisonScoresHelper(String fileOne, String fileTwo) {
        double maxLength = Math.max(fileOne.length(), fileTwo.length());

        int amountOfChangesLD = new LevensthienDistance(fileOne, fileTwo).getEditDistance();
        int amountLCS = new LongestCommonSubsequence(fileOne, fileTwo).getLengthOfLCS();

        double LDScore = (maxLength - amountOfChangesLD) / maxLength * 100;
        double LCSScore = amountLCS / maxLength * 100;
        double overallScore = (LCSScore + LDScore) / 2;

        if (LDScore > scoreForLD) {
            scoreForLD = LDScore;
        }
        if (LCSScore > scoreForLCS) {
            scoreForLCS = LCSScore;
        }
        if (overallScore > overallTextDiffScore) {
            overallTextDiffScore = overallScore;
        }
    }

    /**
     * A getter function for the LD results.
     *
     * @return a double representing the score.
     */
    public double getScoreForLD() {
        return scoreForLD;
    }

    /**
     * A getter function for the LCS results.
     *
     * @return a double representing the score.
     */
    public double getScoreForLCS() {
        return scoreForLCS;
    }

    /**
     * A getter function for the overall score results.
     *
     * @return a double representing the score.
     */
    public double getOverallTextDiffScore() {
        return overallTextDiffScore;
    }

}
