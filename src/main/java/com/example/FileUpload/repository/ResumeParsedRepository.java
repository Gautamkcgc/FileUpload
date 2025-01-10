package com.example.FileUpload.repository;

import com.example.FileUpload.entity.ParsedResume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeParsedRepository extends JpaRepository<ParsedResume, Long> {


}
