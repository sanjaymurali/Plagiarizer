package core.algorithm.lcs;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by carolinemead on 11/12/17.
 */
public class testsForNormalizer {

    @Test
    public void test() throws Exception {
        // Sample file
        String filePath = "src/test/java/core/algorithm/lcs/Rectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "class Rectangle {\n" +
                "private int variable;\n" +
                "private int variable;\n" +
                "public Rectangle(int variable, int variable){\n" +
                "this.variable = variable;\n" +
                "this.variable = variable;\n" +
                "}\n" +
                "public int method(){\n" +
                "return variable * variable;\n" +
                "}\n" +
                "private void method(){\n" +
                "int j;\n" +
                "for (int i = 0;i < 100;i++){\n" +
                "j = i;\n" +
                "}\n" +
                "}\n" +
                "static int method(){\n" +
                "return 100;\n" +
                "}\n" +
                "public void method(int i){\n" +
                "i = 5000;\n" +
                "}}";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    /*
    @Test
    public void testComments() throws Exception {
        String result = null;
        String test = "//remove\n" +
                "don't remove //but remove this\n" +
                "//remove\n" +
                "don't remove \n";
        Normalizer s = new Normalizer(test);
        //result = s.removeComments();
        s.removeComments();
        s.removeExtraLines();
        String expected = "don't remove\ndon't remove\n";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void testLines() throws FileNotFoundException {
        String result = null;
        String test = "extra lines\n\n\n\nregular\nregular\n";
        Normalizer s = new Normalizer(test);
        s.removeExtraLines();
        String expected = "extra lines\nregular\nregular\n";
        Assert.assertEquals(s.getNormalized(), expected);

    }

    @Test
    public void testSpaces() throws FileNotFoundException {
        String result = null;
        String test = "extra       spaces    are  here, but not here.";
        Normalizer s = new Normalizer(test);
        s.removeExtraSpaces();
        String expected = "extra spaces are here, but not here.";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void findVariable() throws IOException {
        String test = "private specialNameVariable;\npublic anotherVariable;\nsome other line of code";
        Normalizer s = new Normalizer(test);
        ArrayList result = new ArrayList<String>();
        result = s.findVariables();
        ArrayList expected = new ArrayList<String>();
        expected.add("specialNameVariable");
        expected.add("anotherVariable");
        Assert.assertEquals(expected, result);

    }
    @Test
    public void replaceVariable() throws IOException {
        String test = "private specialNameVariable;\npublic anotherVariable;\nsome other line of code";
        String expected = "private variable;\npublic variable;\nsome other line of code";
        Normalizer s = new Normalizer(test);
        s.replaceVariables();
        Assert.assertEquals(expected, s.getNormalized());

    }
    */

}
