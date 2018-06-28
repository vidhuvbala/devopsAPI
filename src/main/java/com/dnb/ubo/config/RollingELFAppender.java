package com.dnb.ubo.config;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.qos.logback.core.rolling.RollingFileAppender;
/**
 *  @Name: RollingELFAppender.java
 *
 *  @author: Cognizant Technology Solutions
 *  @Created_On: Jan 18, 2018
 *
 */
public class RollingELFAppender extends RollingFileAppender {

    String version;
    String fields;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public void openFile(String fileName) throws IOException {
        super.openFile(fileName);
        Path path = Paths.get(fileName);
        File activeFile = path.toFile();
        if (activeFile.exists() && activeFile.isFile() && activeFile.length() == 0) {
            lock.lock();
            try {
                new PrintWriter(new OutputStreamWriter(getOutputStream(), StandardCharsets.UTF_8), true).println("#Version: " + version + "\n" + "#Fields: " + fields);
            } finally {
                lock.unlock();
            }
        }
    }

}
