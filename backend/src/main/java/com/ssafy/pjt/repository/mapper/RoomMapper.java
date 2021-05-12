package com.ssafy.pjt.repository.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface RoomMapper {
	public List<Map<String, Object>> roomAll() throws SQLException;
	public List<Map<String, Object>> roomName(String roomName) throws SQLException;
	public List<Map<String, Object>> roomjoinMemeber(int rid) throws SQLException;
	public List<Map<String, Object>> roomJoinEvaluation(int rid) throws SQLException;
}
