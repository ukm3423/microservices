package com.productservice.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;

@RestController
public class FileUploadController {

    @Value("${backend.url}")
    private String backendUrl;

    @Value("${image.upload.directory}")
    private String uploadDirectory;

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Generate a unique filename for the image
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Define the directory where images will be stored
            Path uploadDir = Paths.get(uploadDirectory);
            Path filePath = uploadDir.resolve(filename).normalize();

            // Create the directory if it doesn't exist
            Files.createDirectories(uploadDir);

            // Save the file
            try (OutputStream outputStream = new FileOutputStream(filePath.toFile())) {
                outputStream.write(file.getBytes());
            }

            // Construct the image URI
            String imageUrl = backendUrl + "/images/" + filename;

            return ResponseEntity.ok().body("Image uploaded successfully. URI: " + imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to upload image.");
        }
    }

  

}
