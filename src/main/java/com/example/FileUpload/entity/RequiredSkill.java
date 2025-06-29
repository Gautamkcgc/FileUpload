package com.example.FileUpload.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "requiredSkills")
public class RequiredSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public JobDescription getJd() {
        return jd;
    }


    public void setJd(JobDescription jd) {
        this.jd = jd;
    }
//    @ManyToOne
//    @JoinColumn(name = "job_description_id", nullable = false)
//    private JobDescription jobDescription;


    @ManyToOne
    @JoinColumn(name = "jd_id")
    private JobDescription jd;
}


