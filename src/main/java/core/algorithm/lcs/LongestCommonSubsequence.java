package core.algorithm.lcs;

import java.util.ArrayList;
import java.util.List;

/**
 * A class designed to perform the longest common subsequence algorithm.
 */
public class LongestCommonSubsequence {

  /**
   * Returns an array representing the traversal of LCS through two strings.
   * @param one the first String in the comparison.
   * @param two the second String in the comparison.
   * @return an array representing the traversal.
   */
  public static String longestCommonSubsequence(String one, String two) {
    if (one == null || two == null) {
      return "";
    }

//    List lcs = new ArrayList();
    String str = "";
    int array[][] = new int[one.length()+1][two.length()+1];
    for (int i = 0; i <= one.length(); i++) {
      array[i][0] = 0;
    }
    for (int j = 0; j <= two.length(); j++) {
      array[0][j] = 0;
    }
    for (int i = 0; i <= one.length(); i++) { // may have to make this 1
      for (int j = 0; j <= two.length(); j++) { // may have to make this 1
        if (i == 0 || j == 0) {
          array[i][j] = 0;
        }
        else if (one.charAt(i-1) == two.charAt(j-1)) {
          array[i][j] = array[i-1][j-1] + 1; // as this may cause an array out of bounds, from the beginning
          if (array[i][j] > array[i-1][j] && array[i][j] > array[i][j-1]) {
            str = str + (one.charAt(i-1));
          }
        }
        else if (array[i-1][j] <= array[i][j-1]) {
          array[i][j] = array[i][j-1];
        }
        else if (array[i-1][j] > array[i][j-1]) {
          array[i][j] = array[i-1][j];
        }
        else {
          System.out.println("REACHED SOMEWHERE WE SHOULD NEVER REACH");
        }
      }
    }

//    for (int i = 0; i <= one.length(); i++) { // may have to make this 1
//      for (int j = 0; j <= two.length(); j++) { // may have to make this 1
//        System.out.print(array[i][j]);
//        System.out.print(" ");
//      }
//      System.out.println();
//    }
//    for (Object c: lcs) {
//      System.out.print(c);
//    }
//    System.out.println();

    System.out.println("The length is:" + array[one.length()][two.length()]);
    return str;
  }

}