package core.IO;

import core.PlagiarizerFactory.Factory;
import core.configuration.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class WriterTests {
    // The Writer tests give 100% code coverage when the "uploads" folder under src/main/resources is deleted
    Writer writer;

    @Before
    public void setup() {
        Factory factory = new Factory();
        writer = factory.Writer();
    }

    @BeforeClass
    public static void beforetests() {
        String pathToUploadFolder = ApplicationConfig.pathToUploadFolder;
        File currentDir = new File(pathToUploadFolder);
        File[] files = currentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File[] insidefiles = file.listFiles();
                    for (File insidefile : insidefiles) {
                        insidefile.delete();
                    }
                }
                file.delete();
            }
        }
        currentDir.delete();
    }


    @Test
    public void test1() throws Exception {
        byte[] content = "Hello this is a program".getBytes();
        writer.storeFileOnDisk(0, "sanjay.java", content);

        // There must a file named "0-sanjay.java" in src/main/resources/uploads/0

        // creating a pointer to file
        File pointer = new File("src/main/resources/uploads/0/0-sanjay.java");
        assertTrue(pointer.exists());

        // pointer to a file that doesnt exists
        File pointer1 = new File("src/main/resources/uploads/0/0-sanjay1.java");
        assertFalse(pointer1.exists());
    }

    @After
    public void deleteAll() {
        writer.deleteAllUploads();
    }

}
