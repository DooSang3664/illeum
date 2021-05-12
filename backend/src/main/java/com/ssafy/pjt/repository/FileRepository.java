package com.ssafy.pjt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.pjt.dto.UploadFile;

public interface FileRepository extends JpaRepository<UploadFile, Integer> {
    public UploadFile findOneBySaveFileName(String saveFileName);
    public Optional<UploadFile> findById(int id);
}