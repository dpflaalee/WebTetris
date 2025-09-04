package com.company.tetris.logic;

//이동
public class Tetromino {
//	private Point[] shape;
	//private char type;
	private TetrominoType type;
	private Point position;
	//회전상태 추가...
	private int rotation;
	
	
	public Tetromino (TetrominoType type) {
		this.type = type; 
		this.position=new Point(4,0); //중앙 상단
		this.rotation = 0;
	}
	
    /*private Point[] generateShape(TetrominoType type) {
        switch (type) {
            case I: return new Point[]{ new Point(-1,0), new Point(0,0), new Point(1,0), new Point(2,0) };
            case O: return new Point[]{ new Point(0,0), new Point(1,0), new Point(0,1), new Point(1,1) };
            case T: return new Point[]{ new Point(-1,0), new Point(0,0), new Point(1,0), new Point(0,1) };
            case S: return new Point[]{ new Point(0,0), new Point(1,0), new Point(-1,1), new Point(0,1) };
            case Z: return new Point[]{ new Point(-1,0), new Point(0,0), new Point(0,1), new Point(1,1) };
            case J: return new Point[]{ new Point(-1,0), new Point(0,0), new Point(1,0), new Point(-1,1) };
            case L: return new Point[]{ new Point(-1,0), new Point(0,0), new Point(1,0), new Point(1,1) };
            default: return new Point[]{ new Point(0,0) };
        }
    }*/
	
    //좌표
	public Point[] getAbsoluteBlocks() {
//		Point[] result = new Point[shape.length];
//		for(int i = 0; i<shape.length; i++) {
//			result[i] = new Point(position.x + shape[i].x, position.y + shape[i].y);
//		}
//		return result;
		Point [] shape = type.getShape(rotation);
		Point [] result = new Point[shape.length];
		for(int i=0; i<shape.length; i++) {
			result[i] = new Point(position.x + shape[i].x, position.y + shape[i].y);
		}
		return result;
	}
	
	//이동
	public void move(int dx, int dy) {
		position.x += dx;
		position.y += dy;
	}
	
	//회전
	public void rotateClockwise() {
		rotation = (rotation+1) % 4;
		/*for(int i = 0; i<shape.length; i++) {
			int x = shape[i].x;
			int y = shape[i].y;
			shape[i] = new Point(-y,x);
		}*/
	}
	
	//역회전
    public void rotateCounterClockwise() {
    	rotation = (rotation+3) % 4;
        /*for (int i = 0; i < shape.length; i++) {
            int x = shape[i].x;
            int y = shape[i].y;
            shape[i] = new Point(y, -x);
        }*/
    }
	
	public Point getPosition() {return position;}
	public void setPosition(Point p) {this.position = p;}
	public TetrominoType getType() {return type;}
	public int getRotation() {return rotation;}
	
	public void setRotation(int r) {this.rotation = r%4;}
	public Tetromino copy() {
		Tetromino clone = new Tetromino(this.type);
		clone.setPosition(this.position.copy());
		clone.setRotation(this.rotation);
		return clone;
	}
}
