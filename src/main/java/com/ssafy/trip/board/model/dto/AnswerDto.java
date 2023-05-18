package com.ssafy.trip.board.model.dto;

public class AnswerDto {
	private int id;
	private String content;
	private String createdAt;
	private int boardId;
	private String userId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateAt() {
		return createdAt;
	}
	public void setCreateAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "AnswerDto [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", boardId=" + boardId
				+ ", userId=" + userId + "]";
	}
	
}
