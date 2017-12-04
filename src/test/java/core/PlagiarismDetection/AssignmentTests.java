package core.PlagiarismDetection;

import core.PlagiarizerFactory.Factory;
import core.configuration.ApplicationConfig;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AssignmentTests {

    Assignment assignment;
    Factory factory;

    @Before
    public void setup() {
        factory = new Factory();
        assignment = factory.createAssignment();
    }

    @BeforeClass
    public static void initSetup() {
        Factory factory = new Factory();
        Assignment assignment = factory.createAssignment();
        assignment.resetAssignment();
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

    public void resetHelper() {
        assignment.resetAssignment();
    }

    @Test
    public void test0NoAssignments() throws IOException {
        assertEquals(assignment.getSubmissions().size(), 0);
    }

    @Test
    public void test1AddOneAssignment() throws IOException {
        Submission submission = factory.createSubmission(1, "Sanjay");
        String[] files = {"1.java", "2.java"};
        submission.setFileNames(files);
        submission.setFilePaths(files);
        assignment.pushSubmissions(submission);

        assertEquals(assignment.getSubmissions().size(), 1);
        assertEquals(assignment.getSubmissions().get(0), submission);
        assertEquals(assignment.getSubmissions().get(0).getStudentID(), 1);
        assertEquals(assignment.getSubmissions().get(0).getStudentName(), "Sanjay");
    }

    @Test
    public void test2AddAnotherAssignment() throws IOException {
        Submission submission = factory.createSubmission(2, "Caroline");
        String[] files = {"1.java", "2.java"};
        submission.setFileNames(files);
        submission.setFilePaths(files);
        assignment.pushSubmissions(submission);

        assertEquals(assignment.getSubmissions().size(), 2); // should be 2
        assertEquals(assignment.getSubmissions().get(1), submission);

        assertEquals(assignment.getSubmissions().get(0).getStudentID(), 1);
        assertEquals(assignment.getSubmissions().get(0).getStudentName(), "Sanjay");

        assertEquals(assignment.getSubmissions().get(1).getStudentID(), 2);
        assertEquals(assignment.getSubmissions().get(1).getStudentName(), "Caroline");

        resetHelper(); // resets the assignment object
    }

    @Test
    public void test3DeleteAssignmentInstance() {
        // the assignment is reset in last line of test2AddAnotherAssignment. and then @Before is called
        assertEquals(assignment.getSubmissions().size(), 0); // gets reset
    }

    @Test
    public void test4AddingMultipleSubmissions() {

        Submission submission = factory.createSubmission(1, "Sanjay");
        String[] files = {"1.java", "2.java"};
        submission.setFileNames(files);
        submission.setFilePaths(files);

        Submission submission1 = factory.createSubmission(2, "Caroline");
        submission1.setFileNames(files);
        submission1.setFilePaths(files);

        ArrayList<Submission> al = new ArrayList<>();
        al.add(submission);
        al.add(submission1);

        assignment.setSubmissions(al);
        assertEquals(assignment.getSubmissions().size(), 2); // gets reset
    }

    @Test
    public void test5FindingSubmission() {
        assertEquals(assignment.findSubmission(1).getStudentName(), "Sanjay");
        assertEquals(assignment.findSubmission(2).getStudentName(), "Caroline");
        assertNull(assignment.findSubmission(-1));
        assertNull(assignment.findSubmission(3));
    }

    @Test
    public void test6DeleteAllSubmissions() {
        assignment.deleteSubmissions();

        assertEquals(assignment.getSubmissions().size(), 0); // all submissions are deleted
    }

}
