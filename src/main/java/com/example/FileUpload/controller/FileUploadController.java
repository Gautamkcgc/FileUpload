package com.example.FileUpload.controller;



import com.example.FileUpload.service.FileProcessingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private Logger logger= LoggerFactory.getLogger(FileUploadController.class);
    private final FileProcessingService fileProcessingService;

    public FileUploadController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        try {
//            fileProcessingService.processFile(file);
//            return ResponseEntity.ok("File uploaded and processed successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Error processing file: " + e.getMessage());
//        }
//    }
//




    @PostMapping("/upload/resume")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            String parsedData = fileProcessingService.parseResume(file);
            return ResponseEntity.ok("Parsed Data: " + parsedData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing resume: " + e.getMessage());
        }
    }

    public ResponseEntity<?> multiupload(@RequestParam("file") MultipartFile[] file){
        this.logger.info("{} multiple file uploaded" , file.length);
        re

    }


}
