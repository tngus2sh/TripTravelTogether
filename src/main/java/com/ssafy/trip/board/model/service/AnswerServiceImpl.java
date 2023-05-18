package com.ssafy.trip.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.board.model.dto.AnswerDto;
import com.ssafy.trip.board.model.mapper.AnswerMapper;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	private AnswerMapper answerMapper;

	@Autowired
	public AnswerServiceImpl(AnswerMapper answerMaprer) {
		super();
		this.answerMapper = answerMaprer;
	}

	@Override
	public List<AnswerDto> listAnswer(int boardId) throws Exception {
		return answerMapper.listAnswer(boardId);
	}

	@Override
	public boolean writeAnswer(AnswerDto answerDto) throws Exception {
		if(answerDto.getContent() == null) {
			throw new Exception();
		}
		return answerMapper.writeAnswer(answerDto) == 1;
	}

	@Override
	public boolean modifyAnswer(AnswerDto answerDto) throws Exception {
		return answerMapper.modifyAnswer(answerDto) == 1;
	}

	@Override
	public boolean deleteAnswer(int id) throws Exception {
		return answerMapper.deleteAnswer(id) == 1;
	}
	
}
