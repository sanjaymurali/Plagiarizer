package routes.model;

// This is the model class for a selected student for comparison
public class Students {
    // All these members refer to the selected student
    private int studentID;
    private String studentName;
    private String[] fileNames; // selected files for comparison
    private String[] filePaths; // selected files' paths


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

    public String[] getFileNames() {
        return fileNames;
    }

    public void setFileNames(String[] fileNames) {
        this.fileNames = fileNames;
    }

    public String[] getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(String[] filePaths) {
        this.filePaths = filePaths;
    }



    @Override
    public String toString() {
        String builder = "";

        builder = "StudentID: " + this.studentID + " StudentName: " + this.studentName + "\n" + "Files: ";

        for(int i = 0; i < this.fileNames.length; i++) {
            builder += this.fileNames[i] + ", ";
        }

        builder += "\n Paths: ";
        for(int i = 0; i < this.filePaths.length; i++) {
            builder += this.filePaths[i] + ", ";
        }

        return builder;

    }

}
