package com.practice.automationtesting.utils;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

    public static String getRelativeFilePath(String filename) {
        // Get the base directory path
        String baseDir = System.getProperty("user.dir");

        // Build the relative path
        String relativeFilePath = "src/test/resources/" + filename;

        // Build the absolute path
        Path absolutePath = Paths.get(baseDir, relativeFilePath);

        // Return the absolute path as a string
        return absolutePath.toString();
    }

}
