package org.example.springframework.core.io;

import java.io.*;

public class FileSystemResource implements Resource {

    private String path;

    private File file;

    public FileSystemResource(String path) {
        this.path = path;
        this.file = new File(path);
    }

    public FileSystemResource(File file) {
        this.file = file;
        this.path = file.getPath();
    }

    @Override
    public InputStream getInPutStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
