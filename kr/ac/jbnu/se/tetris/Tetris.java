package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {

	JLabel statusbar; // 게임의 상태를 나타내는 레이블(시작, 일시정지, 게임오버, 스코어 등)

	public Tetris() {

		statusbar = new JLabel(" 0"); // 게임의 상태를 나타내는 레이블을 생성
		add(statusbar, BorderLayout.SOUTH); // 레이블을 프레임의 아래쪽에 추가
		Board board = new Board(this); // Board : 테트리스 게임 로직 및 그래픽
		add(board); // Board를 프레임에 추가
		board.start(); // 게임 시작

		setSize(200, 400); // 프레임의 크기를 200x400으로 설정
		setTitle("Tetris"); // 프레임의 타이틀을 "Tetris"로 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 프레임의 닫기 버튼을 누르면 프로그램 종료
	}

	public JLabel getStatusBar() { // 게임의 상태를 나타내는 레이블을 반환하는 메소드
		return statusbar;
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null); // 프레임을 화면의 가운데에 배치
		game.setVisible(true); // 프레임을 화면에 표시

		Bgm bgm = new Bgm(); // 배경음악 재생
		bgm.start();
	}
}