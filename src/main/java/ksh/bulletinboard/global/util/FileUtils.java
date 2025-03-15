package ksh.bulletinboard.global.util;

import java.util.UUID;

public class FileUtils {

    private static String FILE_DIR = "C:\\Users\\saree98\\Desktop\\attachment\\";

    public static String createStoredFileName(String originalName) {
        String extension = originalName.substring(originalName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();

        return uuid + extension;
    }

    public static String createFullPath(String fileName) {
        return FILE_DIR + fileName;
    }

}
