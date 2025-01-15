package com.example.FileUpload.service;
//
//
//
////import org.apache.commons.io.FilenameUtils;
import com.example.FileUpload.entity.*;
import com.example.FileUpload.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
//import .springframework.web.client.RestTemplate;
        import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
        import java.time.LocalDateTime;
import java.util.*;
        import java.util.concurrent.*;
        import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
@Service
public class JDService {
private final FileRepository fileRepository;
 private final JDUploadRepository jdRepository;
////
////        public JDProcessingService(FileRepository fileRepository, JDUploadRepository jdRepository) {
////            //this.fileRepository = fileRepository;
////            this.jdRepository = jdRepository;
////        }

    public JDService(FileRepository fileRepository,JDUploadRepository jdRepository) {
        this.fileRepository = fileRepository;
        this.jdRepository = jdRepository;
    }




//    public static void processFile1(MultipartFile file) throws IOException {
//        String originalFileName = file.getOriginalFilename();
//
//        if (originalFileName != null && originalFileName.endsWith(".zip")) {
//            processZipFile(file);
//        }
//
//        else if (originalFileName != null && (originalFileName.endsWith(".pdf") || originalFileName.endsWith(".docx"))) {
//            saveFile(file);
//        } else {
//            throw new IllegalArgumentException("Unsupported file type: " + originalFileName);
//        }
//    }

//    private void saveFile(MultipartFile file) throws IOException {
//        UploadedFile uploadedFile = new UploadedFile(
//                file.getOriginalFilename(),
//                file.getContentType(),
//                file.getBytes()
//        );
//        JDUploadRepository.save(uploadedFile);
//    }



//    public static void processZipFile(MultipartFile zipFile) throws IOException {
//        File tempZipFile = File.createTempFile("uploaded", ".zip");
//        zipFile.transferTo(tempZipFile);
//
//        try (ZipFile zip = new ZipFile(tempZipFile)) {
//            Enumeration<? extends ZipEntry> entries = zip.entries();
//            ExecutorService executor = Executors.newFixedThreadPool(10);
//
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = entries.nextElement();
//                executor.submit(() -> {
//                    try (InputStream stream = zip.getInputStream(entry)) {
//                        // Process file
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//            }
//            executor.shutdown();
//        }
//    }





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



    public void processZipFile(MultipartFile zipFile) throws IOException {
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


    private void saveFile(MultipartFile file) throws IOException {
        UploadedFile uploadedFile = new UploadedFile(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes()
        );
        fileRepository.save(uploadedFile);
    }




    public String callJDApi(MultipartFile file) throws IOException {

        String base64Data = Base64.getEncoder().encodeToString(file.getBytes());
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://192.168.1.101:8080/JDParser3/RChilli/ParseJD";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestPayload = new HashMap<>();
        requestPayload.put("filedata", base64Data);
        requestPayload.put("filename", file.getOriginalFilename());
        requestPayload.put("userkey", "0001111");
        requestPayload.put("version", "3.0");
        requestPayload.put("subuserid", "gautam");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestPayload, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new RuntimeException("Failed to parse JD: " + response.getStatusCode());
        }

        return response.getBody();
    }

    public  void parseJDUpload(String json) throws IOException {
        // Step 1: Call the JD parser API





        // Step 2: Parse the JSON response into an entity (JDUpload)
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(json);

        JobDescription jdUpload = new JobDescription();

        JsonNode jdData = rootNode.path("JDParsedData");

        if (jdData.isMissingNode() || jdData.isNull()) {
            throw new RuntimeException("ParseJD data is missing in the API response.");
        }
//
//        String fullName = jdData.path("Name").path("FullName").asText(null);
//        if (fullName == null) {
//            throw new RuntimeException("FullName is missing in the response.");
//        }


     //    Extract fields from the response and map to the entity

        jdUpload.setJobTitle(jdData.path("JobProfile").path("Title").asText());
        jdUpload.setRequiredSkills(jdData.path("Skills").path("Preferred").get(0).path("Skill").asText());
      //  jdUpload.setExperience(jdData.path("ExperienceRequired").get(0).path("MinimumYearsExperience").asText());
    //    jdUpload.setGender(resumeData.path("Gender").asText());

        ExperienceJD ejd=new ExperienceJD();
        List<ExperienceJD> exp = new ArrayList<>();
        ejd.setMinimumYearExperience(jdData.path("ExperienceRequired").path("MinimumYearsExperience").asText());
        ejd.setMaximumYearExperience(jdData.path("ExperienceRequired").path("MaximumYearsExperience").asText());
        ejd.setJd(jdUpload);
        jdUpload.setExperienceList(exp);
        jdUpload.getExperienceList().add(ejd);

        List<RequiredSkill> requiredSkillList = new ArrayList<>();
        jdUpload.setSkillList(requiredSkillList);
        for(JsonNode requiredSkillNode : jdData.path("Skills").path("Required")){
            RequiredSkill skill=new RequiredSkill();
            skill.setSkillName(requiredSkillNode.path("Skill").asText());
            skill.setJd(jdUpload);
            jdUpload.getSkillList().add(skill);

        }

        JobLocation jobLocation=new JobLocation();
        List<JobLocation> jobLocationList=new ArrayList<>();
        jobLocation.setLocation(jdData.path("JobLocation").path("Location").asText());
        jobLocation.setCity(jdData.path("JobLocation").path("City").asText());
        jobLocation.setCountryCode(jdData.path("JobLocation").path("IsoCountryCode").asText());
        jobLocation.setCountry(jdData.path("JobLocation").path("Country").asText());
        jobLocation.setJd(jdUpload);
        jdUpload.setLocationList(jobLocationList);
        jdUpload.getLocationList().add(jobLocation);



//         jdUpload.getSkillList().add(skill);
        // Step 3: Save the parsed data to the database
        jdRepository.save(jdUpload);


System.out.println(jdData.path("JobProfile").path("Title").asText());



    }


    }


