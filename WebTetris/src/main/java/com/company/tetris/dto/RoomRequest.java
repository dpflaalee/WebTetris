package com.company.tetris.dto;

//방 생성 DTO
public class RoomRequest {
    private String roomId;
    private String userId;
    private String hostId;
    private Integer maxPlayers;
    
    public RoomRequest(String roomId, String userId, String hostId, int maxPlayers) {
    	this.roomId = roomId;
    	this.userId = userId;
    	this.hostId = hostId;
    	this.maxPlayers = maxPlayers;
    }
    
    public String getRoomId() {return roomId;}
    public String getUserId() {return userId;}
    public String getHostId() {return hostId;}
    public int getMaxPlayers() {return maxPlayers;}
}
