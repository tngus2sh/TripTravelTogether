package com.ssafy.trip.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.board.model.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}


	@Override
	public List<BoardDto> listBoard(Map<String, Object> map) throws Exception {
		return boardMapper.listBoard(map);
	}


	@Override
	public BoardDto viewBoard(int id) throws Exception {
		return boardMapper.viewBoard(id);
	}


	@Override
	public void updateHit(int id) throws Exception {
		boardMapper.updateHit(id);
	}


	@Override
	public void writeBoard(BoardDto boardDto) throws Exception {
		boardMapper.writeBoard(boardDto);
	}


	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		boardMapper.modifyBoard(boardDto);
	}


	@Override
	public void deleteBoard(int id) throws Exception {
		boardMapper.deleteBoard(id);
	}

}
