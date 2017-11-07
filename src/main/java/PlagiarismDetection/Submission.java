package PlagiarismDetection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Submission{

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

    private int studentID = 0;
    private String studentName;
    private String[] filePaths;

    public void storeSubmission(String name, String... paths) {
        this.studentName = name;
        this.filePaths = paths;
    }

    public String storeFileOnDisk(String name, byte[] fileContent) throws IOException {
        // check if student id exists i.e., if the directory for the student exists
        String pathToFile = createFile(name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(pathToFile);
            outputStream.write(fileContent);
        } finally {
            outputStream.close();
        }

        System.out.println(pathToFile);
        return pathToFile;
    }

    private String createFile(String name) throws IOException{
        // create a directory for student and then create the file inside of it.
        File currentDir = new File(uploadFolder+this.studentID);
        File currentFile = null;
        if(!currentDir.exists()) {
            currentDir.mkdir();
        }
        currentFile = new File(currentDir.getAbsolutePath()+"/"+this.studentID+"-"+name);
        if(!currentFile.exists()) {
            currentFile.createNewFile();
        }

        return currentFile.getAbsolutePath();
    }


    public void deleteAllUploads() {
        File currentDir = new File(uploadFolder);
        File[] files = currentDir.listFiles();
        System.out.println(files.length);
        for(File file: files) {
            if(file.isDirectory()) {
                File[] insidefiles = file.listFiles();
                for(File insidefile: insidefiles) {
                    insidefile.delete();
                }
            }
            file.delete();
        }



    }

    @Override
    public String toString() {
        String returns = "{";
        returns += this.studentID + ", ";
        returns += this.studentName + ", ";
        System.out.println("File: " + this.filePaths.length);
        for(int i=0; i<this.filePaths.length;i++) {
            returns += this.filePaths[i] + ", ";
        }

        returns += "} ";
        return returns;

    }

}
