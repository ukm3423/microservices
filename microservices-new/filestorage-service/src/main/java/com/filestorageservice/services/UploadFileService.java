package com.filestorageservice.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.filestorageservice.models.UploadFile;
import com.filestorageservice.repository.UploadFileRepository;

@Service
public class UploadFileService {

    @Autowired
    private UploadFileRepository fileRepo;

    // private final String FILE_PATH = "/files/";
    @Value("${file.upload-dir}")
    private String FILE_PATH ;

    public String uploadImageToFileSystem(MultipartFile file) throws IllegalStateException, IOException {
        
        File f = new File(FILE_PATH);
        if(!f.exists()){
            f.mkdir();
            System.out.println("\nFolder Created : " + f.getAbsolutePath());
        }else{
            System.out.println("\nFolder not created : " + f.getAbsolutePath());
        }

        String filePath = FILE_PATH + file.getOriginalFilename();
        UploadFile fileData = UploadFile.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .filePath(filePath)
                .build();
        // file.transferTo(new File(filePath));
        Files.copy(file.getInputStream(), Paths.get(filePath));
        fileRepo.save(fileData);
        if(fileData != null){
            return "File Uploaded Successfully : "+filePath;
        }
        return null;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException{
        Optional<UploadFile> fileData = fileRepo.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] image = Files.readAllBytes(new File(filePath).toPath());
        return image;
    }

}
