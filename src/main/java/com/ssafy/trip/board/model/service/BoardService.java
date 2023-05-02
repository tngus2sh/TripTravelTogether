package com.ssafy.trip.board.model.service;

import java.util.List;
import java.util.Map;


import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.util.PageNavigation;

public interface BoardService {
	
	List<BoardDto> listBoard(Map<String, String> map) throws Exception;
	BoardDto viewBoard(int id) throws Exception;
	void updateHit(int id) throws Exception;
	void writeBoard(BoardDto boardDto) throws Exception;
	void modifyBoard(BoardDto boardDto) throws Exception;
	void deleteBoard(int id) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
}
