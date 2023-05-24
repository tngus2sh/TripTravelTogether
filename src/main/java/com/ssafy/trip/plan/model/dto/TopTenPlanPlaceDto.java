package com.ssafy.trip.plan.model.dto;

public class TopTenPlanPlaceDto extends PlaceDto {
	
	private int placeCnt;

	@Override
	public String toString() {
		return "TopTenPlanPlaceDto [placeCnt=" + placeCnt + ", getId()=" + getId() + ", getPlanId()=" + getPlanId()
				+ ", getPlaceId()=" + getPlaceId() + ", getName()=" + getName() + ", getAddress()=" + getAddress()
				+ ", getLat()=" + getLat() + ", getLng()=" + getLng() + ", getImageUrl()=" + getImageUrl()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
}
