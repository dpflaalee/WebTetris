package com.company.tetris.logic;

import java.util.*;

//랜덤 생성
public class TetrominoGenerator {
//	private static final char[] TYPES = {'I', 'O', 'T', 'S', 'Z', 'J', 'L'};
//	private Random random = new Random();
//	
//	public Tetromino next() {
//		char type = TYPES[random.nextInt(TYPES.length)];
//		return new Tetromino(type);
//	}
	
	private static final List<TetrominoType> ALL_TYPES = Arrays.asList(TetrominoType.values());
	private final Queue<TetrominoType> bag = new LinkedList<>();
	private final Random random = new Random();
	
	public Tetromino next() {
		if(bag.isEmpty()) refillBag();
		return new Tetromino(bag.poll());
	}
	private void refillBag() {
		List<TetrominoType> shuffled = new ArrayList<>(ALL_TYPES);
		Collections.shuffle(shuffled, random);
		bag.addAll(shuffled);
	}
	
	//미리보기
	public TetrominoType peekNextType() {
		if(bag.isEmpty()) refillBag();
		return bag.peek();
	}
	
	public List<TetrominoType> getUpcomingTypes(){
		return new ArrayList<>(bag);
	}
}
