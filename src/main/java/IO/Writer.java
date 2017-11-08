package IO;

import configuration.ApplicationConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Writer {

    private String pathToUploadFolder = ApplicationConfig.pathToUploadFolder;

    public Writer() {
        createUploadFolder(); // creating the upload folder
    }

    // Create Upload Folder, Create Files - WRITER methods

    public String storeFileOnDisk(int studentID, String name, byte[] fileContent) throws IOException {
        // check if student id exists i.e., if the directory for the student exists
        String pathToFile = createFile(studentID, name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(pathToFile);
            outputStream.write(fileContent);
        } finally {
            outputStream.close();
        }

        //System.out.println(pathToFile);
        return pathToFile;
    }

    public void deleteAllUploads() {
        File currentDir = new File(pathToUploadFolder);
        File[] files = currentDir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                File[] insidefiles = file.listFiles();
                for (File insidefile : insidefiles) {
                    insidefile.delete();
                }
            }
            file.delete();
        }
    }


    public String createFolderForStudentSubmission(int studentID) {
        File currentDir = new File(pathToUploadFolder + studentID);
        if (!currentDir.exists()) {
            currentDir.mkdir();
        }

        return currentDir.getAbsolutePath();
    }

    // check if upload folder exists
    public Boolean checkUploadFolder() {
        File uploadFolder = new File(pathToUploadFolder);
        return uploadFolder.exists();

    }

    // create the uploads folder if it doesn't exists
    public void createUploadFolder() {
        if (!checkUploadFolder()) {
            File uploadFolder = new File(pathToUploadFolder);
            uploadFolder.mkdir();
        }
    }


    /* Private methods which act as helper methods */

    private String createFile(int studentID, String name) throws IOException {
        // create a directory for student and then create the file inside of it.
        String pathToFolder = createFolderForStudentSubmission(studentID);

        File currentFile = new File(pathToFolder + "/" + studentID + "-" + name);
        if (!currentFile.exists()) {
            currentFile.createNewFile();
        }

        return currentFile.getAbsolutePath();
    }
}
