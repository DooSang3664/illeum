package com.ssafy.pjt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pjt.dto.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByName(String username);
    Member findByEmail(String email);
    Long deleteByEmail(String email);
}
