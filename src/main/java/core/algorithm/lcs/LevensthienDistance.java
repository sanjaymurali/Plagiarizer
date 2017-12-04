package core.algorithm.lcs;

/**
 * A class representing the Levenshtien Distance algorithm for computing the amount
 * of operations needed to convert one string into another.
 *
 * Of note, the prior implementation simply held a static method, but was changed to this for
 * better memory management. In addition, we could add related methods to this class which could
 * show what was edited, etc, that otherwise would not have made sense with just the use of a
 * static method.
 */

public class LevensthienDistance {

    private int editDistance;

    /**
     * The constructor which modifies the private field, editDistance, which
     * tracks the amount of operations it would take to edit one distance into another.
     * @param one
     * @param two
     */
    public LevensthienDistance(String one, String two) {
        if (one == null && two != null) {
            editDistance = two.length();
            return;
        }
        if (one != null && two == null) {
            editDistance = one.length();
            return;
        }
        if (one == null && two == null) {
            editDistance = 0;
            return;
        }

        int lengthOne = one.length();
        int lengthTwo = two.length();

        int distance[][] = new int[lengthOne + 1][lengthTwo + 1];

        for (int i = 0; i < lengthOne + 1; i++) {
            for (int j = 0; j < lengthTwo + 1; j++) {
                distance[i][j] = 0;
            }
        }
        for (int i = 0; i < lengthOne + 1; i++) {
            for (int j = 0; j < lengthTwo + 1; j++) {
                if (i == 0) {
                    distance[i][j] = j;
                } else if (j == 0) {
                    distance[i][j] = i;
                } else if (one.charAt(i - 1) == two.charAt(j - 1)) {
                    distance[i][j] = Math.min(Math.min(distance[i - 1][j - 1], distance[i - 1][j]), distance[i][j - 1]);
                } else if (one.charAt(i - 1) != two.charAt(j - 1)) {
                    // This has the substitution, insertion, and deletion, all being valued as one operation.
                    // We may wish to consider having substitution being valued as two characters.
                    distance[i][j] = 1 + Math.min(Math.min(distance[i][j - 1], distance[i - 1][j]), distance[i - 1][j - 1]);

                }
            }
        }
        editDistance = distance[lengthOne][lengthTwo];

    }

    /**
     * Getter function to return the amount of operations it takes to manipulate
     * one String into another.
     * @return the amount of edits represented by an integer.
     */
    public int getEditDistance() {
        return editDistance;
    }


}
