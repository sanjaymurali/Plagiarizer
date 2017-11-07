package DAO;

import java.io.File;

public class UploadDAO {
    private String name;
    private File files;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFiles() {
        return files;
    }

    public void setFiles(File files) {
        this.files = files;
    }
}