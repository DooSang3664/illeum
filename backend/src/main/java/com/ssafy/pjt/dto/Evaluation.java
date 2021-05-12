package com.ssafy.pjt.dto;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@SuppressWarnings("all")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int vid;
    
    private int eid;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private Date evalDate;
    
    private int attention;
    private int participation;
    private int distracted;
    private int asleep;
    private int afk;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private Date attendTime;
    
    private int ranking;
    
    private String attend;
}
