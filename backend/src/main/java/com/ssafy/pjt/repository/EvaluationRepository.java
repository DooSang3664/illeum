package com.ssafy.pjt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.pjt.dto.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
	Evaluation findByVid(int vid);
	List<Evaluation> findByEid(int eid);
	Long deleteByVid(int vid);
}

