package com.company.tetris.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.company.tetris.dto.RoomRequest;
import com.company.tetris.model.Room;
import com.company.tetris.model.User;

@Service
public class RoomService {
	private final Map<String, Room> roomMap = new ConcurrentHashMap<>();
	private final Map<String, String> userRoomMap = new ConcurrentHashMap<>();
	
	//방장 판별
	public boolean isHost(String roomId, String userId) {
	    Room room = roomMap.get(roomId);
	    return room != null && room.isHost(userId);
	}
	
	// 방 만들기
    public Room createRoom(String hostId, int maxPlayers) {
        // 기존 방 참여 중이면 나가기
        String currentRoomId = userRoomMap.get(hostId);
        if (currentRoomId != null) { leaveRoom(currentRoomId, hostId); }

        Room room = new Room(hostId, maxPlayers);
        roomMap.put(room.getRoomId(), room);
        userRoomMap.put(hostId, room.getRoomId());
        return room;
    }
	
	//방 참여
	public boolean joinRoom(String roomId, User user) {
		//한 유저가 여러 방 입장하는 걸 방지
		String currentRoomId = userRoomMap.get(user.getUserId());
		if(currentRoomId != null && !currentRoomId.equals(roomId)) {leaveRoom(currentRoomId, user.getUserId());}
		
		Room room = roomMap.get(roomId);
		if(room==null || room.isFull()) return false;
		
		boolean joined = room.addUser(user);
		if(joined) userRoomMap.put(user.getUserId(), roomId);
		
		return joined;
	}
	
    // 나가기
    public void leaveRoom(String roomId, String userId) {
        Room room = roomMap.get(roomId);
        if (room != null) {
            room.removeUser(userId);
            userRoomMap.remove(userId);
            if (room.getParticipants().isEmpty()) { deleteRoom(roomId, userId, false); }
        }
    }
    
    // 방 삭제
    public boolean deleteRoom(String roomId, String userId, boolean checkHost) {
        Room room = roomMap.get(roomId);
        if (room == null) return false;
        if (checkHost && !room.isHost(userId)) return false;

        room.getParticipants().forEach(u -> userRoomMap.remove(u.getUserId()));
        roomMap.remove(roomId);
        return true;
    }
    
	
    // 준비 상태 토글
    public boolean toggleReady(String roomId, String userId) {
        Room room = roomMap.get(roomId);
        if (room == null) return false;

        User targetUser = room.getParticipants().stream()
            .filter(u -> u.getUserId().equals(userId))
            .findFirst().orElse(null);

        if (targetUser == null) return false;

        room.setReady(userId, !targetUser.isReady());
        return true;
    }
	
	//전체 준비상태 확인
    public boolean canStartGame(String roomId, String userId) {
        Room room = roomMap.get(roomId);
        return room != null && room.isHost(userId) && room.isReady();
    }
	
	//방 조회
	public List<Room> getRoomList(){ return new ArrayList<>(roomMap.values()); }
	
	//단일 방 조회
	public Room getRoom(String roomId) { return roomMap.get(roomId);}
	
    public String getUserRoomId(String userId) { return userRoomMap.get(userId); }
    
    public Map<String, Object> getRoomInfo(RoomRequest request) {
    	if (request == null || request.getRoomId() == null || request.getUserId() == null) return null;

        Room room = roomMap.get(request.getRoomId());
        if (room == null) return null;

        String userId = request.getUserId();
        boolean isHost = room.isHost(userId);

        // Map<String, User>에서 값(User)의 userId를 기준으로 비교
        boolean isParticipant = room.getParticipants().stream()
        	    .anyMatch(user -> user.getUserId().equals(userId));

        boolean isJoined = request.getRoomId().equals(userRoomMap.get(userId));

        Map<String, Object> info = new HashMap<>();
        info.put("roomId", room.getRoomId());
        info.put("hostId", room.getHostId());
        info.put("maxPlayers", room.getMaxPlayers());
        info.put("participants", room.getParticipants()); 
        info.put("userId", userId);
        info.put("isHost", isHost);
        info.put("isParticipant", isParticipant);
        info.put("isJoined", isJoined);

        return info;
    }
	
}
