package com.ssafy.trip.plan.model.dto;

import java.util.List;

public class PlaceDtoList {
	private List<PlaceDto> placeList;

	public List<PlaceDto> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(List<PlaceDto> placeList) {
		this.placeList = placeList;
	}

	@Override
	public String toString() {
		return "PlaceDtoList [placeList=" + placeList + "]";
	}
}
