package core.PlagiarismDetection;

import core.IO.Writer;
import core.PlagiarizerFactory.Factory;
import core.configuration.ApplicationConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SubmissionTest {

    Factory factory = new Factory();
    Assignment assignment = factory.createAssignment();


    Submission submissionForMe = mock(Submission.class);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterClass
    public static void cleanup() {
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
    }

    @Test
    public void test0AddOneSubmisions() throws IOException {
        Submission submission = factory.createSubmission();

        submission.setStudentID(1);
        submission.setStudentName("Sanjay");
        String[] files = {"1.java", "2.java"};
        submission.setFileNames(files);
        submission.setFilePaths(files);

        assertEquals(submission.getStudentID(), 1);
        assertArrayEquals(submission.getFileNames(), files);
        assertEquals(submission.getStudentName(), "Sanjay");
    }

    @Test
    public void test1AddOneSubmissionAnotherConstructor() throws IOException {
        String[] files = {"1.java", "2.java"};
        Submission submission = factory.createSubmission(2, "Sanjay", files, files);

        assertEquals(submission.getStudentID(), 2);
        assertArrayEquals(submission.getFileNames(), files);
        assertEquals(submission.getStudentName(), "Sanjay");
    }

    @Test
    public void test2AddOneSubmissionAnotherConstructor() throws IOException {
        Submission submission = factory.createSubmission(2, "Caroline");
        String[] files = {"1.java", "2.java"};
        submission.setFileNames(files);
        submission.setFilePaths(files);


        assertEquals(submission.getStudentID(), 2);
        assertArrayEquals(submission.getFileNames(), files);
        assertArrayEquals(submission.getFilePaths(), files);
        assertEquals(submission.getStudentName(), "Caroline");
    }

    @Test
    public void test3storeFileonDiskWithError() throws IOException {

        when(submissionForMe.storeSubmission(any(), any(), any())).thenThrow(new IOException("Failure!"));


        String[] files = {"1.java", "2.java"};
        Submission submission = factory.createSubmission(2, "Sanjay", files, files);
        Writer writer = factory.Writer();

        try {
            submissionForMe.storeSubmission(writer, "Sanjay", "sanjay".getBytes());
            fail("Failed!");
        } catch (IOException e) {
            assertEquals(e.getMessage(), "Failure!");
        }
    }

    @Test
    public void test4StoreFile() throws IOException {
        String[] files = {"1.java", "2.java"};
        Submission submission = factory.createSubmission(2, "Sanjay", files, files);
        Writer writer = factory.Writer();
        assertThat(submission.storeSubmission(writer, "Sanjay", "sanjay".getBytes()), containsString("2/2-Sanjay"));
    }

    @Test
    public void test5ToString() throws IOException {
        String[] files = {"1.java", "2.java"};
        Submission submission = factory.createSubmission(2, "Sanjay", files, files);
        String submissionString = "{2, Sanjay, 1.java, 2.java, 1.java, 2.java}";
        assertEquals(submission.toString(), submissionString);
    }

    @Test
    public void test6AbsolutePath() throws IOException {
        String[] files = {"1.java", "2.java"};
        Submission submission = factory.createSubmission(2, "Sanjay", files, files);
        Writer writer = factory.Writer();

        assertEquals(submission.getAbsolutePath("1.java"), "1.java");

        assertNull(submission.getAbsolutePath("3.java"));
    }
}


