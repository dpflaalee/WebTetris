package com.company.tetris.dto;

import com.company.tetris.logic.Point;
import com.company.tetris.logic.Tetromino;
import com.company.tetris.logic.TetrominoType;

//DTO
public class GameState {
	private int[][] board;
	private Point[] current;
	private int score;
	private boolean gameOver;
	private boolean isPaused;
	//색상
	private TetrominoType currentType;
	private String color;
	//고스트 블럭
	private Point[] ghost;
	//미리보기
	private TetrominoType nextType;
	private Point[] nextBlocks;
	private String nextColor;
	
	public GameState(int[][] board, Point[] current, int score, boolean gameOver, boolean isPaused, Tetromino currentTetromino, 
			Point[] ghostBlocks, TetrominoType nextType,Point[] nextBlocks, String nextColor) {
		this.board = board;
		this.current = current;
		this.score = score;
		this.gameOver = gameOver;
		this.isPaused = isPaused;
		
		//이게,,, 게임을 시작 안한상태에서 null값 방어코드가 없으면 오류가 엄청뜨네...
		if (currentTetromino != null && currentTetromino.getType() != null) {
			this.currentType = currentTetromino.getType();
			this.color = currentType.getColor();			
		}else {
			this.currentType = null;
			this.color = "#999";
		}
		
		this.ghost = ghostBlocks;
		this.nextType = nextType;
		this.nextBlocks = nextBlocks;
		this.nextColor = nextColor;
	}
	
	public boolean isGameOver() { return gameOver;}
	public boolean isPaused() {return isPaused;}
	public int[][] getBoard(){ return board; }
	public Point[] getCurrent(){return current;}
	public int getScore() {return score;}
	
	public TetrominoType getCurrentType() {return currentType;}
	public String getColor() {return color;}
	
	public Point[] getGhost(){return ghost;}
	public TetrominoType getNextType() {return nextType;}
	public Point[] getNextBlocks() { return nextBlocks; }  
	public String getNextColor() { return nextColor; }       
}
