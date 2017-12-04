package core.IO;

import core.PlagiarizerFactory.Factory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ReaderTests {

    Reader reader;

    String pathToFile = "src/main/resources/tests/ReaderRectangle.java";

    @Before
    public void settingUp() {
        Factory factory = new Factory();
        reader = factory.Reader();
    }


    // A path that doesn't exists
    @Test
    public void testNoFile() throws IOException {
        String s = reader.getFile("Empty");

        assertNull(s);
    }

    // A path that exists
    @Test
    public void testFile() throws IOException {

        String s = reader.getFile(pathToFile);

        String program = "package core.algorithm.lcs;\n" +
                "\n" +
                "/*****\n" +
                " * This is a class to represent a rectangle shape.\n" +
                " * Used in testing Normalization\n" +
                " */\n" +
                "class ReaderRectangle {\n" +
                "\n" +
                "    //constructor\n" +
                "    public Rectangle(int height, int width) {\n" +
                "        this.height = height;\n" +
                "        this.width = width;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    //gets area of rectangle\n" +
                "    public int area() {\n" +
                "        return height * width;\n" +
                "    }\n" +
                "\n" +
                "    private void someFunction() {\n" +
                "        int j;\n" +
                "        for (int i = 0; i < 100; i++) {\n" +
                "            //inside of some loop\n" +
                "            j = i;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    static int someOtherFunction() {\n" +
                "        // height and width should not be removed\n" +
                "        return 100;\n" +
                "    }\n" +
                "\n" +
                "    public void rectangle(int i) {\n" +
                "        i = 5000;\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    // 文档\n" +
                "\n" +
                "    private int height; /* the rectangle's height */\n" +
                "    private int width; /* the rectangle's width */\n" +
                "}";

        assertEquals(s, program);
    }

    // to make sure non-english characters are read
    @Test
    public void testNonEnglishCharactersInProgram() throws IOException {
        String s = reader.getFile(pathToFile);

        assertTrue(s.contains("文档"));
    }
}
