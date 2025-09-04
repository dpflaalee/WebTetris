package com.company.tetris.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.company.tetris.dto.GameState;
import com.company.tetris.service.TetrisService;


@Component
public class TetrisWebSocket {
	//클라이언트에 게임 보드의 상태와 현재 블록의 상태 전송하기 위한 설정
	
	private final SimpMessagingTemplate messagingTemplate;
	private final TetrisService tetrisService;
	
    @Autowired
    public TetrisWebSocket(SimpMessagingTemplate messagingTemplate, TetrisService tetrisService) {
        this.messagingTemplate = messagingTemplate;
        this.tetrisService = tetrisService;
    }

    public void sendGameState() {
        GameState state = tetrisService.getCurrentState();
        messagingTemplate.convertAndSend("/topic/gameState", state);
    }

    public void sendRestartMessage() {
        messagingTemplate.convertAndSend("/topic/restartGame", "Game has restarted");
    }
    
	/*
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

    private GameService gameService;

    @Autowired
    public void setGameService(@Lazy GameService gameService) {
        this.gameService = gameService;
    }

    public void sendBoardState() {
        List<String> nextBlockTypes = gameService.getNextBlockTypes();

        BoardState boardState = BoardState.builder()
                .gameBoard(gameService.getBoard().getGameBoard())
                .totalClearLine(gameService.getTotalClearLines())
                .level(gameService.getLevel())
                .nextBlock(nextBlockTypes)
                .build();

        messagingTemplate.convertAndSend("/topic/boardState", boardState);
    }

    public void sendControlBlockState() {
        Block controlBlock = gameService.getControlBlock();
        if (controlBlock != null) {
            BlockState blockState = new BlockState(
                    controlBlock.getX(),
                    controlBlock.getY(),
                    controlBlock.getType(),
                    controlBlock.getShape(),
                    controlBlock.getCurrentRotation(),
                    controlBlock.getShapes()
            );
            messagingTemplate.convertAndSend("/topic/controlBlockState", blockState);
        }
    }

    public void sendHoldBlockState(String holdBlockType) {
        HoldBlockState holdBlockState = new HoldBlockState(gameService.getHoldBlockType());
        messagingTemplate.convertAndSend("/topic/holdBlockState", holdBlockState);
    }

    public void sendGhostBlockState(GhostBlockState ghostBlockState) {
        if (ghostBlockState != null) {
            messagingTemplate.convertAndSend("/topic/ghostBlock", ghostBlockState);
        }
    }
    public void sendRestartGame() {
        messagingTemplate.convertAndSend("/topic/restartGame", "Game has restarted");
    }*/
}
