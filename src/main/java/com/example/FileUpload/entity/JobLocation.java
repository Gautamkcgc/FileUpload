package com.example.FileUpload.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "jobLocation")
public class JobLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String location;
    private String city;
    private String country;
    private String countryCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public JobDescription getJd() {
        return jd;
    }


    public void setJd(JobDescription jd) {
        this.jd = jd;
    }

    @ManyToOne
    @JoinColumn(name = "jd_id")
    private JobDescription jd;

}
