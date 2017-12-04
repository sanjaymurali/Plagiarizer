package routes.model;

// This is the model class for a selected student for comparison
public class Students {
    // All these members refer to the selected student
    private int studentID;
    private String studentName;
    private String[] fileNames; // selected files for comparison
    private String[] filePaths; // selected files' paths

    public String[] getFilePaths() {
        return filePaths;
    }

}
