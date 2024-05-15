package com.filestorageservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filestorageservice.models.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long>{
    
    Optional<UploadFile> findByName(String name);

}
