package com.falvojr.audio2text.util;

public final class FileUtils {

    private FileUtils() { }

    public static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return filename.substring(dotIndex + 1);
    }
}
