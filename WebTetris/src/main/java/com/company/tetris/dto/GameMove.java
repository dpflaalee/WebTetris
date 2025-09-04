package com.company.tetris.dto;

//입력용 DTO... 게임 참여중인 유저가 한 행동값 입력받는 용
public class GameMove {
	private String roomId;
	private String userId;
	private String direction;
	
	public GameMove (String roomId, String userId, String direction) {
		this.roomId = roomId;
		this.userId = userId;
		this.direction = direction;
	}
	
	public String getRoomId() {return roomId;}
	public String getUserId() {return userId;}
	public String getDirection() {return direction;}	
}
