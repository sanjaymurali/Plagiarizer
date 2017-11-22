package core.PlagiarizerFactory;

import core.IO.*;
import core.PlagiarismDetection.Assignment;
import core.PlagiarismDetection.Submission;

/**
 * AbstractFactory specifies the behaviour of any class implementing it. It declares methods which can create Objects
 * of classes such as "Assignment", "Submission", "Writer"
 */
public abstract class AbstractFactory {

    /**
     * @return the Assignment (an instance of Assignment)
     */
    public abstract Assignment createAssignment();

    /**
     * @return the Submission
     */
    public abstract Submission createSubmission();

    /**
     * @param studentID is the unique ID given to each Student
     * @param name      is the name of the student
     * @return the Submission with the given studentID and name
     */
    public abstract Submission createSubmission(int studentID, String name);

    /**
     * @param studentID is the unique ID given to each Student
     * @param name      is the name of the student
     * @param filePaths is the absolute paths to each file uploaded by the student
     * @param fileNames is the file names of each file uploaded by the students
     * @return the Submission with the given studentID, name, filePaths and fileNames
     */
    public abstract Submission createSubmission(int studentID, String name, String[] filePaths, String[] fileNames);

    /**
     * @return the Writer
     */
    public abstract Writer Writer();

    /**
     * @return the Reader
     */
    public abstract Reader Reader();
}
