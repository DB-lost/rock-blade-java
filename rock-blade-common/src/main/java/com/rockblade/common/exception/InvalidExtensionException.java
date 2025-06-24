/*
 * @Author: DB 2502523450@qq.com
 * @Date: 2025-01-15 23:30:43
 * @LastEditors: DB 2502523450@qq.com
 * @LastEditTime: 2025-01-16 09:03:24
 * @FilePath: /rock-blade-java/src/main/java/com/rockblade/framework/core/base/exception/InvalidExtensionException.java
 * @Description: 文件上传 误异常类
 *
 * Copyright (c) 2025 by RockBlade, All Rights Reserved.
 */
package com.rockblade.framework.core.base.exception;

import java.io.Serial;
import java.util.Arrays;

import org.apache.tomcat.util.http.fileupload.FileUploadException;

import lombok.Getter;

@Getter
public class InvalidExtensionException extends FileUploadException {

  @Serial private static final long serialVersionUID = 1L;

  /** 允许扩展 */
  private final String[] allowedExtension;

  /** 扩展 */
  private final String extension;

  /** 文件名 */
  private final String filename;

  /**
   * 无效扩展异常
   *
   * @param allowedExtension 允许扩展
   * @param extension 扩展
   * @param filename 文件名
   * @author DB
   * @since 2024/05/23
   */
  public InvalidExtensionException(String[] allowedExtension, String extension, String filename) {
    super(
        "文件["
            + filename
            + "]后缀["
            + extension
            + "]不正确，请上传"
            + Arrays.toString(allowedExtension)
            + "格式");
    this.allowedExtension = allowedExtension;
    this.extension = extension;
    this.filename = filename;
  }

  /**
   * 无效图像扩展异常
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  public static class InvalidImageExtensionException extends InvalidExtensionException {
    @Serial private static final long serialVersionUID = 1L;

    public InvalidImageExtensionException(
        String[] allowedExtension, String extension, String filename) {
      super(allowedExtension, extension, filename);
    }
  }

  /**
   * 无效flash扩展异常
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  public static class InvalidFlashExtensionException extends InvalidExtensionException {
    @Serial private static final long serialVersionUID = 1L;

    public InvalidFlashExtensionException(
        String[] allowedExtension, String extension, String filename) {
      super(allowedExtension, extension, filename);
    }
  }

  /**
   * 无效媒体扩展异常
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  public static class InvalidMediaExtensionException extends InvalidExtensionException {
    @Serial private static final long serialVersionUID = 1L;

    public InvalidMediaExtensionException(
        String[] allowedExtension, String extension, String filename) {
      super(allowedExtension, extension, filename);
    }
  }

  /**
   * 无效视频扩展异常
   *
   * @author DB
   * @version 1.0.0
   * @since 2024/05/23
   */
  public static class InvalidVideoExtensionException extends InvalidExtensionException {
    @Serial private static final long serialVersionUID = 1L;

    public InvalidVideoExtensionException(
        String[] allowedExtension, String extension, String filename) {
      super(allowedExtension, extension, filename);
    }
  }
}
