package com.rockblade.domain.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.rockblade.domain.system.dto.request.LogSearchRequest;
import com.rockblade.domain.system.dto.response.LogFileInfoResponse;
import com.rockblade.domain.system.service.LogService;
import com.rockblade.framework.core.base.entity.PageDomain;
import com.rockblade.framework.utils.SqlUtils;

import cn.hutool.core.util.StrUtil;

@Service
public class LogServiceImpl implements LogService {

  private static final String LOG_PATH = "./logs/rock-blade-java";
  private static final String[] LOG_TYPES = {"info", "error", "sql"};

  @Override
  public List<LogFileInfoResponse> getLogFiles(String logType) {
    List<LogFileInfoResponse> logFiles = new ArrayList<>();
    try {
      File logDir = new File(LOG_PATH);
      if (!logDir.exists() || !logDir.isDirectory()) {
        return logFiles;
      }

      Stream<Path> walk = Files.walk(logDir.toPath(), 1);
      logFiles =
          walk.filter(Files::isRegularFile)
              .filter(
                  path -> {
                    if (StrUtil.isNotEmpty(logType)) {
                      return path.getFileName().toString().contains(logType);
                    }
                    return true;
                  })
              .map(
                  path -> {
                    LogFileInfoResponse info = new LogFileInfoResponse();
                    File file = path.toFile();
                    info.setFileName(file.getName());
                    info.setFilePath(file.getPath());
                    info.setFileSize(file.length());
                    info.setLastModified(
                        LocalDateTime.ofInstant(
                            java.time.Instant.ofEpochMilli(file.lastModified()),
                            ZoneId.systemDefault()));
                    for (String type : LOG_TYPES) {
                      if (file.getName().contains(type)) {
                        info.setLogType(type.toUpperCase());
                        break;
                      }
                    }
                    return info;
                  })
              .collect(Collectors.toList());
      walk.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return logFiles;
  }

  @Override
  public Resource downloadLogFile(String fileName) {
    try {
      Path filePath = Paths.get(LOG_PATH, fileName);
      File file = filePath.toFile();
      if (file.exists() && file.isFile()) {
        return new FileSystemResource(file);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String searchLogContent(LogSearchRequest request) {
    if (request == null || !StrUtil.isNotEmpty(request.getLogType())) {
      return "请指定日志类型";
    }

    StringBuilder result = new StringBuilder();
    try {
      String logFileName = request.getLogType().toLowerCase() + ".log";
      Path logFile = Paths.get(LOG_PATH, logFileName);

      if (!Files.exists(logFile)) {
        return "日志文件不存在";
      }

      List<String> lines = Files.readAllLines(logFile);

      PageDomain pageDomain = SqlUtils.getInstance().getPageDomain();
      int startIndex = (pageDomain.getPage() - 1) * pageDomain.getPageSize();
      int endIndex = Math.min(startIndex + pageDomain.getPageSize(), lines.size());

      // 如果有关键字，进行过滤
      if (StrUtil.isNotEmpty(request.getKeyword())) {
        lines =
            lines.stream()
                .filter(line -> line.contains(request.getKeyword()))
                .collect(Collectors.toList());
      }

      // 时间过滤待实现

      // 分页处理
      if (startIndex < lines.size()) {
        lines.subList(startIndex, endIndex).forEach(line -> result.append(line).append("\n"));
      }

    } catch (IOException e) {
      e.printStackTrace();
      return "读取日志文件失败";
    }

    return result.toString();
  }

  @Override
  public boolean cleanupLogs(int days) {
    try {
      File logDir = new File(LOG_PATH);
      if (!logDir.exists() || !logDir.isDirectory()) {
        return false;
      }

      long cutoffTime = System.currentTimeMillis() - (days * 24L * 60L * 60L * 1000L);

      File[] files = logDir.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isFile() && file.lastModified() < cutoffTime) {
            file.delete();
          }
        }
      }
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
