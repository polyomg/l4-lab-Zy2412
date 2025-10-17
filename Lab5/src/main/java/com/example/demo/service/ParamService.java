package com.example.demo.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ResourceLoader;

@Service
public class ParamService {
    @Autowired
    HttpServletRequest request;

    @Autowired
    ResourceLoader resourceLoader;

    public String getString(String name, String defaultValue) {
        String value = request.getParameter(name);
        return value != null ? value : defaultValue;
    }

    public int getInt(String name, int defaultValue) {
        try {
            return Integer.parseInt(getString(name, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getDouble(String name, double defaultValue) {
        try {
            return Double.parseDouble(getString(name, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        return Boolean.parseBoolean(getString(name, String.valueOf(defaultValue)));
    }

    public Date getDate(String name, String pattern) {
        try {
            String dateStr = getString(name, "");
            if (dateStr.isEmpty()) {
                return null;
            }
            return new SimpleDateFormat(pattern).parse(dateStr);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing date: " + e.getMessage(), e);
        }
    }

    public File save(MultipartFile file, String path) {
        if (file.isEmpty()) {
            return null;
        }
        try {
            // Get the absolute path to the static directory
            String staticPath = resourceLoader.getResource("classpath:static").getFile().getAbsolutePath();
            Path uploadDir = Paths.get(staticPath, path);
            
            // Create the directory if it doesn't exist
            File dir = uploadDir.toFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File savedFile = new File(dir, file.getOriginalFilename());
            file.transferTo(savedFile);
            return savedFile;
        } catch (Exception e) {
            throw new RuntimeException("Error saving file: " + e.getMessage(), e);
        }
    }
}