package com.ssafy.trip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.board.model.dto.BoardDto;

public interface BoardService {
	
	List<BoardDto> listBoard(Map<String, Object> map) throws Exception;
	BoardDto viewBoard(int id) throws Exception;
	void updateHit(int id) throws Exception;
	void writeBoard(BoardDto boardDto) throws Exception;
	void modifyBoard(BoardDto boardDto) throws Exception;
	void deleteBoard(int id) throws Exception;
}
