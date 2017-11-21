package core.PlagiarismDetection;

import core.IO.Writer;

import java.io.IOException;

/**
 * The Submission class consists a student's submission.
 */
public class Submission {

    private int studentID;
    private String studentName;
    private String[] filePaths;
    private String[] fileNames;

    // Default Constructor
    public Submission() {}

    /**
     *
     * @param studentID is the unique ID given to each student
     * @param name is the name of the student
     */
    public Submission(int studentID, String name) {
        this.studentID = studentID;
        this.studentName = name;
    }

    /**
     *
     * @param studentID is the unique ID given to each student
     * @param name is the name of the student
     * @param filePaths is the absolute paths to each file uploaded by the student
     * @param fileNames is the file names of each file uploaded by the students
     */
    public Submission(int studentID, String name, String[] filePaths, String[] fileNames) {
        this.studentID = studentID;
        this.studentName = name;
        this.filePaths = filePaths;
        this.fileNames = fileNames;
    }

    /**
     *
     * @param writer is the Writer which provides method to write the contents of the file onto disk
     * @param fileName is the name of the file
     * @param fileContent is the content of the file as byte array
     * @return a String which is the absolute path to the file on the disk
     * @throws IOException when there is an error in writing the file
     */
    public String storeSubmission(Writer writer, String fileName, byte[] fileContent) throws IOException {
        System.out.println("Inside!");
        return writer.storeFileOnDisk(this.studentID, fileName, fileContent);
    }

    /**
     *
     * @param fileName is the file name whose absolute path we need
     * @return the absolute path to the file
     */
    public String getAbsolutePath(String fileName) {
        for (int i = 0; i < this.fileNames.length; i++) {
            if(this.fileNames[i].equals(fileName)) {
                return this.filePaths[i];
            }
        }
        return null;
    }

    /**
     *
     * @return the Student ID
     */
    public int getStudentID() {
        return studentID;
    }

    /**
     *
     * @param studentID is the ID of the student
     */
    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    /**
     *
     * @return the name of the student
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     *
     * @param studentName is the name of the student
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     *
     * @return the absolute paths to each file uploaded by the student
     */
    public String[] getFilePaths() {
        return filePaths;
    }

    /**
     *
     * @param filePaths is the absolute paths to each file uploaded by the student
     */
    public void setFilePaths(String[] filePaths) {
        this.filePaths = filePaths;
    }

    /**
     *
     * @return the file names of each file uploaded by the students
     */
    public String[] getFileNames() {
        return fileNames;
    }

    /**
     *
     * @param fileNames is the file names of each file uploaded by the students
     */
    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    /**
     * to print the submission
     * @return the String version of the Submission Object
     */
    @Override
    public String toString() {
        String returns = "{";
        returns += this.studentID + ", ";
        returns += this.studentName + ", ";
        for (int i = 0; i < this.filePaths.length; i++) {
            returns += this.filePaths[i] + ", ";
        }

        for (int i = 0; i < this.fileNames.length; i++) {
            returns += this.fileNames[i] + ", ";
        }

        returns += "} ";
        return returns;

    }
}
