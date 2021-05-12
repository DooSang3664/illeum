package com.ssafy.pjt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pjt.dto.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByName(String username);
    Member findByEmail(String email);
    Member findByUid(int uid);
    Long deleteByEmail(String email);
}

