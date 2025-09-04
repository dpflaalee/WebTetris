package com.company.tetris.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.company.tetris.dto.RoomRequest;
import com.company.tetris.model.User;
import com.company.tetris.service.GameSession;
import com.company.tetris.service.GameSessionManager;
import com.company.tetris.service.RoomService;
import com.company.tetris.websocket.GameEventBroadcaster;

@Controller
public class RoomWebSocketController {
	private final RoomService roomService;
	private final SimpMessagingTemplate messagingTemplate;
	
	private final GameSessionManager sessionManager;
    private final GameEventBroadcaster broadcaster;
	
	@Autowired
	public RoomWebSocketController(RoomService roomService, SimpMessagingTemplate messagingTemplate, GameSessionManager sessionManager, GameEventBroadcaster broadcaster) {
		this.roomService = roomService;
		this.messagingTemplate = messagingTemplate;
        this.sessionManager = sessionManager;
        this.broadcaster = broadcaster;
	}
	
	@MessageMapping("/room/list")
	public void sendRoomList() {
	    messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomList());
	}
	
	@MessageMapping("/room/create")
    public void createRoom(RoomRequest request) {
		roomService.createRoom(request.getHostId(), request.getMaxPlayers());
        messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomList());
    }
	
	//상세정보조회
	@MessageMapping("/room/info")
	public void getRoomInfo(RoomRequest request) {
	    if (request.getUserId() == null || request.getRoomId() == null) return;

	    Map<String, Object> info = roomService.getRoomInfo(request);
	    if (info != null) { messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), info); }
	}

    @MessageMapping("/room/join")
    public void joinRoom(RoomRequest request) {
//        roomService.joinRoom(request.getRoomId(), new User(request.getUserId()));
//        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), roomService.getRoom(request.getRoomId())); 
        boolean joined = roomService.joinRoom(request.getRoomId(), new User(request.getUserId()));
        if (joined) {  messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), roomService.getRoom(request.getRoomId())); }
    }

    @MessageMapping("/room/ready")
    public void toggleReady(RoomRequest request) {
        roomService.toggleReady(request.getRoomId(), request.getUserId());
        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), roomService.getRoom(request.getRoomId()));
    }

    @MessageMapping("/room/start")
    public void startGame(RoomRequest request) {
        String roomId = request.getRoomId();
        String userId = request.getUserId();

        if (roomService.canStartGame(roomId, userId)) {
            // 1) 방 세션 초기화 
            GameSession session = sessionManager.getSession(roomId);
            session.startGame();

            // 2) 클라 전환용 신호
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/start", "Game Started");

            // 3) 전체 상태 브로드캐스트
            broadcaster.broadcastCameState(roomId, session.getAllStates());
        }
    }

    @MessageMapping("/room/leave")
    public void leaveRoom(RoomRequest request) {
    	roomService.leaveRoom(request.getRoomId(), request.getUserId());
        messagingTemplate.convertAndSend("/topic/room/" + request.getRoomId(), roomService.getRoom(request.getRoomId()));
    }

    @MessageMapping("/room/delete")
    public void deleteRoom(RoomRequest request) {
    	 boolean deleted = roomService.deleteRoom(request.getRoomId(), request.getUserId(), true); // 호스트 체크
         if (deleted) { messagingTemplate.convertAndSend("/topic/rooms", roomService.getRoomList());}
    }
    
    @MessageMapping("/room/my")
    public void getMyRoom(RoomRequest request) {
        String roomId = roomService.getUserRoomId(request.getUserId());
        if (roomId != null) { messagingTemplate.convertAndSend("/topic/room/" + roomId, roomService.getRoom(roomId)); }
    }
}
