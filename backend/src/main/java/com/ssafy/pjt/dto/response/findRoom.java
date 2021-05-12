package com.ssafy.pjt.dto.response;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@SuppressWarnings("all")
public class findRoom {

    private int rid;
    
    private int uid;
    
    private String founder;
    
    @Column(name = "room_name")
    private String room_name;
    
    @Column(name = "room_password")
    private String room_password;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "start_time")
    private Date start_time;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "end_time")
    private Date end_time;
    
    @Column(name = "room_state")
    private String room_state;
    
    @Column(name = "room_type")
    private String room_type;
    
}
