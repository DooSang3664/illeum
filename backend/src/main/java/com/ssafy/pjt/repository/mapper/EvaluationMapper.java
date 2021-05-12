package com.ssafy.pjt.repository.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.pjt.dto.Evaluation;
import com.ssafy.pjt.dto.request.insertRoomEvaluationDto;

public interface EvaluationMapper {
	public Evaluation seachEvaluation(int eid) throws SQLException;
	public List<Map<String, Object>> roomEntrantInfo(insertRoomEvaluationDto dto) throws SQLException;
}
