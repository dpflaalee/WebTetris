package com.company.tetris.service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.company.tetris.dto.GameState;

//플레이어별 게임 상태 관리
public class GameSession {
    private final Map<String, PlayerGameService> players = new ConcurrentHashMap<>();

    public PlayerGameService getPlayer(String userId) { 
    	return players.computeIfAbsent(userId, id -> new PlayerGameService()); }

    public Map<String, GameState> getAllStates() {
        return players.entrySet().stream() .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getCurrentState()));
    }

    public void removePlayer(String userId) { players.remove(userId); }
    
    public void startGame() { players.values().forEach(PlayerGameService::restart); }
    
    public Collection<PlayerGameService> getAllPlayers() { return players.values(); }
    
    //전원 게임 오버 상태인가 판별...
    public boolean isAllGameOver() {
    	return players.values().stream()
    			.map(PlayerGameService :: getCurrentState)
    			.allMatch(GameState :: isGameOver);
    }
    public List<Map<String, Object>> getRankings(){
    	return players.entrySet().stream()
    			.map(e ->{
    				Map<String, Object> entry = new HashMap<>();
    				entry.put("userId", e.getKey());
    				entry.put("score", e.getValue().getCurrentState().getScore());
    				return entry;
    			})
    			
    			.sorted((a, b) -> Integer.compare((int) b.get("score"), (int) a.get("score")))
    	        .collect(Collectors.toList());
    }
}
