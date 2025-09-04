package com.company.tetris.logic;

//점수!
public class ScoreManager {
	private int score = 0;
	public void addScore(int lines) {
		switch (lines) {
		case 1: score += 100; break;
		case 2: score += 300; break;
		case 3: score += 500; break;
		case 4: score += 800; break;
		}
	}
	public int getScore() {return score;}
}
