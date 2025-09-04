package com.company.tetris.websocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.company.tetris.dto.GameState;
import com.company.tetris.service.GameSession;
import com.company.tetris.service.GameSessionManager;

//각 유저 게임 상태 반환하는... 일대 다 통신용 유틸
@Component
public class GameEventBroadcaster {
	@Autowired private SimpMessagingTemplate messagingTemplate;
	@Autowired private GameSessionManager sessionManager;
	
	public void broadcastCameState(String roomId, Map<String, GameState> states) {
	    GameSession session = sessionManager.getSession(roomId);
	    if (session.isAllGameOver()) {
	    	List<Map<String, Object>> rankings = session.getRankings();

	        Map<String, Object> payload = new HashMap<>();
	        payload.put("states", states);
	        payload.put("rankings", rankings);

	        messagingTemplate.convertAndSend("/topic/game/" + roomId, payload);
	    } else { messagingTemplate.convertAndSend("/topic/game/" + roomId, states); }
	}
}
