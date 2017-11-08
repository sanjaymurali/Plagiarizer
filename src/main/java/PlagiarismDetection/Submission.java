package PlagiarismDetection;

import IO.Writer;

import java.io.IOException;


public class Submission {

    private String uploadFolder = "src/main/resources/uploads/";

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String[] getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String[] filePaths) {
        this.filePaths = filePaths;
    }

    public Submission() {}

    public Submission(int studentID, String name) {
        this.studentID = studentID;
        this.studentName = name;
    }

    // store the files on to disk
    public String storeSubmission(Writer writer, String fileName, byte[] fileContent) throws IOException {
        return writer.storeFileOnDisk(this.studentID, fileName, fileContent);
    }

    // to print the submission
    @Override
    public String toString() {
        String returns = "{";
        returns += this.studentID + ", ";
        returns += this.studentName + ", ";
        System.out.println("File: " + this.filePaths.length);
        for (int i = 0; i < this.filePaths.length; i++) {
            returns += this.filePaths[i] + ", ";
        }

        returns += "} ";
        return returns;

    }


    private int studentID = 0;
    private String studentName;
    private String[] filePaths;

}
