package core.algorithm.lcs;

/**
* A class representing the Levenshtien Distance algorithm for computing the amount
* of operations needed to convert one string into another.
*/

public class LevensthienDistance {


  /**
   * Calculates the minimum amount of operations neede to edit one string to be a replica of another
   * (this is otherwise known as Levenshtien Distance).
   * @param one the first String.
   * @param two the second String.
   * @return an integer value representing the minimum amount of edits needed.
   */
  public static int editDistance(String one, String two) {

    if (one == null && two != null) {
      return two.length();
    }
    if (one != null && two == null) {
      return one.length();
    }
    if (one == null && two == null) {
      return 0;
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
        System.out.print(distance[i][j]);
      }
      System.out.println();
    }

    for (int i = 0; i < lengthOne + 1; i++) {
      for (int j = 0; j < lengthTwo + 1; j++) {
        if (i == 0) {
          distance[i][j] = j;
        } else if (j == 0) {
          distance[i][j] = i;
        }
//                else if (one.charAt(i-1) == two.charAt(j-1)) {
//                    distance[i][j] = distance[i-1][j-1];
//                }
        else if (one.charAt(i-1) == two.charAt(j-1)) {
          distance[i][j] = Math.min(Math.min(distance[i - 1][j - 1], distance[i - 1][j]), distance[i][j - 1]);
        }
        else if (one.charAt(i-1) != two.charAt(j-1)) {
          // This has the substitution, insertion, and deletion, all being valued as one operation.
          // We may wish to consider having substitution being valued as two characters.
          distance[i][j] = 1 + Math.min(Math.min(distance[i][j - 1], distance[i - 1][j]), distance[i-1][j - 1]);

        }
      }
    }

    for (int i = 0; i < lengthOne + 1; i++) {
      for (int j = 0; j < lengthTwo + 1; j++) {
        System.out.print(distance[i][j] + " ");
      }
      System.out.println();
    }

    System.out.println("Edit Distance is: " + distance[lengthOne][lengthTwo]);
    return distance[lengthOne][lengthTwo];

  }

}
