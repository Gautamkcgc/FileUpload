package com.example.FileUpload.entity;
//import com.example.FileUpload.entity.JDUpload;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jobDescription")
public class JobDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String requiredSkills;
    private String experience;



    @OneToMany(mappedBy = "jd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperienceJD> experienceList;

    @OneToMany(mappedBy = "jd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RequiredSkill> skillList;

    @OneToMany(mappedBy = "jd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobLocation> locationList;







    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }





















    public List<RequiredSkill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<RequiredSkill> skillList) {
        this.skillList = skillList;
    }

    public List<ExperienceJD> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<ExperienceJD> experienceList) {
        this.experienceList = experienceList;
    }

    public List<JobLocation> getLocationList(){
        return locationList;
    }

    public void setLocationList(List<JobLocation> locationList){
        this.locationList=locationList;
    }



}