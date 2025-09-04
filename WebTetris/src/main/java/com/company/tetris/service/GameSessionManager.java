package com.company.tetris.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

//방  세션 관리
@Service
public class GameSessionManager {
	private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();
	
    public GameSession getSession(String roomId) { return sessions.computeIfAbsent(roomId, id -> new GameSession()); }

    public void removeSession(String roomId) { sessions.remove(roomId); }
}
