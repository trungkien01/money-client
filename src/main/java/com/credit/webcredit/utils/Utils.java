package com.credit.webcredit.utils;

import java.io.*;
import java.nio.file.*;

import com.credit.webcredit.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public class Utils {

    public static void saveFile(String uploadDir, String fileName,
                                  MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public static String getUserName(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal instanceof MyUserDetails ? ((MyUserDetails) principal).getUsername() : principal.toString();
    }

}