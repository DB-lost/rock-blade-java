/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-16 19:56:48
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 20:12:17
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/handler/UploadHandler.java
 * @Description: 上传文件工具类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.rockblade.framework.config.RockBladeConfig;
import com.rockblade.framework.config.ServerConfig;
import com.rockblade.framework.core.base.exception.InvalidExtensionException;
import com.rockblade.framework.core.base.exception.UtilException;
import com.rockblade.framework.core.constants.Constants;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 上传文件工具类
 *
 * @author DB
 * @version 1.1.0
 * @since 2024-05-23 11:49
 */
@Component
public class UploadHandler {

  @Autowired private RockBladeConfig rockBladeConfig;

  @Autowired private ServerConfig serverConfig;

  /**
   * 上传文件
   *
   * @param file 文件
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public String uploadFile(MultipartFile file) {
    // 判断文件名长度
    int fileNameLength = Objects.requireNonNull(file.getOriginalFilename()).length();
    if (fileNameLength > Constants.DEFAULT_FILE_NAME_LENGTH) {
      throw new UtilException("文件名过长");
    }
    // 文件大小校验
    try {
      assertAllowed(file, Constants.DEFAULT_ALLOWED_EXTENSION);
    } catch (FileSizeLimitExceededException | InvalidExtensionException e) {
      throw new UtilException(e);
    }
    // 编码文件名
    String fileName = extractFilename(file);
    // 获取绝对文件
    String absPath = getAbsoluteFile(rockBladeConfig.getUploadPath(), fileName).getAbsolutePath();
    try {
      // 文件传输
      file.transferTo(Paths.get(absPath));
    } catch (IOException e) {
      throw new UtilException(e);
    }
    // 上传并返回新文件名称
    String pathFileName = getPathFileName(rockBladeConfig.getUploadPath(), fileName);
    // 强制将http变成https
    // String https = serverConfig.getUrl().replace("http:", "https:");
    // String url = https + fileName;
    return serverConfig.getUrl() + pathFileName;
  }

  /**
   * 获取文件名称
   *
   * @param fileName 路径名称
   * @return 没有文件路径的名称
   * @author DB
   * @since 2024/05/23
   */
  public String getName(String fileName) {
    if (fileName == null) {
      return null;
    }
    int lastUnixPos = fileName.lastIndexOf('/');
    int lastWindowsPos = fileName.lastIndexOf('\\');
    int index = Math.max(lastUnixPos, lastWindowsPos);
    return fileName.substring(index + 1);
  }

  /**
   * 获取路径文件名
   *
   * @param uploadDir 上传dir
   * @param fileName 文件名称
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  private String getPathFileName(String uploadDir, String fileName) {
    int dirLastIndex = rockBladeConfig.getProfile().length() + 1;
    String currentDir = StrUtil.sub(uploadDir, 0, dirLastIndex);
    return Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
  }

  /**
   * 编码文件名
   *
   * @param file 文件
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  public final String extractFilename(MultipartFile file) {
    return StrUtil.format(
        "{}/{}_{}.{}",
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
        file.getOriginalFilename(),
        IdUtil.getSnowflakeNextId(),
        getExtension(file));
  }

  /**
   * 获取绝对文件
   *
   * @param uploadDir 上传dir
   * @param fileName 文件名称
   * @return {@link File }
   * @author DB
   * @since 2024/05/23
   */
  public final File getAbsoluteFile(String uploadDir, String fileName) {
    File desc = new File(uploadDir + File.separator + fileName);
    if (!desc.exists()) {
      if (!desc.getParentFile().exists()) {
        desc.getParentFile().mkdirs();
      }
    }
    return desc;
  }

  /**
   * 文件大小校验
   *
   * @param file 上传的文件
   * @param allowedExtension 允许扩展
   * @throws FileSizeLimitExceededException 文件大小限制超出异常
   * @throws InvalidExtensionException 无效扩展异常
   * @author DB
   * @since 2024/05/23
   */
  private void assertAllowed(MultipartFile file, String[] allowedExtension)
      throws FileSizeLimitExceededException, InvalidExtensionException {
    long size = file.getSize();
    if (size > Constants.DEFAULT_MAX_SIZE) {
      throw new FileSizeLimitExceededException(
          "文件过大", size, Constants.DEFAULT_MAX_SIZE / 1024 / 1024);
    }
    String fileName = file.getOriginalFilename();
    String extension = getExtension(file);
    if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
      if (allowedExtension == Constants.IMAGE_EXTENSION) {
        throw new InvalidExtensionException.InvalidImageExtensionException(
            allowedExtension, extension, fileName);
      } else if (allowedExtension == Constants.FLASH_EXTENSION) {
        throw new InvalidExtensionException.InvalidFlashExtensionException(
            allowedExtension, extension, fileName);
      } else if (allowedExtension == Constants.MEDIA_EXTENSION) {
        throw new InvalidExtensionException.InvalidMediaExtensionException(
            allowedExtension, extension, fileName);
      } else if (allowedExtension == Constants.VIDEO_EXTENSION) {
        throw new InvalidExtensionException.InvalidVideoExtensionException(
            allowedExtension, extension, fileName);
      } else {
        throw new InvalidExtensionException(allowedExtension, extension, fileName);
      }
    }
  }

  /**
   * 获取文件名的后缀
   *
   * @param file 表单文件
   * @return 后缀名
   * @author DB
   * @since 2024/05/23
   */
  private String getExtension(MultipartFile file) {
    String extension = FileNameUtil.getSuffix(file.getOriginalFilename());
    if (StrUtil.isEmpty(extension)) {
      extension = getExtension(Objects.requireNonNull(file.getContentType()));
    }
    return extension;
  }

  /**
   * 允许扩展
   *
   * @param extension 扩展
   * @param allowedExtension 允许扩展
   * @return boolean
   * @author DB
   * @since 2024/05/23
   */
  public final boolean isAllowedExtension(String extension, String[] allowedExtension) {
    for (String str : allowedExtension) {
      if (str.equalsIgnoreCase(extension)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 得到扩展
   *
   * @param prefix 前缀
   * @return {@link String }
   * @author DB
   * @since 2024/05/23
   */
  private String getExtension(String prefix) {
    return switch (prefix) {
      case Constants.IMAGE_PNG -> "png";
      case Constants.IMAGE_JPG -> "jpg";
      case Constants.IMAGE_JPEG -> "jpeg";
      case Constants.IMAGE_BMP -> "bmp";
      case Constants.IMAGE_GIF -> "gif";
      default -> "";
    };
  }
}
