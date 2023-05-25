package com.ssafy.trip.attraction.model.dto;

public class AttractionDetailInfoDto extends AttractionInfoDto{
	
	private String overview;

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@Override
	public String toString() {
		return "AttractionDetailInfoDto [overview=" + overview + ", getContentId()=" + getContentId()
				+ ", getContentTypeId()=" + getContentTypeId() + ", getTitle()=" + getTitle() + ", getAddr1()="
				+ getAddr1() + ", getAddr2()=" + getAddr2() + ", getZipcode()=" + getZipcode() + ", getTel()="
				+ getTel() + ", getFirst_image()=" + getFirst_image() + ", getFirst_image2()=" + getFirst_image2()
				+ ", getReadcount()=" + getReadcount() + ", getSidoCode()=" + getSidoCode() + ", getGugunCode()="
				+ getGugunCode() + ", getLatitude()=" + getLatitude() + ", getLongitude()=" + getLongitude()
				+ ", getMlevel()=" + getMlevel() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
	
}
