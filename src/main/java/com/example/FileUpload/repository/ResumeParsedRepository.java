package com.example.FileUpload.repository;

import com.example.FileUpload.entity.ParsedResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeParsedRepository extends JpaRepository<ParsedResume, Long> {



}
