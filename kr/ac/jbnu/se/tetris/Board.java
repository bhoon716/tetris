package kr.ac.jbnu.se.tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

	final int BoardWidth = 10; //게임 보드의 가로 칸 수
	final int BoardHeight = 22; //게임 보드의 세로 칸 수

	Bgm bgm = new Bgm(); //배경음악 객체

	Timer timer; //게임의 속도를 조절하는 타이머
	boolean isFallingFinished = false; //현재 블록이 다 떨어졌는지 확인하는 변수
	boolean isStarted = false; //게임이 시작되었는지를 나타내는 변수
	boolean isPaused = false; //게임이 일시정지되었는지를 나타내는 변수
	int numLinesRemoved = 0; //제거된 줄의 수를 나타내는 변수
	int curX = 0; //현재 블록의 x좌표
	int curY = 0; //현재 블록의 y좌표
	int ghostY = 0; //현재 블록의 고스트 블록의 y좌표
	JLabel statusbar; //게임의 상태를 나타내는 레이블(시작, 일시정지, 게임오버, 스코어 등)
	Shape curPiece; //현재 블록을 나타내는 객체
	Tetrominoes[] board; //게임 보드를 나타내는 배열
	int boardTop = (int) getSize().getHeight() - BoardHeight * squareHeight(); //게임 보드의 상단 좌표

	public Board(Tetris parent) {

		setFocusable(true); //키보드 입력을 받을 수 있도록 설정
		curPiece = new Shape(); //새로운 블록을 생성
		timer = new Timer(400, this); //타이머 생성(400ms마다 actionPerformed()를 호출)
		bgm.play(); //배경음악 재생
		timer.start(); //타이머 시작

		board = new Tetrominoes[BoardWidth * BoardHeight]; //게임 보드를 나타내는 배열 생성
		addKeyListener(new TAdapter()); //키보드 입력을 받을 수 있도록 설정
		clearBoard(); //게임 보드를 초기화
		statusbar = new JLabel(String.valueOf(numLinesRemoved)); // 게임의 상태를 나타내는 레이블을 생성
		add(statusbar, BorderLayout.SOUTH); // 레이블을 프레임의 아래쪽에 추가
	}

	private void drawGhost(Graphics g, int curX, int curY, Tetrominoes shape) { //x, y는 블록 왼쪽 상단의 좌표, shape는 블록의 모양
		int newY = curY;
		while(newY > 0){
			if(!ghostTryMove(curPiece, curX, newY-1))
				break;
			--newY;
		}
		for(int i=0; i<4; ++i){
			int x = curX + curPiece.x(i);
			int y = newY - curPiece.y(i);
			g.fillRect(0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), squareWidth(), squareHeight());
		}
	}

	public void actionPerformed(ActionEvent e) { //타이머가 400ms마다 호출하는 메소드
		if (isFallingFinished) { //블록이 떨어지는 것이 끝났다면
			isFallingFinished = false; //끝났음을 나타내는 변수를 false로 설정
			newPiece(); //새로운 블록을 생성
		} else { //떨어지는 것이 끝나지 않았다면
			oneLineDown(); //블록을 한 칸 아래로 이동
		}
	}

	int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth; //게임 보드의 가로 공간을 BoardWidth로 나눈 값(한 칸의 가로 길이)
	}

	int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight; //게임 보드의 세로 공간을 BoardHeight로 나눈 값(한 칸의 세로 길이)
	}

	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x]; //게임 보드의 (x, y) 위치에 있는 블록의 모양을 반환
	}

	public void start() { //게임을 시작하는 메소드
		if (isPaused) //게임이 일시정지되었다면
			return; //메소드 종료

		isStarted = true; //게임이 시작되었음을 나타내는 변수를 true로 설정
		isFallingFinished = false; //블록이 떨어지는 것이 끝났음을 나타내는 변수를 false로 설정
		numLinesRemoved = 0; //제거된 줄의 수를 0으로 설정
		clearBoard(); //게임 보드를 초기화
		statusbar.setText(String.valueOf(numLinesRemoved));
		newPiece(); //새로운 블록을 생성
		timer.start(); //타이머 시작
	}

	private void pause() { //게임을 일시정지하는 메소드
		if (!isStarted) //게임이 시작되지 않았다면
			return; //메소드 종료

		isPaused = !isPaused; //게임이 일시정지되었음을 나타내는 변수를 반전
		if (isPaused) { //게임이 일시정지되었다면
			timer.stop(); //타이머 정지
			statusbar.setText("paused"); //게임의 상태를 나타내는 레이블에 "paused"를 출력
			bgm.stop();
		} else { //게임이 일시정지되지 않았다면
			timer.start(); //타이머 시작
			statusbar.setText(String.valueOf(numLinesRemoved)); //게임의 상태를 나타내는 레이블에 제거된 줄의 수를 출력
			bgm.play();
		}
		repaint(); //게임 보드를 다시 그림
	}
	
	private void restart(){ //게임을 재시작하는 메소드
		bgm.replay();
		start();
	}

	public void paint(Graphics g) { //게임 보드를 그리는 메소드
		super.paint(g); //부모 클래스의 paint()를 호출
		addBkgImg(g); // 이미지 위에 격자가 그려질 수 있도록 이미지를 먼저 그리고 격자를 그림
		drawBackground(g);
		drawGhost(g, curX, curY, curPiece.getShape());
		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) { //게임 보드의 모든 칸에 대해
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1); //게임 보드의 (j, BoardHeight - i - 1) 위치에 있는 블록의 모양을 가져옴
				if (shape != Tetrominoes.NoShape) //블록의 모양이 NoShape(없음)이 아니라면
					drawSquare(g, 0 + j * squareWidth(), boardTop + i * squareHeight(), shape); //블록을 그림
			}
		}

		if (curPiece.getShape() != Tetrominoes.NoShape) { //현재 블럭의 모양이 NoShape(없음)이 아니라면
			for (int i = 0; i < 4; ++i) { //현재 블럭의 모든 칸에 대해
				int x = curX + curPiece.x(i); //현재 블럭의 x좌표
				int y = curY - curPiece.y(i); //현재 블럭의 y좌표
				drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape()); //현재 블럭을 그림
			}
		}
	}

	private void dropDown() { //블록을 한 칸 아래로 이동하는 메소드
		int newY = curY; //현재 블록의 y좌표를 저장
		while (newY > 0) { //현재 블록의 y좌표가 0보다 작아질 때까지 반복
			if (!tryMove(curPiece, curX, newY - 1)) //블록을 한 칸 아래로 이동할 수 없다면
				break; //반복문 종료
			--newY; //현재 블록의 y좌표를 1만큼 감소
		}
		pieceDropped(); //블록을 한 칸 아래로 이동
	}

	private void oneLineDown() { //블록을 한 칸 아래로 이동하는 메소드
		if (!tryMove(curPiece, curX, curY - 1)) //블록을 한 칸 아래로 이동할 수 없다면
			pieceDropped(); //블록을 한 칸 아래로 이동
	}

	private void clearBoard() { //게임 보드를 초기화하는 메소드
		for (int i = 0; i < BoardHeight * BoardWidth; ++i) //게임 보드의 모든 칸에 대해
			board[i] = Tetrominoes.NoShape; //칸의 모양을 NoShape(없음)으로 설정
	}

	private void pieceDropped() { //블록을 한 칸 아래로 이동하는 메소드
		for (int i = 0; i < 4; ++i) { //현재 블록의 모든 칸에 대해
			int x = curX + curPiece.x(i); //현재 블록의 x좌표
			int y = curY - curPiece.y(i); //현재 블록의 y좌표
			board[(y * BoardWidth) + x] = curPiece.getShape(); //게임 보드의 (x, y) 위치에 현재 블록의 모양을 저장
		}

		removeFullLines(); //블록을 내리고 가득 찬 줄이 있으면 제거

		if (!isFallingFinished) //블록이 떨어지는 것이 끝나지 않았다면?
			newPiece(); //새로운 블록을 생성?
	}

	private void newPiece() { //새로운 블록을 생성하는 메소드
		curPiece.setRandomShape(); //새로운 블록의 모양을 랜덤으로 설정
		curX = BoardWidth / 2 + 1; //새로운 블록의 x좌표
		curY = BoardHeight - 1 + curPiece.minY(); //새로운 블록의 y좌표

		if (!tryMove(curPiece, curX, curY)) { //새로운 위치로 블록을 이동할 수 없다면
			curPiece.setShape(Tetrominoes.NoShape); //현재 블록의 모양을 NoShape(없음)으로 설정
			timer.stop(); //타이머 정지
			isStarted = false; //게임이 시작되었음을 나타내는 변수를 false로 설정
			statusbar.setText("game over"); //게임의 상태를 나타내는 레이블에 "game over"를 출력
			bgm.stop(); //배경음악 정지
		}
	}

	private boolean tryMove(Shape newPiece, int newX, int newY) { //새로운 위치(newX, newY)로 블록을 이동하려고 시도하는 메소드
		for (int i = 0; i < 4; ++i) { //새로운 블록의 모든 칸에 대해
			int x = newX + newPiece.x(i); //새로운 블록의 x좌표
			int y = newY - newPiece.y(i); //새로운 블록의 y좌표
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) //새로운 블록이 게임 보드의 범위를 벗어난다면
				return false; //false 반환
			if (shapeAt(x, y) != Tetrominoes.NoShape) //새로운 블록이 게임 보드의 다른 블록과 겹친다면 = 새로운 x, y에 블록이 존재한다면
				return false; //false 반환
		}

		curPiece = newPiece; //새로운 블록을 현재 블록으로 설정
		curX = newX; //현재 블록의 x좌표를 newX로 설정
		curY = newY; //현재 블록의 y좌표를 newY로 설정
		repaint(); //게임 보드를 다시 그림
		return true; //true 반환
	}

	private boolean ghostTryMove(Shape newPiece, int newX, int newY){
		for(int i=0; i<4; ++i){
			int x = newX + newPiece.x(i); //새로운 블록의 x좌표
			int y = newY - newPiece.y(i); //새로운 블록의 y좌표
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) //새로운 블록이 게임 보드의 범위를 벗어난다면
				return false; //false 반환
			if (shapeAt(x, y) != Tetrominoes.NoShape) //새로운 블록이 게임 보드의 다른 블록과 겹친다면 = 새로운 x, y에 블록이 존재한다면
				return false; //false 반환
		}
		repaint();
		return true;
	}

	private void removeFullLines() { //블록을 내리고 가득 찬 줄이 있으면 제거하는 메소드
		int numFullLines = 0; //가득 찬 줄의 수를 나타내는 변수

		for (int i = BoardHeight - 1; i >= 0; --i) { //게임 보드의 가장 아래쪽 줄부터 검사
			boolean lineIsFull = true; //가득 찬 줄인지를 나타내는 변수

			for (int j = 0; j < BoardWidth; ++j) { //가득 찬 줄인지 검사
				if (shapeAt(j, i) == Tetrominoes.NoShape) { //가득 찬 줄이 아니라면
					lineIsFull = false; //가득 찬 줄이 아님을 나타내는 변수를 false로 설정
					break; //반복문 종료
				}
			}

			if (lineIsFull) { //가득 찬 줄이라면
				++numFullLines; //가득 찬 줄의 수를 1만큼 증가
				for (int k = i; k < BoardHeight - 1; ++k) { //가득 찬 줄 위쪽의 모든 줄에 대해
					for (int j = 0; j < BoardWidth; ++j) //가득 찬 줄 위쪽의 모든 줄에 대해
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1); //가득 찬 줄 위쪽의 모든 줄을 한 칸씩 내림
				}
			}
		}

		if (numFullLines > 0) { //가득 찬 줄이 있다면
			numLinesRemoved += numFullLines; //제거된 줄의 수를 증가
			statusbar.setText(String.valueOf(numLinesRemoved)); //게임의 상태를 나타내는 레이블에 제거된 줄의 수를 출력
			isFallingFinished = true; //블록이 떨어지는 것이 끝났음을 나타내는 변수를 true로 설정
			curPiece.setShape(Tetrominoes.NoShape); //현재 블록의 모양을 NoShape(없음)으로 설정
			repaint(); //게임 보드를 다시 그림
		}
	}

	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) { //x, y는 블록 왼쪽 상단의 좌표, shape는 블록의 모양
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102), //색상 배열(RGB)
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0) };

		Color color = colors[shape.ordinal()]; //블록의 모양에 따라 색상을 정함

		g.setColor(color); //블록의 색상을 정함
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2); //블록 내부를 그림

		g.setColor(color.brighter()); //밝은 색상으로 설정
		g.drawLine(x, y + squareHeight() - 1, x, y); //블록의 왼쪽에 밝은 선을 그림(경계선)
		g.drawLine(x, y, x + squareWidth() - 1, y); //블록의 위쪽에 밝은 선을 그림

		g.setColor(color.darker()); //어두운 색상으로 설정
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1); //블록의 아래쪽에 어두운 선을 그림(그림자)
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x + squareWidth() - 1, y + 1); //블록의 오른쪽에 어두운 선을 그림
	}

	private void addBkgImg(Graphics g) {
		ImageIcon bkgImg = new ImageIcon("kr/ac/jbnu/se/tetris/image/background.jpg");
		Image bkgImg1 = bkgImg.getImage();
		g.drawImage(bkgImg1, 0, 0, getWidth(), getHeight(), this);
	}

	private void drawBackground(Graphics g) { //격자를 그리는 메소드
		// 가로선 그리기
		for (int y = 0; y <= BoardHeight * squareHeight(); y += squareHeight()) {
			g.setColor(Color.LIGHT_GRAY); // 격자 선 색상 설정
			g.drawLine(0, boardTop + y, BoardWidth * squareWidth(), boardTop + y);
		}

		// 세로선 그리기
		for (int x = 0; x <= BoardWidth * squareWidth(); x += squareWidth()) {
			g.setColor(Color.LIGHT_GRAY); // 격자 선 색상 설정
			g.drawLine(x, 0, x, BoardHeight * squareHeight());
		}
	}


	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
	
			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}
	
			if (isPaused) {
				return;
			}

			switch (keycode) {
				case KeyEvent.VK_LEFT:
					tryMove(curPiece, curX - 1, curY);
					break; // 왼쪽으로 이동 (왼쪽 화살표)
				case KeyEvent.VK_RIGHT:
					tryMove(curPiece, curX + 1, curY);
					break; // 오른쪽으로 이동 (오른쪽 화살표)
				case KeyEvent.VK_DOWN:
					tryMove(curPiece.rotateRight(), curX, curY);
					break; // 시계 방향 회전 (아래쪽 화살표)
				case KeyEvent.VK_UP:
					tryMove(curPiece.rotateLeft(), curX, curY);
					break; // 반시계 방향 회전 (위쪽 화살표)
				case KeyEvent.VK_SPACE:
					dropDown();
					break; // 하드 드롭 (Space)
				case 'd':
				case 'D':
					oneLineDown();
					break; // 소프트 드롭 (D)
				case 'r':
				case 'R':
					restart();
					break; // 게임 재시작 (R)
			}
		}
	}
		
}
