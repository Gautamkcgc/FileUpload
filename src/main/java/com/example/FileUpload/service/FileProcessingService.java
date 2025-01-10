package com.example.FileUpload.service;



import com.example.FileUpload.entity.*;
import com.example.FileUpload.repository.FileRepository;
import com.example.FileUpload.repository.ResumeParsedRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


@Service
public class FileProcessingService {

    private final FileRepository fileRepository;
   // private final ResumeParsedRepository resumeParsedRepository;

    private final ResumeParsedRepository resumeParsedRepository;

    public FileProcessingService(FileRepository fileRepository, ResumeParsedRepository resumeParsedRepository) {
        this.fileRepository = fileRepository;
        this.resumeParsedRepository=resumeParsedRepository;

    }

    public void processFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();

        if (originalFileName != null && originalFileName.endsWith(".zip")) {
            processZipFile(file);
        }

         else if (originalFileName != null && (originalFileName.endsWith(".pdf") || originalFileName.endsWith(".docx"))) {
            saveFile(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + originalFileName);
        }
    }



    private void processZipFile(MultipartFile zipFile) throws IOException {
        File tempZipFile = File.createTempFile("uploaded", ".zip");
        zipFile.transferTo(tempZipFile);

        try (ZipFile zip = new ZipFile(tempZipFile)) {
            Enumeration<? extends ZipEntry> entries = zip.entries();
            ExecutorService executor = Executors.newFixedThreadPool(10);

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                executor.submit(() -> {
                    try (InputStream stream = zip.getInputStream(entry)) {
                        // Process file
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            executor.shutdown();
        }
    }


//
//    public String parseResume(MultipartFile file) throws IOException {
//        String base64Data = Base64.getEncoder().encodeToString(file.getBytes());
//        RestTemplate restTemplate = new RestTemplate();
//        String apiUrl = "http://192.168.1.101:8080/RChilliParser9/Rchilli/parseResumeBinary";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> requestPayload = new HashMap<>();
//        requestPayload.put("filedata", base64Data);
//        requestPayload.put("filename", file.getOriginalFilename());
//        requestPayload.put("userkey", "0001111");
//        requestPayload.put("version", "8.0.0");
//        requestPayload.put("subuserid", "Gautam");
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
//
//        return response.getBody();
//    }
//
//    public void processFile1(MultipartFile file) throws IOException {
//        String parsedData = parseResume(file); // Call the parser
//
//
//        String base64Data = Base64.getEncoder().encodeToString(file.getBytes());
//        RestTemplate restTemplate = new RestTemplate();
//        String apiUrl = "http://192.168.1.101:8080/RChilliParser9/Rchilli/parseResumeBinary";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> requestPayload = new HashMap<>();
//        requestPayload.put("filedata", base64Data);
//        requestPayload.put("filename", file.getOriginalFilename());
//        requestPayload.put("userkey", "0001111");
//        requestPayload.put("version", "8.0.0");
//        requestPayload.put("subuserid", "Gautam");
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
//
//        //return response.getBody();
//
//
//        // Parse the JSON response into an entity (ParsedResume)
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = objectMapper.readTree(parsedData);
//
//        ParsedResume parsedResume = new ParsedResume();
//        parsedResume.setName(rootNode.path("ResumeParserData").path("Name").asText());
//        parsedResume.setEmail(rootNode.path("ResumeParserData").path("Email").get(0).path("EmailAddress").asText());
//        parsedResume.setPhone(rootNode.path("ResumeParserData").path("PhoneNumber").get(0).path("Number").asText());
//        parsedResume.setSkills(rootNode.path("ResumeParserData").path("SkillKeywords").asText());
//        parsedResume.setExperience(rootNode.path("ResumeParserData").path("Experience").asText());
//        parsedResume.setEducation(rootNode.path("ResumeParserData").path("Qualification").asText());
//
//        // Save parsed data to the database
//        resumeParsedRepository.save(parsedResume);
//    }
//
//







//
//
//    public String parseResume(MultipartFile file) throws IOException {
//        String parsedData = parseResume(file);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = objectMapper.readTree(parsedData);
//
//        ParsedResume parsedResume = new ParsedResume();
//        JsonNode resumeData = rootNode.path("ResumeParserData");
//
//        // Personal Information
//        parsedResume.setFullName(resumeData.path("Name").path("FullName").asText());
//        parsedResume.setEmail(resumeData.path("Email").get(0).path("EmailAddress").asText());
//        parsedResume.setPhone(resumeData.path("PhoneNumber").get(0).path("FormattedNumber").asText());
//        parsedResume.setGender(resumeData.path("Gender").asText());
//
//
//        // Education
//        List<Education> educationList = new ArrayList<>();
//        for (JsonNode eduNode : resumeData.path("SegregatedQualification")) {
//            Education education = new Education();
//            education.setInstitutionName(eduNode.path("Institution").path("Name").asText());
//            education.setDegree(eduNode.path("Degree").path("DegreeName").asText());
//            education.setSpecialization(eduNode.path("Degree").path("Specialization").toString());
//            education.setStartDate(eduNode.path("StartDate").asText());
//            education.setEndDate(eduNode.path("EndDate").asText());
//            education.setAggregate(eduNode.path("Aggregate").path("Value").asText());
//            education.setResume(parsedResume);
//            educationList.add(education);
//        }
//        parsedResume.setEducation(educationList);
//
//        // Experience
//        List<Experience> experienceList = new ArrayList<>();
//        for (JsonNode expNode : resumeData.path("SegregatedExperience")) {
//            Experience experience = new Experience();
//            experience.setEmployerName(expNode.path("Employer").path("EmployerName").asText());
//            experience.setJobTitle(expNode.path("JobProfile").path("Title").asText());
//            experience.setJobDescription(expNode.path("JobDescription").asText());
//            experience.setStartDate(expNode.path("StartDate").asText());
//            experience.setEndDate(expNode.path("EndDate").asText());
//            experience.setResume(parsedResume);
//            experienceList.add(experience);
//        }
//        parsedResume.setExperience(experienceList);
//
//        // Skills
//        List<Skills> skillList = new ArrayList<>();
//        for (JsonNode skillNode : resumeData.path("SegregatedSkill")) {
//            Skills skill = new Skills();
//            skill.setSkillName(skillNode.path("Skill").asText());
//            skill.setSkillType(skillNode.path("Type").asText());
//            skill.setResume(parsedResume);
//            skillList.add(skill);
//        }
//        parsedResume.setSkills(skillList);
//
//        // Save to database
//        resumeParsedRepository.save(parsedResume);
//        return parsedData;
//    }
//






    public String callResumeParserApi(MultipartFile file) throws IOException {

        String base64Data = Base64.getEncoder().encodeToString(file.getBytes());
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://192.168.1.101:8080/RChilliParser9/Rchilli/parseResumeBinary";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("filedata", base64Data);
        requestPayload.put("filename", file.getOriginalFilename());
        requestPayload.put("userkey", "0001111");
        requestPayload.put("version", "8.0.0");
        requestPayload.put("subuserid", "Gautam");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to parse resume: " + response.getStatusCode());
        }

        return response.getBody();
    }

    public String parseResume(MultipartFile file) throws IOException {
        // Step 1: Call the parser API
        String parsedData = callResumeParserApi(file);

        // Step 2: Parse the JSON response into an entity (ParsedResume)
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(parsedData);

        ParsedResume parsedResume = new ParsedResume();
        JsonNode resumeData = rootNode.path("ResumeParserData");

        // Extract fields from the response and map to the entity

        parsedResume.setFullName(resumeData.path("Name").path("FullName").asText());
        parsedResume.setEmail(resumeData.path("Email").get(0).path("EmailAddress").asText());
        parsedResume.setPhone(resumeData.path("PhoneNumber").get(0).path("FormattedNumber").asText());
        parsedResume.setGender(resumeData.path("Gender").asText());

        // Education
        List<Education> educationList = new ArrayList<>();
        for (JsonNode eduNode : resumeData.path("SegregatedQualification")) {
            Education education = new Education();
            education.setInstitutionName(eduNode.path("Institution").path("Name").asText());
            education.setDegree(eduNode.path("Degree").path("DegreeName").asText());
            education.setSpecialization(eduNode.path("Degree").path("Specialization").asText());
            education.setStartDate(eduNode.path("StartDate").asText());
            education.setEndDate(eduNode.path("EndDate").asText());
            education.setAggregate(eduNode.path("Aggregate").path("Value").asText());
            education.setResume(parsedResume);
            educationList.add(education);
        }
        parsedResume.setEducationList(educationList);

        // Experience
        List<Experience> experienceList = new ArrayList<>();
        for (JsonNode expNode : resumeData.path("SegregatedExperience")) {
            Experience experience = new Experience();
            experience.setEmployerName(expNode.path("Employer").path("EmployerName").asText());
            experience.setJobTitle(expNode.path("JobProfile").path("Title").asText());
            experience.setJobDescription(expNode.path("JobDescription").asText());
            experience.setStartDate(expNode.path("StartDate").asText());
            experience.setEndDate(expNode.path("EndDate").asText());
            experience.setResume(parsedResume);
            experienceList.add(experience);
        }
        parsedResume.setExperienceList(experienceList);

        // Skills
        List<Skills> skillList = new ArrayList<>();
        for (JsonNode skillNode : resumeData.path("SegregatedSkill")) {
            Skills skill = new Skills();
            skill.setSkillName(skillNode.path("Skill").asText());
            skill.setSkillType(skillNode.path("Type").asText());
            skill.setResume(parsedResume);
            skillList.add(skill);
        }
        parsedResume.setSkillList(skillList);

        // Step 3: Save the parsed data to the database
        resumeParsedRepository.save(parsedResume);

        return parsedData;
    }

    private void saveFile(MultipartFile file) throws IOException {
        UploadedFile uploadedFile = new UploadedFile(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        fileRepository.save(uploadedFile);
    }

//    private void saveFile(File file, String fileName) throws IOException {
//        UploadedFile uploadedFile = new UploadedFile(
//                fileName,
//                Files.probeContentType(file.toPath()),
//                Files.readAllBytes(file.toPath())
//        );
//        fileRepository.save(uploadedFile);
//    }
}
