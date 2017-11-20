package core.PlagiarizerFactory;

import core.IO.*;
import core.PlagiarismDetection.Assignment;
import core.PlagiarismDetection.Submission;

/**
 * The factory extends AbstractFactory class and implements all the abstract methods in it.
 */
public class Factory extends AbstractFactory {

    /**
     *
     * @return the Assignment (an instance of Assignment)
     */
    public Assignment createAssignment() {
        return Assignment.createAssignment();
    }

    /**
     *
     * @return the Submission
     */
    public Submission createSubmission() {
        return new Submission();
    }

    /**
     *
     * @param studentID is the unique ID given to each Student
     * @param name is the name of the student
     * @return the Submission with the given studentID and name
     */
    public Submission createSubmission(int studentID, String name) {
        return new Submission(studentID, name);
    }

    /**
     *
     * @param studentID is the unique ID given to each Student
     * @param name is the name of the student
     * @param filePaths is the absolute paths to each file uploaded by the student
     * @param fileNames is the file names of each file uploaded by the students
     * @return the Submission with the given studentID, name, filePaths and fileNames
     */
    public Submission createSubmission(int studentID, String name, String[] filePaths, String[] fileNames) {
        return new Submission(studentID, name, filePaths, fileNames);
    }

    /**
     *
     * @return the Writer
     */
    public Writer Writer() {
        return new Writer();
    }

    /**
     *
     * @return the Reader
     */
    public Reader Reader() { return new Reader(); }
}
