package org.example.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

public interface Resource {

    InputStream getInPutStream() throws IOException;
}
