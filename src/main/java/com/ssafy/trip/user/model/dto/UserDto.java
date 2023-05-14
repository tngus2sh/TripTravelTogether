package com.ssafy.trip.user.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "UserDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class UserDto {
	
	@ApiModelProperty(value = "회원 아이디")
	private String userId;
	@ApiModelProperty(value = "회원 비밀번호")
	private String userPwd;
	@ApiModelProperty(value = "회원 이름")
	private String userName;
	@ApiModelProperty(value = "회원 이메일 id")
	private String emailId;
	@ApiModelProperty(value = "회원 이메일 domain")
	private String emailDomain;
	@ApiModelProperty(value = "회원 등급")
	private String grade;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getEmailDomain() {
		return emailDomain;
	}
	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "UserDto [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", emailId=" + emailId
				+ ", emailDomain=" + emailDomain + ", grade=" + grade + "]";
	}
	
	
}
