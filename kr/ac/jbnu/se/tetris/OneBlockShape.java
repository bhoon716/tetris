package kr.ac.jbnu.se.tetris;

public class OneBlockShape { //블록의 모양을 나타내는 클래스

    private Tetrominoes oneBlockShape;
	private int coords[][];

	public OneBlockShape() { //생성자
		coords = new int[1][2]; //블록의 좌표를 나타내는 배열 생성
		setOneBlockShape();
	}

    public Tetrominoes getOneBlockShape() {
        return oneBlockShape;
    }

    public void setOneBlockShape() {
        coords[0][0] = 0;
        coords[0][1] = 0;
        oneBlockShape = Tetrominoes.OneBlockShape;
    }

	public int getOneBlockX() { //블록의 x좌표를 반환하는 메소드
		return coords[0][0];
	}

	public int getOneBlockY() { //블록의 y좌표를 반환하는 메소드
		return coords[0][1];
	}
}