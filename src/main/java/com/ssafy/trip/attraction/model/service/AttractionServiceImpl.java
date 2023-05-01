package com.ssafy.trip.attraction.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.attraction.model.dto.AttractionInfoDto;
import com.ssafy.trip.attraction.model.dto.GunguDto;
import com.ssafy.trip.attraction.model.dto.SidoDto;
import com.ssafy.trip.attraction.model.mapper.AttractionMapper;

@Service
public class AttractionServiceImpl implements AttractionService {
	
	private AttractionMapper attractionMapper;

	public AttractionServiceImpl(AttractionMapper attractionMapper) {
		super();
		this.attractionMapper = attractionMapper;
	}

	@Override
	public List<SidoDto> listSido() throws SQLException {
		return attractionMapper.listSido();
	}

	@Override
	public int getSidoCode(String sidoName) throws SQLException {
		return attractionMapper.getSidoCode(sidoName);
	}

	@Override
	public List<GunguDto> listGungu(int sidoCode) throws SQLException {
		return attractionMapper.listGungu(sidoCode);
	}

	@Override
	public int getGunguCode(Map<String, Object> map) throws SQLException {
		return attractionMapper.getGunguCode(map);
	}

	@Override
	public List<AttractionInfoDto> listAttractionAll(Map<String, Object> map) throws SQLException {
		return attractionMapper.listAttractionAll(map);
	}

	@Override
	public List<AttractionInfoDto> listAttraction(Map<String, Object> map) throws SQLException {
		return attractionMapper.listAttraction(map);
	}

}
