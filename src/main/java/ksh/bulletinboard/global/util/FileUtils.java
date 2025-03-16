package ksh.bulletinboard.global.util;

import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FileUtils {

    private static String FILE_DIR = "C:\\Users\\saree98\\Desktop\\attachment\\";

    public static String saveFile(MultipartFile multipartFile) throws IOException {
        String originalName = multipartFile.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String storedName = uuid + extension;

        String fullPath = FILE_DIR + storedName;
        multipartFile.transferTo(new File(fullPath));

        return storedName;
    }

    public static UrlResource findStoredFileResource(String fileName) throws IOException {
        String fullPath = FILE_DIR + fileName;
        return new UrlResource("file:" + fullPath);
    }

    public static String encodeFileName(String fileName) {
        return UriUtils.encode(fileName, StandardCharsets.UTF_8);
    }

}
