package com.example.FileUpload.entity;


import jakarta.persistence.*;

import lombok.Data;



@Entity
@Data

public class ExperienceJD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String minimumYearExperience;
    private String maximumYearExperience;

    public String getMinimumYearExperience() {
        return minimumYearExperience;
    }

    public void setMinimumYearExperience(String minimumYearExperience) {
        this.minimumYearExperience = minimumYearExperience;
    }

    public String getMaximumYearExperience() {
        return maximumYearExperience;
    }

    public void setMaximumYearExperience(String maximumYearExperience) {
        this.maximumYearExperience = maximumYearExperience;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

