package core.algorithm.lcs;

/**
 * A class designed to perform the longest common subsequence algorithm.
 */
public class LongestCommonSubsequence {

    private int lengthOfLCS;

    /**
     * Modifies the private field, lengthOfLcs, which repreents the length of the LCS through
     * two strings. This may be later modified to include the actual LCS as well.
     *
     * @param one the first String in the comparison.
     * @param two the second String in the comparison.
     */
    public LongestCommonSubsequence(String one, String two) {
        String str = "";

        if (one == null || two == null) {
            lengthOfLCS = 0;
            return;
        }
        else {
            int array[][] = new int[one.length() + 1][two.length() + 1];
            for (int i = 0; i <= one.length(); i++) {
                array[i][0] = 0;
            }
            for (int j = 0; j <= two.length(); j++) {
                array[0][j] = 0;
            }
            for (int i = 0; i <= one.length(); i++) {
                for (int j = 0; j <= two.length(); j++) {
                    if (i == 0 || j == 0) {
                        array[i][j] = 0;
                    } else if (one.charAt(i - 1) == two.charAt(j - 1)) {
                        array[i][j] = array[i - 1][j - 1] + 1;
                        if (array[i][j] > array[i - 1][j] && array[i][j] > array[i][j - 1]) {
                            str = str + (one.charAt(i - 1));
                        }
                    } else if (array[i - 1][j] <= array[i][j - 1]) {
                        array[i][j] = array[i][j - 1];
                    } else {
                        array[i][j] = array[i - 1][j];
                    }
                }
            }
            lengthOfLCS = array[one.length()][two.length()];
        }

    }

    /**
     * A getter function returning the length of the LCS.
     * @return the length of the LCS, represented by an integer.
     */
    public int getLengthOfLCS() {
        return lengthOfLCS;
    }


}
