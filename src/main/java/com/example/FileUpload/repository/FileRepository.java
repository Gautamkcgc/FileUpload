package com.example.FileUpload.repository;



import com.example.FileUpload.entity.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, Long> {
}

