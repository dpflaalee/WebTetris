package com.company.tetris.service;

import org.springframework.stereotype.Service;

import com.company.tetris.dto.GameState;
import com.company.tetris.logic.*;

@Service
public class TetrisService {
	private TetrisBoard board = new TetrisBoard();
	private TetrominoGenerator generator = new TetrominoGenerator();
	private ScoreManager scoreManager = new ScoreManager();
	private boolean isGameOver = false;
	private boolean isPaused = false;
	
	public void spawn() {
		Tetromino t = generator.next();
		t.setPosition(new Point(4, 0));
		if(board.canMove(t)) {board.spawn(t);} else {
			if(!isGameOver) {System.out.println("GAME OVER"); isGameOver=true;}} // 게임 오버 커멘드
		//board.spawn(t);
	}
	
	public void move(String direction) {
		if(isGameOver || isPaused) return;
		
		Tetromino t = board.getCurrent();
		if(t==null) return;
		
		Point originalPosition = t.getPosition().copy();
		//Point[] originalShape = t.getAbsoluteBlocks();
		
		switch (direction) {
			case "left": t.move(-1, 0); break;
			case "right": t.move(1, 0); break;
			case "down": t.move(0, 1); break;
			case "rotate": 
				t.rotateClockwise(); 
				if(!board.canMove(t)) {t.rotateCounterClockwise();} //회전 복구
				break;
		}
		
		if(!board.canMove(t)) {
			t.setPosition(originalPosition);
			if("down".equals(direction)) {
				board.fixTetromino();
				int lines = board.clearLines();
				scoreManager.addScore(lines);
				spawn();		
			}
		}
	}
	
	public void drop() {
		if(isGameOver || isPaused) return;
		
		Tetromino t = board.getCurrent();
		if(t==null) return;
		
		while (board.canMove(t)) { t.move(0, 1); }
		t.move(0, -1); // 마지막 유효 위치로 롤백
		board.fixTetromino();
		int lines = board.clearLines();
		scoreManager.addScore(lines);
		spawn();
	}
	
	//---일시정지, 재개, 재시작
	public void pause() {isPaused=true;}
	public void resume() {isPaused=false;}
	public void restart() {
		board = new TetrisBoard();
		scoreManager = new ScoreManager();
		isGameOver = false;
		isPaused = false;
		spawn();
	}
	
	//---상태반환
	public GameState getCurrentState() {
		Tetromino current = board.getCurrent();
		Point[]blocks = current != null ? current.getAbsoluteBlocks() : new Point[0];
		Point[]ghost = board.getGhostBlocks();
		
		TetrominoType nextType = generator.peekNextType();
		Tetromino preview = new Tetromino(nextType);
		Point[] nextBlocks = preview.getAbsoluteBlocks();
		String nextColor = nextType.getColor();
		
		return new GameState(board.getBoard(), blocks, scoreManager.getScore(), isGameOver, isPaused, current, 
				ghost, nextType, nextBlocks, nextColor);
	}
	
	public int getScore() {return scoreManager.getScore();}
}
