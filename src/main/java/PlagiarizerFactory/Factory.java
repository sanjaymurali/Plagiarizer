package PlagiarizerFactory;

import IO.*;
import PlagiarismDetection.Assignment;
import PlagiarismDetection.Submission;

public class Factory extends AbstractFactory {

    public Assignment createAssignment() {
        return Assignment.createAssignment();
    }

    public Submission createSubmission(int studentID, String name) {
        return new Submission(studentID, name);
    }

    public Submission createSubmission() {
        return new Submission();
    }

    public Writer Writer() {
        return new Writer();
    }

}
