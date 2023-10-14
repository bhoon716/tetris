package kr.ac.jbnu.se.tetris;

import java.util.Random;

public class Shape { //블록의 모양을 나타내는 클래스

	private Tetrominoes pieceShape;
	private int coords[][];
	private int[][][] coordsTable;

	public Shape() { //생성자
		coords = new int[4][2]; //블록의 좌표를 나타내는 배열 생성
		setShape(Tetrominoes.NoShape); //블록의 모양을 NoShape(없음)으로 설정
	}
	public void setShape(Tetrominoes shape) { //블록의 모양을 설정하는 메소드
		//블록의 상대적인 좌표를 나타내는 배열(NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape)
		coordsTable = new int[][][] { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
			{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, { { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },
			{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } }, { { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
			{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } }, { { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
			{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } }, { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }};

		//coordsTable 배열에서 가져온 좌표 정보를 coords 배열에 저장
		for (int i = 0; i < 4; i++) { 
			for (int j = 0; j < 2; ++j) {
				coords[i][j] = coordsTable[shape.ordinal()][i][j]; 
			}
		}
		pieceShape = shape; //현재 블록(pieceShape)를 매개변수 shape로 업데이트
	}

	private void setX(int index, int x) { //블록의 x좌표를 설정하는 메소드
		coords[index][0] = x; //coords 배열의 index번째 칸의 x좌표를 x로 설정
	}

	private void setY(int index, int y) { //블록의 y좌표를 설정하는 메소드
		coords[index][1] = y;
	}

	public int getX(int index) { //블록의 x좌표를 반환하는 메소드
		return coords[index][0];
	}

	public int getY(int index) { //블록의 y좌표를 반환하는 메소드
		return coords[index][1];
	}

	public Tetrominoes getShape() { //블록의 모양을 반환하는 메소드
		return pieceShape;
	}

	public String getShapeToString(){
		return pieceShape.toString();
	}

	// public void setRandomShape() { //랜덤으로 블록의 모양을 설정하는 메소드
	// 	Random r = new Random(); //랜덤 객체 생성
	// 	int x = Math.abs(r.nextInt()) % 7 + 1; //1부터 7까지의 랜덤한 숫자를 생성
	// 	Tetrominoes[] values = Tetrominoes.values(); //Tetrominoes 열거형의 모든 값들을 가져옴
	// 	setShape(values[x]); //랜덤한 모양을 설정
	// }

	public String setRanShape(){
		Random r = new Random(); //랜덤 객체 생성
		int x = Math.abs(r.nextInt()) % 7 + 1; //1부터 7까지의 랜덤한 숫자를 생성
		Tetrominoes[] values = Tetrominoes.values(); //Tetrominoes 열거형의 모든 값들을 가져옴
		return values[x].toString();
	}
		
	public int minX() { //블록의 x좌표 중 가장 작은 값을 반환하는 메소드
		int m = coords[0][0]; //가장 작은 값으로 coords 배열의 첫 번째 칸의 x좌표를 설정
		for (int i = 0; i < 4; i++) { //coords 배열의 모든 칸을 검사
			m = Math.min(m, coords[i][0]); //가장 작은 값으로 m을 업데이트
		}
		return m; //가장 작은 값 반환
	}

	public int minY() { //블록의 y좌표 중 가장 작은 값을 반환하는 메소드
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	public Shape rotateLeft() { // 블록을 왼쪽으로 회전하는 메소드
		if (pieceShape == Tetrominoes.SquareShape) // 블록의 모양이 SquareShape(네모)라면
			return this; // 블록을 회전시키지 않고 그대로 반환
		
		Shape result = new Shape(); // 새로운 블록 result 생성
		result.pieceShape = pieceShape; // 새로운 블록의 모양을 현재 블록의 모양으로 설정
	
		for (int i = 0; i < 4; ++i) { // 블록의 모든 칸에 대해
			result.setX(i, getY(i)); // 현재 칸의 x 좌표를 새로운 칸의 y 좌표로 설정
			result.setY(i, -getX(i)); // 현재 칸의 y 좌표를 새로운 칸의 -x 좌표로 설정
		}
		return result; // 새로운 블록 반환
	}

	public Shape rotateRight() { //블록을 오른쪽으로 회전하는 메소드
		if (pieceShape == Tetrominoes.SquareShape)
			return this;

		Shape result = new Shape();
		result.pieceShape = pieceShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, -getY(i));
			result.setY(i, getX(i));
		}
		return result;
	}
}