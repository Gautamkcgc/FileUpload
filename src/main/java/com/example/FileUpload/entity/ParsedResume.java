//package com.example.FileUpload.entity;
//
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "parsed_resumes")
//public class ParsedResume {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long resume_id;
//
//    @Column(name = "name")
//    private String fullName;
//
//    @Column(name = "email")
//    private String email;
//
//    @Column(name = "phone")
//    private String phone;
//
//    @Column(name = "gender")
//    private String gender;
//
////    @Column(name = "experience")
////    private String experience;
////
////    @Column(name = "education")
////    private String education;
//
//    // Getters and setters
//
//
//    public Long getId() {
//        return resume_id;
//    }
//
//    public void setId(Long id) {
//        this.resume_id = id;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String name) {
//        this.fullName = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String skills) {
//        this.gender = gender;
//    }
//
//    public void setEducation(List<Education> educationList) {
//    }
//
//    public void setExperience(List<Experience> experienceList) {
//    }
//
//    public void setSkills(List<Skills> skillList) {
//    }
//
////    public String getExperience() {
////        return experience;
////    }
////
////    public void setExperience(String experience) {
////        this.experience = experience;
////    }
////
////    public String getEducation() {
////        return education;
////    }
////
////    public void setEducation(String education) {
////        this.education = education;
////    }
//
//    // One-to-many relationships
//    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Education> educationList;
//
//    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Experience> experienceList;
//
//    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Skills> skillsList;
//
//
//}



package com.example.FileUpload.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "parsed_resumes")
public class ParsedResume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educationList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experienceList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skills> skillList;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Education> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<Experience> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experience> experienceList) {
        this.experienceList = experienceList;
    }

    public List<Skills> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skills> skillList) {
        this.skillList = skillList;
    }



}
