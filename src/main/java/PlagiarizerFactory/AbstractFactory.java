package PlagiarizerFactory;

import IO.*;
import PlagiarismDetection.Assignment;
import PlagiarismDetection.Submission;

/**
 * AbstractFactory specifies the behaviour of any class implementing it. It declares methods which can create Objects
 * of classes such as "Assignment", "Submission", "Writer"
 */
public abstract class AbstractFactory {

    public abstract Assignment createAssignment();

    public abstract Submission createSubmission(int studentID, String name);

    public abstract Submission createSubmission();

    public abstract Writer Writer();
}
