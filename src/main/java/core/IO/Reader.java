package core.IO;

import java.io.*;

/**
 * The reader class is built on top of java core.IO, which provides easy access to the Student's file(s) in the disk
 * Acts like a helper class for Reading files
 */
public class Reader {

    /**
     *
     * @param pathToFile is the absolute path to the file in the disk
     * @return a String which is the content of the file in "UTF-8" format
     * @throws IOException when the file cannot be read
     */
    public String getFile(String pathToFile) throws IOException{
        // check if file exists and then read from it
        if(checkFileExists(pathToFile)) {
            BufferedReader inputStream = null;
            try {
                // contains the content of the file in UTF-8
                inputStream = new BufferedReader(new InputStreamReader(
                        new FileInputStream(pathToFile), "UTF-8"));

                int program;
                String builder = "";
                // read character by character and append it to the builder
                while ((program = inputStream.read()) != -1) {
                    builder += (char) program;
                }
                return builder;
            } finally {
                inputStream.close();
            }
        }

        return null;
    }

    /**
     *
     * @param pathToFile is the absolute path to the file in the disk
     * @return a Boolean, true iff the file exists on the disk, else false
     */
    private Boolean checkFileExists(String pathToFile) {
        // Pointer to the file
        File file = new File(pathToFile);
        // check if the file exists
        if(file.exists())
            return true;
        else
            return false;
    }
}
