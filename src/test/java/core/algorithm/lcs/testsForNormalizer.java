package core.algorithm.lcs;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Test suite that ensures files are read and normalized correctly.
 */
public class testsForNormalizer {

    String pathToFolder = "src/main/resources/tests/";

    @Test
    public void test() throws IOException {
        String filePath = pathToFolder + "Rectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "class Rectangle {\n" +
                "private int variable;\n" +
                "private int variable;\n" +
                "public Rectangle(int variable, int variable) {\n" +
                "this.variable = variable;\n" +
                "this.variable = variable;\n" +
                "}\n" +
                "public int method() {\n" +
                "return variable * variable;\n" +
                "}\n" +
                "private void method() {\n" +
                "int j;\n" +
                "for (int i = 0;i < 100;i++) {\n" +
                "j = i;\n" +
                "}\n" +
                "}\n" +
                "static int method() {\n" +
                "return 100;\n" +
                "}\n" +
                "public void method(int i) {\n" +
                "i = 5000;\n" +
                "}}";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void badFile() {
        String filePath = "test/NotRealPath.java";
        Normalizer s;
        try {
            s = new Normalizer(filePath);
            fail();
        } catch (IOException e) {
            return;
        }

    }

    @Test
    public void testNoConstructorOrVariables() throws IOException {
        String filePath = pathToFolder+"noConstructorOrVariablesRectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "public class noConstructorOrVariablesRectangle {\n" +
                "\n" +
                "public int method() {\n" +
                "return 10 * 100;\n" +
                "}\n" +
                "}";
        Assert.assertEquals(s.getNormalized(), expected);

    }
    @Test
    public void testNoHeader() throws IOException { //TODO this doesn't work when first line is // comment
        String filePath = pathToFolder+"testEmptyClass.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        System.out.println(s.getNormalized());
        Assert.assertEquals("", s.getNormalized());

    }

}