package com.filestorageservice.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import com.filestorageservice.services.UploadFileService;

@RestController
public class UploadFileController {
    
    @Autowired
    private UploadFileService fileService;

    @Value("${file.upload-dir}")
    private String FILE_PATH ;


    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException{
        String uploadImage = fileService.uploadImageToFileSystem(file);

        return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) throws IOException{
        byte[] imageData = fileService.downloadImageFromFileSystem(fileName);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpg")).body(imageData);
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws MalformedURLException {
        // Resolve the path to the image
        Path imagePath = Paths.get(FILE_PATH + imageName);
        
        // Load the image as a resource
        Resource resource = new UrlResource(imagePath.toUri());
        
        // Check if the resource exists and is readable
        if (resource.exists() && resource.isReadable()) {
            // Return the image as a ResponseEntity with the appropriate headers
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust the content type as per your image format
                    .body(resource);
        } else {
            // Return a 404 Not Found response if the image is not found
            return ResponseEntity.notFound().build();
        }
    }

}
