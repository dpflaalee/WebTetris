package com.company.tetris.logic;

//타입, 회전 상태 관리
public enum TetrominoType {
	
	I("#FF0000", new Point[][] {
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(2,0) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(0,2) },
        { new Point(-1,1), new Point(0,1), new Point(1,1), new Point(2,1) },
        { new Point(1,-1), new Point(1,0), new Point(1,1), new Point(1,2) }
	}), 
	O("#0047DE",new Point[][] {
        { new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) },
        { new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) },
        { new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) },
        { new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) }
	}), 
	T("#47B700", new Point [][] {
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(0,1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(1,0) },
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(0,-1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(-1,0) }
	}), 
	S("#00B897", new Point [][] {
        { new Point(0,0), new Point(1,0), new Point(-1,1), new Point(0,1) },
        { new Point(0,-1), new Point(0,0), new Point(1,0), new Point(1,1) },
        { new Point(0,0), new Point(1,0), new Point(-1,1), new Point(0,1) },
        { new Point(0,-1), new Point(0,0), new Point(1,0), new Point(1,1) }
	}), 
	Z("#00f7ff", new Point [][] {
        { new Point(-1,0), new Point(0,0), new Point(0,1), new Point(1,1) },
        { new Point(1,-1), new Point(0,0), new Point(1,0), new Point(0,1) },
        { new Point(-1,0), new Point(0,0), new Point(0,1), new Point(1,1) },
        { new Point(1,-1), new Point(0,0), new Point(1,0), new Point(0,1) }
	}), 
	J("#DEB800", new Point [][] {
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(-1,1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(1,-1) },
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(1,-1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(-1,1) }
	}), 
	L("#DE00DE", new Point [][] {
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(1,1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(1,1) },
        { new Point(-1,0), new Point(0,0), new Point(1,0), new Point(-1,-1) },
        { new Point(0,-1), new Point(0,0), new Point(0,1), new Point(-1,-1) }
	});
	
	private final String color;
	private final Point [][] rotations;
	
	TetrominoType(String color, Point[][] rotations) {
		this.color=color;
		this.rotations = rotations;
	}
	
	public String getColor() {return color;}
	public Point[] getShape(int rotation) { return rotations[rotation % rotations.length]; }
}
