package com.ssafy.trip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.model.dto.BoardDto;

@Mapper
public interface BoardMapper {
	
	List<BoardDto> listBoard(Map<String, Object> map) throws SQLException;
	List<BoardDto> listBoard() throws SQLException;
	BoardDto viewBoard(int id) throws SQLException;
	void updateHit(int id) throws SQLException;
	int writeBoard(BoardDto boardDto) throws SQLException;
	int modifyBoard(BoardDto boardDto) throws SQLException;
	int deleteBoard(int id) throws SQLException;
	int getTotalBoardCount(Map<String, Object> param) throws SQLException;
}
