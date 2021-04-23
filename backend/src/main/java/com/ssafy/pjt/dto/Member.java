package com.ssafy.pjt.dto;

import lombok.Data;

import javax.persistence.*;

import java.util.Collection;

@Data
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int uid;
    
    @Column(nullable=false, unique=true, length=50)
    private String email;
    
    private String password;
    
    @Column(nullable=false, unique=true, length=30)
    private String name;

    private String role;

    private String thumbnail;
    
//    @OneToMany(fetch=FetchType.EAGER, cascade= CascadeType.ALL)
//    @JoinColumn(name="uid")
//    private Collection<Room> room;
     
}
