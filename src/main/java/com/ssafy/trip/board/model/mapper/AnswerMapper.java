package com.ssafy.trip.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.board.model.dto.AnswerDto;

@Mapper
public interface AnswerMapper {
	
	List<AnswerDto> listAnswer(int boardId) throws SQLException;
	int writeAnswer(AnswerDto answerDto) throws SQLException;
	int modifyAnswer(AnswerDto answerDto) throws SQLException;
	int deleteAnswer(int id) throws SQLException;
}
