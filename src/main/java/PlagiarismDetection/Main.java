package PlagiarismDetection;

import PlagiarizerFactory.Factory;

import java.io.IOException;

// dummy to test
public class Main {
    public static void main(String... args) throws IOException {
        Factory factory = new Factory();

        Submission s = factory.createSubmission(5, "sanjay");
        String[] paths = {"/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try copy.java","/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try.java"};
        s.setFilePaths(paths);

        Assignment a = factory.createAssignment();

        a.pushSubmissions(s);

        IO.Reader reader = new IO.Reader();

        reader.getFile("/Users/sanjaymurali/Desktop/PlagiarismDetector/src/main/resources/uploads/0/0-try.java");




    }
}
