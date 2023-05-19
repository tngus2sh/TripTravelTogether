package com.ssafy.trip.plan.model.dto;

import java.util.List;

public class RegisterPlanRequest {
	
	private PlanDto planDto;
	private List<PlaceDto> placeDtoList;

	public PlanDto getPlanDto() {
		return planDto;
	}

	public void setPlanDto(PlanDto planDto) {
		this.planDto = planDto;
	}

	public List<PlaceDto> getPlaceDtoList() {
		return placeDtoList;
	}

	public void setPlaceDtoList(List<PlaceDto> placeDtoList) {
		this.placeDtoList = placeDtoList;
	}

}
