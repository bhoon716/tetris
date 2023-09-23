package kr.ac.jbnu.se.tetris;

import javax.swing.JFrame;

public class Tetris extends JFrame {

	public Tetris() {

		Board board = new Board(this); // Board : 테트리스 게임 로직 및 그래픽
		add(board); // Board를 프레임에 추가
		board.start(); // 게임 시작

		setSize(200, 400); // 프레임의 크기를 200x400으로 설정
		setTitle("Tetris"); // 프레임의 타이틀을 "Tetris"로 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 프레임의 닫기 버튼을 누르면 프로그램 종료
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null); // 프레임을 화면의 가운데에 배치
		game.setVisible(true); // 프레임을 화면에 표시
	}
}