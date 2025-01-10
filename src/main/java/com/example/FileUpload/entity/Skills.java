package com.example.FileUpload.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills")
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private String skillType;


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

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillType() {
        return skillType;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public ParsedResume getResume() {
        return resume;
    }

    public void setResume(ParsedResume resume) {
        this.resume = resume;
    }
}

