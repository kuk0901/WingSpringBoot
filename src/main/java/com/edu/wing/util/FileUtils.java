package com.edu.wing.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component("fileUtils")
public class FileUtils {
  private static final String FILE_PATH; // 운영체제에 맞춰 경로 설정
  private static final String DIRECTORY_NAME = "wing_files"; // 디렉토리 명
  private static final String DEFAULT_EXTENSION = ".png"; // 확장자 없는 경우 사용

  static {
    String os = System.getProperty("os.name").toLowerCase();
    String userHome = System.getProperty("user.home");

    if (os.contains("win")) {
      FILE_PATH = "C:" + File.separator + DIRECTORY_NAME;
    } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
      FILE_PATH = userHome + File.separator + DIRECTORY_NAME;
    } else {
      FILE_PATH = userHome + File.separator + DIRECTORY_NAME;
    }

    createDirectory();
  }

  private static void createDirectory() {
    File directory = new File(FILE_PATH);
    if (!directory.exists()) {
      boolean created = directory.mkdirs();
      if (created) {
        System.out.println("디렉토리가 성공적으로 생성되었습니다: " + FILE_PATH);
      } else {
        System.err.println("디렉토리 생성 실패: " + FILE_PATH);
      }
    } else {
      System.out.println("디렉토리가 이미 존재합니다: " + FILE_PATH);
    }
  }

  public static String getFilePath() {
    return FILE_PATH;
  }

  public Map<String, String> parseInsertFileInfo(MultipartFile cardImg) throws IOException {
    Map<String, String> fileInfo = new HashMap<>();

    if (cardImg != null && !cardImg.isEmpty()) {
      String originalFileName = cardImg.getOriginalFilename();
      String storedFileName = generateUniqueFileName(originalFileName);

      File file = new File(FILE_PATH, storedFileName);
      cardImg.transferTo(file);

      fileInfo.put("originalFileName", originalFileName);
      fileInfo.put("storedFileName", storedFileName);
    }

    return fileInfo;
  }

  private String generateUniqueFileName(String originalFileName) {
    String baseName = CommonUtils.getRandomString();
    String extension = getFileExtension(originalFileName);
    return baseName + extension;
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || fileName.isEmpty()) {
      return DEFAULT_EXTENSION;
    }

    int lastIndexOfDot = fileName.lastIndexOf(".");
    if (lastIndexOfDot == -1) {
      return DEFAULT_EXTENSION;
    }

    String extension = fileName.substring(lastIndexOfDot).toLowerCase();

    if (extension.equals(".jpg") || extension.equals(".jpeg") || extension.equals(".png")) {
      return extension;
    }

    return DEFAULT_EXTENSION;
  }
}