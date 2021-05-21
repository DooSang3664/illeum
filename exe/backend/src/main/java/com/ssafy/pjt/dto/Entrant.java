package com.ssafy.pjt.dto;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@SuppressWarnings("all")
public class Entrant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int eid;
    
    private int uid;
       
    private int rid;
    
//    @OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
//    @JoinColumn(name="eid")
//    private Collection<Evaluation> evaluation;
//    
//    @OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
//    @JoinColumn(name="uid")
//    private Collection<Member> member;

}
