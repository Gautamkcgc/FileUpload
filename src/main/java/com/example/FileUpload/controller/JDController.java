package com.example.FileUpload.controller;

import com.example.FileUpload.service.FileProcessingService;
import com.example.FileUpload.service.JDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class JDController {

        private final JDService jdService;

        @Autowired
        public JDController(JDService jdService) {
            this.jdService = jdService;
        }

        @PostMapping("/upload/jd")
        public ResponseEntity<String> uploadJobDescription(@RequestParam("file") MultipartFile file) {
            try {
                String jdUpload1 = jdService.callJDApi(file);
                     jdService.parseJDUpload(jdUpload1);
                System.out.println(jdUpload1);

                return ResponseEntity.ok("Parsed Data: " + jdUpload1);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error parsing job description: " + e.getMessage());
            }
        }



    }







