package com.productservice.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// http://192.168.1.173:9093/filestorage/api/upload-file

@FeignClient(name = "filestorage-service" , url = "http://192.168.1.173:9093/filestorage/api/")
public interface FileUploadClient {
    

    @GetMapping("/download/{fileName}")
    public ResponseEntity<?>downloadImage(@PathVariable String fileName);


}
