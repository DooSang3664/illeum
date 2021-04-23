package com.ssafy.pjt.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int rid;
    
    private int uid;
    
    @Column(nullable=false, length=45)
    private String roomName;
    
    @CreationTimestamp
    private Date startTime;
    
    @UpdateTimestamp
    private Date endTime;

    private String state;
  
}
