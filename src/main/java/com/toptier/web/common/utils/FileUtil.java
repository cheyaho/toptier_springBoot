package com.toptier.web.common.utils;

import com.toptier.web.dto.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class FileUtil {

    public static String getExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static String generateUniqueFileName(String originalFileName) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHssSSS"));
        String fileNameWithoutExt = originalFileName.substring(0, originalFileName.lastIndexOf("."));
        return fileNameWithoutExt + "_" + timeStamp + getExtension(originalFileName);
    }

    public static void makeDirectory(String path) {
        java.io.File dir = new java.io.File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 파일 업로드
     * @param uploadFile, path
     * @param path
     * @return
     * @throws Exception
     */
    public static FileUploadResponse fileUploader(MultipartFile uploadFile, String path) throws Exception {
        if (uploadFile != null && !uploadFile.isEmpty()) {
            String originalFileName = uploadFile.getOriginalFilename();
            String savedFileName = generateUniqueFileName(originalFileName);

            // 파일 저장 경로 설정
            String uploadPath = "/Volumes/MacData/Study-JAVA/toptier/src/main/resources/static" + path;
            log.info("uploadPath : {}", uploadPath);

            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            File destFile = new File(uploadPath + savedFileName);
            uploadFile.transferTo(destFile);

            String fileUrl = path +savedFileName;

            return FileUploadResponse.success(fileUrl, originalFileName);
        } else {
            return FileUploadResponse.failure("파일 업로드에 실패했습니다.");
        }
    }

    public List<FileUploadResponse> multiFilesUploader(MultipartFile[] files, String path) {
        List<FileUploadResponse> responses = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                FileUploadResponse response = fileUploader(file, path);
                if (!response.uploaded()) {
                    throw new RuntimeException("파일 업로드에 실패했습니다.");
                }
                responses.add(response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return responses;
    }
}
