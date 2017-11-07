package PlagiarismDetection;

import java.util.ArrayList;
import java.util.Iterator;

public class Assignment {

    private ArrayList<Submission> submissions = new ArrayList<>();

    private static Assignment assignmentInstance;
    private Assignment() {}

    public static Assignment createAssignment() {
        if(assignmentInstance == null)
            assignmentInstance = new Assignment();

        return assignmentInstance;
    }

    public Submission findSubmission(int studentID) {
        Iterator<Submission> iterator = this.submissions.iterator();
        while(iterator.hasNext()) {
            Submission currentSubmission = iterator.next();
            if(studentID == (currentSubmission.getStudentID())) {
                return currentSubmission;
            }
        }

    return null;
    }


    public void pushSubmissions(Submission submission) {
        this.submissions.add(submission);
    }

    public ArrayList<Submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(ArrayList<Submission> submissions) {
        this.submissions = submissions;
    }
}
