package com.company.tetris.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import com.company.tetris.dto.GameMove;
import com.company.tetris.dto.RoomRequest;
import com.company.tetris.service.GameSession;
import com.company.tetris.service.GameSessionManager;
import com.company.tetris.service.PlayerGameService;
import com.company.tetris.service.RoomService;
import com.company.tetris.websocket.GameEventBroadcaster;

@Controller
public class TetrisControllerWebSocket {
	private final GameSessionManager sessionManager;
	private final GameEventBroadcaster broadcaster;
	private final RoomService roomService;
	
	@Autowired
	public TetrisControllerWebSocket(GameSessionManager sessionManager, GameEventBroadcaster broadcaster, RoomService roomService) {
		this.sessionManager = sessionManager;
		this.broadcaster = broadcaster;
		this.roomService = roomService;
	}
	
	@MessageMapping("/move")
	public void handleMove(GameMove move) {
		GameSession session = sessionManager.getSession(move.getRoomId());
		PlayerGameService player = session.getPlayer(move.getUserId());
		player.move(move.getDirection());
		
		broadcaster.broadcastCameState(move.getRoomId(), session.getAllStates());
	}
	
	@MessageMapping("/drop")
	public void handleDrop(GameMove move) {
		GameSession session = sessionManager.getSession(move.getRoomId());
		PlayerGameService player = session.getPlayer(move.getUserId());
		player.drop();
		
		broadcaster.broadcastCameState(move.getRoomId(), session.getAllStates());
	}
	
    @MessageMapping("/pause")
    public void handlePause(RoomRequest request) {
        String roomId = request.getRoomId();
        String userId = request.getUserId();
        if (!roomService.isHost(roomId, userId)) return;

        GameSession session = sessionManager.getSession(roomId);
        session.getAllPlayers().forEach(PlayerGameService::pause);
        broadcaster.broadcastCameState(roomId, session.getAllStates());
    }

    @MessageMapping("/resume")
    public void handleResume(RoomRequest request) {
        String roomId = request.getRoomId();
        String userId = request.getUserId();
        if (!roomService.isHost(roomId, userId)) return;

        GameSession session = sessionManager.getSession(roomId);
        session.getAllPlayers().forEach(PlayerGameService::resume);
        broadcaster.broadcastCameState(roomId, session.getAllStates());
    }
	
}
