package com.example.FileUpload.controller;



import com.example.FileUpload.entity.ParsedResume;
import com.example.FileUpload.entity.Skills;
import com.example.FileUpload.repository.ResumeParsedRepository;
import com.example.FileUpload.service.FileProcessingService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileProcessingService fileProcessingService;
    private final ResumeParsedRepository resumeParsedRepository;


    public FileUploadController(FileProcessingService fileProcessingService, ResumeParsedRepository resumeParsedRepository) {
        this.fileProcessingService = fileProcessingService;

        this.resumeParsedRepository=resumeParsedRepository;
    }




    @PostMapping("/upload/resume")
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            String parsedData = fileProcessingService.parseResume(file);
            System.out.println(parsedData);
            return ResponseEntity.ok("Parsed Data: " + parsedData);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error parsing resume: " + e.getMessage());
        }
    }





    // Read: Retrieve records by ID
    @GetMapping("/{id}")
    public ResponseEntity<ParsedResume> getResumeById(@PathVariable Long id) {
        Optional<ParsedResume> resume = resumeParsedRepository.findById(id);
        return resume.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Read: Retrieve records using filters
    @GetMapping
    public ResponseEntity<List<ParsedResume>> getResumesByFilters(@RequestParam(required = false) String skill,
                                                                  @RequestParam(required = false) String location,
                                                                  @RequestParam(required = false) Integer experienceMin,
                                                                  @RequestParam(required = false) Integer experienceMax) {
        // Placeholder logic - Implement filtering logic in repository or service layer
        List<ParsedResume> resumes = resumeParsedRepository.findAll(); // Replace with filtered query
        return ResponseEntity.ok(resumes);
    }

    // Update: Modify existing records
    @PutMapping("/{id}")
    public ResponseEntity<ParsedResume> updateResume(@PathVariable Long id, @RequestBody ParsedResume updatedResume) {
        Optional<ParsedResume> existingResumeOpt = resumeParsedRepository.findById(id);
        if (existingResumeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ParsedResume existingResume = existingResumeOpt.get();
        existingResume.setFullName(updatedResume.getFullName());
        existingResume.setEmail(updatedResume.getEmail());
        existingResume.setPhone(updatedResume.getPhone());
        existingResume.setGender(updatedResume.getGender());
        existingResume.setEducationList(updatedResume.getEducationList());
        existingResume.setExperienceList(updatedResume.getExperienceList());
        existingResume.setSkillList(updatedResume.getSkillList());

        ParsedResume savedResume = resumeParsedRepository.save(existingResume);
        return ResponseEntity.ok(savedResume);
    }

    // Delete: Remove specific records
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Long id) {
        if (!resumeParsedRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resume not found");
        }

        resumeParsedRepository.deleteById(id);
        return ResponseEntity.ok("Resume deleted successfully");
    }

    // Search: For Candidates (skills, location, experience range, education level)
//    @GetMapping("/search")
//    public ResponseEntity<List<Skills>> searchResumes(@RequestParam(required = false) String skill,
//                                                      @RequestParam(required = false) String location,
//                                                      @RequestParam(required = false) Integer experienceMin,
//                                                      @RequestParam(required = false) Integer experienceMax,
//                                                      @RequestParam(required = false) String educationLevel) {
//        // Placeholder logic - Implement advanced search in repository or service layer
//        List<Skills> results = skillsRepository.findBySkills(); // Replace with filtered query
//        return ResponseEntity.ok(results);
//    }

}
