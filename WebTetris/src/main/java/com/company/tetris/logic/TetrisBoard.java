package com.company.tetris.logic;

//게임판, 충돌 검사
public class TetrisBoard {
	private final int width = 10;
	private final int height = 20;
	private int[][] board = new int[height][width];
	private Tetromino current;
	
	//블록 생성
	public void spawn(Tetromino t) {
		t.setPosition(new Point(4,0));
		if(canMove(t)) { current = t;
		}else {System.out.println("GAME OVER"); current=null;}
	}
	
	//고스트 블록
	public Point[] getGhostBlocks() {
		Tetromino current = getCurrent();
		if(current == null) return new Point[0];
		
		Tetromino ghost = current.copy();		
		while (canMove(ghost)) {ghost.move(0, 1);}
		ghost.move(0, -1); //이동 불가 시 마지막 유효 위치로 롤백
		
		return ghost.getAbsoluteBlocks();
	}
	
	//이동 가능한가요?
	public boolean canMove(Tetromino t) {
		for(Point p : t.getAbsoluteBlocks()) {
			if(p.x < 0 || p.x >= width || p.y >= height) return false;
	        if(p.y >= 0 && board[p.y][p.x] != 0) return false;
		}
		return true;
	}
	//블록 고정
	public void fixTetromino() {
		for(Point p : current.getAbsoluteBlocks()) {
			if(p.y >= 0 && p.y < height && p.x >= 0 && p.x < width) {
				board[p.y][p.x] = 1;
			}
		}
	}
	//줄 삭제
	public int clearLines() {
		int linesCleared = 0;
		for(int y=0; y<height; y++) {
			boolean full = true;
			for(int x=0; x<width; x++) { if(board[y][x]==0) { full = false; break; } }
			if(full) {
				linesCleared++;
				for(int row = y; row>0; row--) { board[row]=board[row-1].clone(); }
				board[0] = new int[width];
			}
		}
		return linesCleared;
	}
	
	//현재 블록 반환
	public Tetromino getCurrent() {return current;}
	//게임판 상태 반환
	public int[][] getBoard(){return board;}
	//게임판 크기
	public int getWidth() {return width;}
	public int getHeight() {return height;}
	
}
