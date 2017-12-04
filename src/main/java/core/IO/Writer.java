package core.IO;

import core.configuration.ApplicationConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * The writer class is built on top of java core.IO
 * Acts like a helper class for Writing content/file on to the disk
 */
public class Writer {

    private String pathToUploadFolder = ApplicationConfig.pathToUploadFolder; // The relative path to the "upload" folder

    /**
     * Create the "upload" folder if it doesn't exists
     */
    public Writer() {
        createUploadFolder(); // creating the upload folder
    }


    /**
     * This method is used to write the file on to disk
     * Ex : if the StudentID is 1, file name "Hello.java" and some content
     * This function creates a folder called "1" under "upload" folder and creates a file named "Hello.java"
     * and writes the contents onto it.
     *
     * @param studentID   is the unique ID given to each student while uploading a file
     * @param name        is the name of the file
     * @param fileContent is the actual content of the file
     * @return a String which is the absolute path to the file on the disk
     * @throws IOException when there is an error in writing the file
     */
    public String storeFileOnDisk(int studentID, String name, byte[] fileContent) throws IOException {
        // check if student id exists i.e., if the directory for the student exists
        String pathToFile = createFile(studentID, name);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(pathToFile);
            outputStream.write(fileContent);
        } finally {
            // close the stream
            outputStream.close();
        }
        return pathToFile;
    }

    /**
     * Delete all the sub-folders in the "upload" folder.
     */
    public void deleteAllUploads() {
        // Get the pointer to the "upload" directory on the disk and delete every sub-folder under it
        File currentDir = new File(pathToUploadFolder);
        File[] files = currentDir.listFiles();
        if (files != null) {
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
    }

    /* Private methods which act as helper methods */

    /**
     * @param studentID is the unique ID given to each Student when uploading
     * @return a String which is the absolute path to the "Student's folder" inside the "upload" folder
     */
    private String createFolderForStudentSubmission(int studentID) {
        // pointer to the folder containing the Student's file
        File currentDir = new File(pathToUploadFolder + studentID);
        if (!currentDir.exists()) {
            currentDir.mkdirs();
        }
        return currentDir.getAbsolutePath();
    }

    /**
     * This function creates the "upload" folder if it doesnt exists
     */
    private void createUploadFolder() {
        if (!checkUploadFolder()) {
            File uploadFolder = new File(pathToUploadFolder);
            uploadFolder.mkdir();
        }
    }

    /**
     * @return true iff the "upload" folder exists; else false
     */
    private Boolean checkUploadFolder() {
        // pointer to the "upload" folder
        File uploadFolder = new File(pathToUploadFolder);
        return uploadFolder.exists();
    }

    /**
     * Given a StudentID and file name, this method, creates a folder with studentID as name and create a file with given
     * file name.
     * Ex: if studentID is 1, and name is "Hello.java"
     * a folder named "1" is created inside "upload" folder and a file called "Hello.java" is created inside the folder
     * The file name is changed as "1-Hello.java"
     *
     * @param studentID is the unique ID given to each student while uploading
     * @param name      of the file
     * @return a String which is the absolute path to the file
     * @throws IOException when there is error creating a folder
     */
    private String createFile(int studentID, String name) throws IOException {
        // create a directory for student and then create the file inside of it.
        String pathToFolder = createFolderForStudentSubmission(studentID);

        File currentFile = new File(pathToFolder + File.separator + studentID + "-" + name);
        if (!currentFile.exists()) {
            currentFile.createNewFile();
        }
        return currentFile.getAbsolutePath();
    }
}
