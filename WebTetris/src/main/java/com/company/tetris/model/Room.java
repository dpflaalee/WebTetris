package com.company.tetris.model;

import java.util.*;

public class Room {
	private String roomId;
    private String hostId;
    private int maxPlayers;
    private Map<String, User> participants = new LinkedHashMap<>();

    public Room(String hostId, int maxPlayers) {
        this.roomId = UUID.randomUUID().toString();
        this.hostId = hostId;
        this.maxPlayers = maxPlayers;
        this.participants.put(hostId, new User(hostId));
    }

    public String getRoomId() { return roomId; }
    public String getHostId() { return hostId; }
    public int getMaxPlayers() { return maxPlayers; }
    public Collection<User> getParticipants() { return participants.values(); }
    public boolean isFull() { return participants.size() >= maxPlayers; }
    public boolean isReady() { return participants.values().stream().allMatch(User::isReady); }
    public boolean isHost(String userId) { return hostId.equals(userId); }

    public boolean addUser(User user) {
        if (isFull() || participants.containsKey(user.getUserId())) return false;
        participants.put(user.getUserId(), user);
        return true;
    }

    public void removeUser(String userId) {
        participants.remove(userId);
        if (userId.equals(hostId) && !participants.isEmpty()) {
            hostId = participants.keySet().iterator().next(); // 방장 변경
        }
    }

    public void setReady(String userId, boolean ready) {
        User user = participants.get(userId);
        if (user != null) user.setReady(ready);
    }

}
