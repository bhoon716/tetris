package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
	private String id;

	public Tetris() {
		setSize(400, 400); // 프레임의 크기를 400x400으로 설정
		setTitle("Tetris"); // 프레임의 타이틀을 "Tetris"로 설정
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 프레임의 닫기 버튼을 누르면 프로그램 종료
		setVisible(false);
		setLayout(new BorderLayout());
		switchPanel(new Login(this));
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void switchPanel(JPanel panel){
		getContentPane().removeAll();
		getContentPane().add(panel);
		revalidate();
		repaint();
		panel.requestFocus();
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null); // 프레임을 화면의 가운데에 배치
		game.setVisible(true); // 프레임을 화면에 표시
	}
}