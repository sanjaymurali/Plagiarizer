package core.algorithm.lcs;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;

/**
 * Test suite that ensures files are read and normalized correctly.
 */
public class NormalizerTests {

    String pathToFolder = "src/main/resources/tests/";

    @Test
    public void test() throws IOException {
        String filePath = pathToFolder + "Rectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "class Rectangle {private int variable; private int variable; " +
                "public Rectangle(int variable, int variable) {this.variable = variable; " +
                "this.variable = variable; } public int method() {return variable * variable; } " +
                "private void method() {int j; for (int i = 0; i < 100; i++) {j = i; } } " +
                "static int method() {return 100; } public void method(int i) {i = 5000; } }";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void badFile() {
        String filePath = "test/NotRealPath.java";
        try {
            Normalizer s = new Normalizer(filePath);
            fail();
        } catch (IOException e) {
            return;
        }

    }

    @Test
    public void testNoConstructor() throws IOException {
        String filePath = pathToFolder + "noConstructorRectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "public class noConstructorRectangle {private int variable; private int variable; }";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void testNoVariableDeclarations() throws IOException {
        String filePath = pathToFolder + "noVariablesRectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "public class noVariablesRectangle {public Rectangle(){1 + 1; }}";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void testNoConstructorOrVariables() throws IOException {
        String filePath = pathToFolder + "noConstructorOrVariablesRectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "public class noConstructorOrVariablesRectangle {public int method() {return 10 * 100; }}";
        Assert.assertEquals(s.getNormalized(), expected);

    }

    @Test
    public void testNoHeader() throws IOException {
        String filePath = pathToFolder + "testEmptyClass.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        Assert.assertEquals("", s.getNormalized());

    }

    @Test
    public void testPartialNormalization() throws IOException {
        String filePath = pathToFolder + "Rectangle.java";
        Normalizer s = new Normalizer(filePath);
        s.runPartialNormalization();
        String expected = "package core.algorithm.lcs;\n" +
                "\n" +
                "/*****\n" +
                " * This is a class to represent a method shape.\n" +
                " * Used in testing Normalization\n" +
                " */\n" +
                "class Rectangle {\n" +
                "\n" +
                "    //constructor\n" +
                "    public Rectangle(int variable, int variable) {\n" +
                "        this.variable = variable;\n" +
                "        this.variable = variable;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    //gets method of method\n" +
                "    public int method() {\n" +
                "        return variable * variable;\n" +
                "    }\n" +
                "\n" +
                "    private void method() {\n" +
                "        int j;\n" +
                "        for (int i = 0; i < 100; i++) {\n" +
                "            //inside of some loop\n" +
                "            j = i;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    static int method() {\n" +
                "        // variable and variable should not be removed\n" +
                "        return 100;\n" +
                "    }\n" +
                "\n" +
                "    public void method(int i) {\n" +
                "        i = 5000;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    private int variable; /* the method's variable */\n" +
                "    private int variable; /* the method's variable */\n" +
                "}";
        Assert.assertEquals(s.getNormalized(), expected);
    }

    @Test
    public void testOddVariableType() throws IOException {
        String filePath = pathToFolder + "OddVariableTypes.java";
        Normalizer s = new Normalizer(filePath);
        s.runNormalization();
        String expected = "public class OddVariableTypes<T extends Comparable<T>> implements Sorter {" +
                "private ArrayList<String> variable; private ArrayList variable; " +
                "public OddVariableTypes(){this.variable = null; this.variable = null; }" +
                "private class privateclass {private privatemethod() {} } private method(){}}";
        Assert.assertEquals(s.getNormalized(), expected);


    }

}