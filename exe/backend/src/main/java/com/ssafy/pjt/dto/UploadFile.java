package com.ssafy.pjt.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
/**
 * 
 * @author https://github.com/woobong/spring-boot-jpa-summernote-image-upload-example/
 *
 */
@Entity
@Data
@SuppressWarnings("all")
@Table(name="uploadfile")
public class UploadFile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column
    String originFileName;
    
    @Column
    String saveFileName;
    
    @Column
    String filePath;
    
    @Column
    String contentType;
    
    @Column
    long size;
    
    Date regDate;
}