package com.ssafy.trip.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.board.model.dto.BoardDto;
import com.ssafy.trip.board.model.dto.BoardParameterDto;
import com.ssafy.trip.board.model.mapper.BoardMapper;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {
	
	private BoardMapper boardMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper boardMapper) {
		super();
		this.boardMapper = boardMapper;
	}
	
	@Override
	public List<BoardDto> listBoard(BoardParameterDto boardParameterDto) throws Exception {
		int start = boardParameterDto.getPg() == 0 ? 0 : (boardParameterDto.getPg() - 1) * boardParameterDto.getSpp();
		boardParameterDto.setStart(start);
		return boardMapper.listBoard(boardParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));
		System.out.println(currentPage  +  " currentPage");

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = boardMapper.getTotalBoardCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
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
	public boolean writeBoard(BoardDto boardDto) throws Exception {
		if(boardDto.getTitle() == null || boardDto.getContent() == null) {
			throw new Exception();
		}
		return boardMapper.writeBoard(boardDto) == 1;
	}


	@Override
	@Transactional
	public boolean modifyBoard(BoardDto boardDto) throws Exception {
		return boardMapper.modifyBoard(boardDto) == 1;
	}


	@Override
	@Transactional
	public boolean deleteBoard(int id) throws Exception {
		return boardMapper.deleteBoard(id) == 1;
	}





}
