package com.ssafy.pjt.dto;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.time.LocalDateTime;

@Data
@Entity
@SuppressWarnings("all")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rid;
    
    private int uid;
    
    @Column(name = "room_name", nullable=false, length=45)
    private String roomName;
    
    @Column(nullable=false, length=45)
    private String roomPassword;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    
    @Column(nullable=false, length=45)
    private String roomState;
    
    @Column(nullable=false, length=45)
    private String roomType;

}
