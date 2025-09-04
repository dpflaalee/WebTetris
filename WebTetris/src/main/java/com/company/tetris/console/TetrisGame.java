package com.company.tetris.console;

import java.util.Scanner;

import com.company.tetris.logic.Point;
import com.company.tetris.logic.ScoreManager;
import com.company.tetris.logic.TetrisBoard;
import com.company.tetris.logic.Tetromino;
import com.company.tetris.logic.TetrominoGenerator;

public class TetrisGame {
	/*private TetrisBoard board = new TetrisBoard();
	private TetrominoGenerator generator = new TetrominoGenerator();
	private ScoreManager scoreManager = new ScoreManager();
	private Scanner scanner = new Scanner(System.in);
	
	private void renderToConsole(TetrisBoard board) {
		int [][] grid = board.getBoard();
		Tetromino current = board.getCurrent();
		for(int y=0; y<grid.length; y++) {
			for(int x=0; x<grid[0].length; x++) {
				boolean isBlock = grid[y][x] ==1;
				boolean isCurrent = false;
				if(current !=null) {
					for(Point p : current.getAbsoluteBlocks()) {
						if(p.x == x && p.y ==y) {
							isCurrent = true;
							break;
						}
					}
				}
				System.out.print(isCurrent ? "@" : (isBlock ? "#" : "."));
			}
			System.out.println();
		}
	}
	
	public void start() {
		while(true) {
			Tetromino t = generator.next();
			t.setPosition(new Point(4,0)); //중앙에서 시작
			board.spawn(t);
			
			//못움직이면 종료
			if(!board.canMove(t)) {System.out.println("GAME OVER!"); break;}
			while(true) {
				renderToConsole(board);
				System.out.println("Score: " + scoreManager.getScore());
				System.out.print("Command(a: left, d: right, s: down, w: rotate, x: drop): ");
				String input = scanner.nextLine();
				
				switch(input) {
					case "a" : t.move(-1, 0); break;
					case "d" : t.move(1, 0); break;
					case "s" : t.move(0, 1); break;
					case "w" : t.rotateClockwise(); break;
					case "x" : while(board.canMove(t)) {t.move(0, 1);} t.move(0, -1); break;
				}
			}
		}
	}*/
}
