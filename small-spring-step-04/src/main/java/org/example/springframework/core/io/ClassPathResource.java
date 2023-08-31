package org.example.springframework.core.io;

import cn.hutool.core.util.ClassUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader != null ? classLoader : ClassUtil.getClassLoader();
    }

    @Override
    public InputStream getInPutStream() throws IOException {
        InputStream in = classLoader.getResourceAsStream(path);
        if (in == null) {
            throw new FileNotFoundException(path + "cannot be opened because it does not exist");
        }
        return in;
    }
}
