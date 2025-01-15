package com.example.FileUpload.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "experience")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String experience;
    private String segregatedExperience;
    private String currentEmployer;
    private String jobProfile;
   // private String jobDescription;
    private String totalExperienceInMonths;
    private String totalExperienceInYear;
    private String totalExperienceRange;



    @ManyToOne
    @JoinColumn(name = "resume_id")  // Foreign key to parsed_resumes table
    private ParsedResume resume;
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParsedResume getResume() {
        return resume;
    }

    public void setResume(ParsedResume resume) {
        this.resume = resume;
    }


    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSegregatedExperience() {
        return segregatedExperience;
    }

    public void setSegregatedExperience(String segregatedExperience) {
        this.segregatedExperience = segregatedExperience;
    }

    public String getCurrentEmployer() {
        return currentEmployer;
    }

    public void setCurrentEmployer(String currentEmployer) {
        this.currentEmployer = currentEmployer;
    }

    public String getJobProfile() {
        return jobProfile;
    }

    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }

    public String getTotalExperienceInMonths() {
        return totalExperienceInMonths;
    }

    public void setTotalExperienceInMonths(String totalExperienceInMonths) {
        this.totalExperienceInMonths = totalExperienceInMonths;
    }

    public String getTotalExperienceInYear() {
        return totalExperienceInYear;
    }

    public void setTotalExperienceInYear(String totalExperienceInYear) {
        this.totalExperienceInYear = totalExperienceInYear;
    }

    public String getTotalExperienceRange() {
        return totalExperienceRange;
    }

    public void setTotalExperienceRange(String totalExperienceRange) {
        this.totalExperienceRange = totalExperienceRange;
    }
}

