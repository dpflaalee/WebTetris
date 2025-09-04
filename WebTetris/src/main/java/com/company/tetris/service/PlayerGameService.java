package com.company.tetris.service;

import com.company.tetris.dto.GameState;

public class PlayerGameService {
    private final TetrisService tetrisService;

    public PlayerGameService() {
        this.tetrisService = new TetrisService(); // 각 유저마다 독립적인 인스턴스
        this.tetrisService.spawn(); // 초기 블록 생성
    }

    public void move(String direction) { tetrisService.move(direction); }

    public void drop() { tetrisService.drop(); }

    public void pause() { tetrisService.pause(); }

    public void resume() { tetrisService.resume(); }

    public void restart() { tetrisService.restart(); }

    public GameState getCurrentState() { return tetrisService.getCurrentState(); }
}
