package PlagiarismDetection;

import java.util.ArrayList;
import java.util.Iterator;

public class Assignment {

    private ArrayList<Submission> submissions = new ArrayList<>();

    private static Assignment assignmentInstance;

    private Assignment() {
    }

    // singleton
    public static Assignment createAssignment() {
        if (assignmentInstance == null)
            assignmentInstance = new Assignment();

        return assignmentInstance;
    }

    // find submission given student ID
    public Submission findSubmission(int studentID) {
        Iterator<Submission> iterator = this.submissions.iterator();
        while (iterator.hasNext()) {
            Submission currentSubmission = iterator.next();
            if (studentID == (currentSubmission.getStudentID())) {
                return currentSubmission;
            }
        }

        return null;
    }

    // Add Submission of a student
    public void pushSubmissions(Submission submission) {
        this.submissions.add(submission);
    }

    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }
}
