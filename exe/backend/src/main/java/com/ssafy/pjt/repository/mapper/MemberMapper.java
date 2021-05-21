package com.ssafy.pjt.repository.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MemberMapper {
	public List<Map<String, Object>> memberJoinRoom(int uid) throws SQLException;
	public List<Map<String, Object>> founder(int uid) throws SQLException;
	public List<Map<String, Object>> memberJoinEvaluation(int uid) throws SQLException;
	public List<Map<String, Object>> memberAttend(int uid) throws SQLException;
}
