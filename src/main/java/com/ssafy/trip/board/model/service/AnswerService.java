package com.ssafy.trip.board.model.service;

import java.util.List;

import com.ssafy.trip.board.model.dto.AnswerDto;

public interface AnswerService {
	
	List<AnswerDto> listAnswer(int boardId) throws Exception;
	boolean writeAnswer(AnswerDto answerDto) throws Exception;
	boolean modifyAnswer(AnswerDto answerDto) throws Exception;
	boolean deleteAnswer(int id) throws Exception;
}
