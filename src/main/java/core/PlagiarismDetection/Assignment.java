package core.PlagiarismDetection;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Assignment is a singleton which contains a List of Submissions
 */
public class Assignment {

    // An ArrayList of Submission
    private ArrayList<Submission> submissions = new ArrayList<>();

    // singleton setup

    private static Assignment assignmentInstance;

    /**
     * The Default constructor is made private to make sure it isnt directly called to create an
     * instance of the Assignment class
     */
    private Assignment() {
    }

    /**
     * @return a new instance of Assignment, iff Assignment is null else returns the current instance
     */
    public static Assignment createAssignment() {
        if (assignmentInstance == null)
            assignmentInstance = new Assignment();
        return assignmentInstance;
    }

    /**
     * @param studentID which is the unique ID given to each student while uploading
     * @return a Submission of the given studentID
     */
    public Submission findSubmission(int studentID) {
        // Iterate through all submissions and check if the studentID exists, if it exists return that Submission else null
        Iterator<Submission> iterator = this.submissions.iterator();
        while (iterator.hasNext()) {
            Submission currentSubmission = iterator.next();
            if (studentID == (currentSubmission.getStudentID())) {
                return currentSubmission;
            }
        }
        return null;
    }

    /**
     * @return ArrayList containing all the Submissions
     */
    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }

    /**
     * @param submissions is an ArrayList containing Submission as item
     */
    public void setSubmissions(ArrayList<Submission> submissions) {
        this.submissions = submissions;
    }

    /**
     * This method is used to remove all the submissions for the current assignment
     * Thus it deletes all the items of the Submissions ArrayList
     */
    public void deleteSubmissions() {
        this.submissions.clear();
    }

    /**
     * This method is used to add a Submission to the submissions ArrayList
     *
     * @param submission is the Submission which must be added to the submissions ArrayList
     */
    public void pushSubmissions(Submission submission) {
        this.submissions.add(submission);
    }

    // Test only method

    /**
     * Used to reset the assignments
     */
    public void resetAssignment() {
        assignmentInstance = null;
    }
}
