package core.IO;

import core.PlagiarizerFactory.Factory;
import core.configuration.ApplicationConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class WriterTests {
    // The Writer tests give 100% code coverage when the "uploads" folder under src/main/resources is deleted
    Writer writer;

    @Mock
    ApplicationConfig mock;

    @Before
    public void setup() {
        Factory factory = new Factory();
        writer = factory.Writer();
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
