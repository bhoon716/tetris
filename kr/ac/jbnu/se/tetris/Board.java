package kr.ac.jbnu.se.tetris;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import static java.awt.event.KeyEvent.VK_ESCAPE;

public class Board extends JPanel implements ActionListener {
	private Tetris tetris;
	private Bgm bgm;

	protected final int BoardWidth = 10; //게임 보드의 가로 칸 수
	protected final int BoardHeight = 22; //게임 보드의 세로 칸 수

	protected Timer timer; //게임의 속도를 조절하는 타이머
	protected boolean isFallingFinished = false; //현재 블록이 다 떨어졌는지 확인하는 변수
	protected boolean isStarted = false; //게임이 시작되었는지를 나타내는 변수
	protected boolean isPaused = false; //게임이 일시정지되었는지를 나타내는 변수
	protected int numLinesRemoved = 0; //제거된 줄의 수를 나타내는 변수
	protected int curX = 0; //현재 블록의 x좌표
	protected int curY = 0; //현재 블록의 y좌표
	protected Shape curPiece; //현재 블록을 나타내는 객체
	protected Tetrominoes[] board = new Tetrominoes[BoardWidth * BoardHeight]; //게임 보드를 나타내는 배열 생성
	protected int boardTop = (int) getSize().getHeight() - BoardHeight * squareHeight(); //게임 보드의 상단 좌표
	protected int combo = 0;
	protected int score = 0;
	protected String curStatus = "";
	private String modeName = "";
	protected JLabel scoreLabel = new JLabel("Score : " + score);
	protected JLabel statusLabel = new JLabel(curStatus);
	protected JLabel comboLabel = new JLabel("Combo : " + combo);
	protected JPanel statusPanel = new JPanel();
	private JPanel nextBlockPanel = new JPanel();
	private JPanel holdBlockPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JButton backButton = new JButton("Back");
	private JButton itemReservesButton = new JButton(Player.getItemReserves() + "개");
	private Item item = new Item(this);


	public Board(Tetris tetris, String modeName) {
		this.tetris = tetris;
		this.modeName = modeName;
		this.curStatus = modeName;
		setFocusable(true);  // Allow the Board component to receive focus
		requestFocusInWindow();
		setLayout(new BorderLayout()); //보더 레이아웃으로 설정
		setPreferredSize(new Dimension(250, 400));
		curPiece = new Shape(); //현재 블록을 생성(NoShape)
		bgm = new Bgm();
		timer = new Timer(getTimerDelay(modeName), this); //타이머 생성(400ms마다 actionPerformed()를 호출)
		bgm.setVolume(tetris.getBgmVolume());
		bgm.play(); //배경음악 재생
		timer.start(); //타이머 시작
		clearBoard(); //게임 보드를 초기화
		addKeyListener(new TAdapter()); //키보드 입력을 받을 수 있도록 설정
		start();

		statusPanel.setPreferredSize(new Dimension(190, 400));
		statusPanel.setLayout(new FlowLayout());
		statusPanel.setBackground(Color.ORANGE);
		add(statusPanel, BorderLayout.EAST);

		nextBlockPanel.setPreferredSize(new Dimension(100, 100));
		nextBlockPanel.setBackground(Color.YELLOW);

		holdBlockPanel.setPreferredSize(new Dimension(100, 100));
		holdBlockPanel.setBackground(Color.GREEN);

		statusPanel.add(nextBlockPanel, BorderLayout.NORTH);
		statusPanel.add(holdBlockPanel, BorderLayout.CENTER);
		statusPanel.add(rightPanel, BorderLayout.SOUTH);
		statusPanel.add(backButton, BorderLayout.SOUTH);
		if(Player.getItemReserves() > 0) statusPanel.add(itemReservesButton, BorderLayout.SOUTH);

		rightPanel.setPreferredSize(new Dimension(120, 50));
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(Color.RED);
		rightPanel.add(statusLabel, BorderLayout.NORTH);
		rightPanel.add(scoreLabel, BorderLayout.CENTER);
		rightPanel.add(comboLabel, BorderLayout.SOUTH);
		backButton.setPreferredSize(new Dimension(100, 30));
		backButton.addActionListener(e -> backButtonListener());
		itemReservesButton.addActionListener(e -> {
			item.useItem();
			if(Player.getItemReserves() == 0) itemReservesButton.setVisible(false);
			setFocusable(true);  // Set the focus on the game panel
			requestFocusInWindow(); // Request focus for the game panel
		});
		itemReservesButton.setPreferredSize(new Dimension(30, 30));
		try {
			Image img = ImageIO.read(getClass().getResource("resources/itemIcon.png"));
			itemReservesButton.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //타이머가 400ms마다 호출하는 메소드
		if (isFallingFinished) { //블록이 떨어지는 것이 끝났다면
			isFallingFinished = false; //끝났음을 나타내는 변수를 false로 설정
			newPiece(); //새로운 블록을 생성

		} else { //떨어지는 것이 끝나지 않았다면
			oneLineDown(); //블록을 한 칸 아래로 이동
		}
	}

	public void backButtonListener() {
		bgm.stop(); //배경음악 정지
		removePauseScreen();
		calcGameExp();
		Tetris.player.setLevel();
		tetris.switchPanel(new MainMenu(tetris)); // 메인 메뉴 화면으로 전환
	}

	protected void drawGhost(Graphics g, int curX, int curY, Tetrominoes shape) { //x, y는 블록 왼쪽 상단의 좌표, shape는 블록의 모양
		if (curPiece.getShape() == Tetrominoes.NoShape)
			return;

		int newY = curY;
		while (newY > 0) {
			if (!ghostTryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.getX(i);
			int y = newY - curPiece.getY(i);
			g.fillRect(0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), squareWidth(), squareHeight());
		}
	}

	private int getTimerDelay(String modeName) {
		switch (modeName) {
			case "Easy":
			case "스프린트 모드":
			case "타임어택 모드":
			case "그림자 모드":
				return 500;
			case "Normal":
				return 300;
			case "Hard":
				return 200;
			case "Very Hard":
				return 80;
			case "God":
				return 30;
			default:
				return 400;
		}
	}

	protected int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth / 2; //게임 보드의 가로 공간을 BoardWidth로 나눈 값(한 칸의 가로 길이)
	}

	protected int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight; //게임 보드의 세로 공간을 BoardHeight로 나눈 값(한 칸의 세로 길이)
	}

	protected Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x]; //게임 보드의 (x, y) 위치에 있는 블록의 모양을 반환
	}

	public void start() { //게임을 시작하는 메소드
		if (isPaused) //게임이 일시정지되었다면
			return; //메소드 종료

		isStarted = true; //게임이 시작되었음을 나타내는 변수를 true로 설정
		isFallingFinished = false; //블록이 떨어지는 것이 끝났음을 나타내는 변수를 false로 설정
		numLinesRemoved = 0; //제거된 줄의 수를 0으로 설정
		clearBoard(); //게임 보드를 초기화
		newPiece(); //새로운 블록을 생성
		timer.start(); //타이머 시작
	}

	private void pause() { //게임을 일시정지하는 메소드
		if (!isStarted) //게임이 시작되지 않았다면
			return; //메소드 종료

		isPaused = !isPaused; //게임이 일시정지되었음을 나타내는 변수를 반전
		if (isPaused) { //게임이 일시정지되었다면
			timer.stop(); //타이머 정지
			curStatus = modeName + " (Paused)";
			bgm.stop();
			pauseScreen();
		} else { //게임이 일시정지되지 않았다면
			timer.start(); //타이머 시작
			curStatus = modeName;
			bgm.play();
		}
		repaint(); //게임 보드를 다시 그림
	}

	public void resume() {
		pause();
		timer.start();
		curStatus = modeName;
		bgm.play();
		removePauseScreen();
		setFocusable(true);  // Set the focus on the game panel
		requestFocusInWindow(); // Request focus for the game panel
	}

	private void restart() {
		int choice = JOptionPane.showConfirmDialog(this, "게임을 재시작하시겠습니까?", "게임 재시작 확인", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			score = 0;
			curStatus = modeName;
			bgm.replay();
			removePauseScreen();
			isPaused = false;
			start();
			setFocusable(true);  // Set the focus on the game panel
			requestFocusInWindow(); // Request focus for the game panel
		}
	}

	protected void stopGame() {
		curPiece.setShape(Tetrominoes.NoShape); //현재 블록의 모양을 NoShape(없음)으로 설정
		isStarted = false;
		timer.stop(); //타이머 정지
		bgm.stop();
	}

	public void paint(Graphics g) { //게임 보드를 그리는 메소드
		super.paint(g); //부모 클래스의 paint()를 호출
		addBkgImg(g);
		drawGridPattern(g);
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
				int x = curX + curPiece.getX(i); //현재 블럭의 x좌표
				int y = curY - curPiece.getY(i); //현재 블럭의 y좌표
				drawSquare(g, 0 + x * squareWidth(), boardTop + (BoardHeight - y - 1) * squareHeight(), curPiece.getShape()); //현재 블럭을 그림
			}
		}

		updateScorePanel();
	}

	protected void updateScorePanel() {
		statusLabel.setText(curStatus);
		scoreLabel.setText("Score : " + score);
		comboLabel.setText("Combo : " + combo);
	}

	public int getNumLinesRemoved() { //제거된 줄의 수를 반환하는 메소드
		return numLinesRemoved;
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

	protected void oneLineDown() { //블록을 한 칸 아래로 이동하는 메소드
		if (!tryMove(curPiece, curX, curY - 1)) //블록을 한 칸 아래로 이동할 수 없다면
			pieceDropped(); //블록을 한 칸 아래로 이동
	}

	protected void clearBoard() { //게임 보드를 초기화하는 메소드s
		for (int i = 0; i < BoardHeight * BoardWidth; ++i) //게임 보드의 모든 칸에 대해
			board[i] = Tetrominoes.NoShape; //칸의 모양을 NoShape(없음)으로 설정
	}

	private void pieceDropped() { //블록을 한 칸 아래로 이동하는 메소드
		for (int i = 0; i < 4; ++i) { //현재 블록의 모든 칸에 대해
			int x = curX + curPiece.getX(i); //현재 블록의 x좌표
			int y = curY - curPiece.getY(i); //현재 블록의 y좌표
			board[(y * BoardWidth) + x] = curPiece.getShape(); //게임 보드의 (x, y) 위치에 현재 블록의 모양을 저장
		}

		removeFullLines(); //블록을 내리고 가득 찬 줄이 있으면 제거

		if (!isFallingFinished) //블록이 떨어지는 것이 끝나지 않았다면
			newPiece(); //새로운 블록을 생성
	}

	protected void newPiece() { //새로운 블록을 생성하는 메소드
		curPiece.setRandomShape(); //새로운 블록의 모양을 랜덤으로 설정
		curX = BoardWidth / 2; //새로운 블록의 x좌표
		curY = BoardHeight - 1 + curPiece.minY(); //새로운 블록의 y좌표

		if (!tryMove(curPiece, curX, curY)) { //새로운 위치로 블록을 이동할 수 없다면
			curStatus = "Game Over";
			stopGame(); //게임 정지
		}
	}

	protected boolean tryMove(Shape newPiece, int newX, int newY) { //새로운 위치(newX, newY)로 블록을 이동하려고 시도하는 메소드
		for (int i = 0; i < 4; ++i) { //새로운 블록의 모든 칸에 대해
			int x = newX + newPiece.getX(i); //새로운 블록의 x좌표
			int y = newY - newPiece.getY(i); //새로운 블록의 y좌표
			if ((x < 0) && (y >= 0 || y <= BoardHeight)) //새로운 위치가 왼쪽 벽을 넘어간다면
				tryMove(newPiece, newX + 1, newY); //새로운 블록을 오른쪽으로 한 칸 이동
			if ((x >= BoardWidth) && (y >= 0 || y <= BoardHeight)) //새로운 위치가 오른쪽 벽을 넘어간다면
				tryMove(newPiece, newX - 1, newY); //새로운 블록을 왼쪽으로 한 칸 이동
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

	private boolean ghostTryMove(Shape newPiece, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.getX(i); //새로운 블록의 x좌표
			int y = newY - newPiece.getY(i); //새로운 블록의 y좌표
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) //새로운 블록이 게임 보드의 범위를 벗어난다면
				return false; //false 반환
			if (shapeAt(x, y) != Tetrominoes.NoShape) //새로운 블록이 게임 보드의 다른 블록과 겹친다면 = 새로운 x, y에 블록이 존재한다면
				return false; //false 반환
		}
		repaint();
		return true;
	}

	protected void removeFullLines() {
		int numFullLines = 0;
		int comboScore = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			combo += numFullLines;
			numLinesRemoved += numFullLines;
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		} else {
			combo = 0;
		}

		if (combo > 1) {
			comboScore = 50 * combo;
		}
		score += 100 * numFullLines + comboScore;
	}

	protected void drawSquare(Graphics g, int x, int y, Tetrominoes shape) { //x, y는 블록 왼쪽 상단의 좌표, shape는 블록의 모양
		Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102), new Color(102, 204, 102), //색상 배열(RGB)
				new Color(102, 102, 204), new Color(204, 204, 102), new Color(204, 102, 204), new Color(102, 204, 204),
				new Color(218, 170, 0)};

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

	protected void addBkgImg(Graphics g) {
		ImageIcon bkgImg = new ImageIcon("kr/ac/jbnu/se/tetris/resources/backGround.jpg");
		Image bkgImg1 = bkgImg.getImage();
		g.drawImage(bkgImg1, 0, 0, getWidth() / 2, getHeight(), this);
	}

	protected void drawGridPattern(Graphics g) { //격자를 그리는 메소드
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

	public void helpScreen() {
		String msg =
				"다양한 난이도와 모드를 지원하는 테트리스 게임입니다.\n\n" +
						"[모드 설명]\n" +
						"스프린트: 40줄을 최대한 빠른 시간 안에 지우는 모드\n" +
						"타임어택: 2분 동안 많은 줄을 제거하는 모드\n" +
						"고스트: 고스트만 보이는 모드\n\n" +
						"[단축키]\n" +
						"방향 키: 블록 회전\n" +
						"ESC: 일시정지\n" +
						"Space: 하드 드롭\n" +
						"D: 소프트 드롭";

		JOptionPane.showMessageDialog(this, msg, "도움말", JOptionPane.INFORMATION_MESSAGE);
	}

	public void pauseScreen() {
		if (isPaused) {
			JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
			JLayeredPane layeredPane = topFrame.getLayeredPane();

			// Create a semi-transparent panel to dim the screen
			JPanel dimPanel = new JPanel();
			dimPanel.setBackground(new Color(0, 0, 0, 142));
			dimPanel.setBounds(0, 0, (int) getSize().getWidth() / 2, (int) getSize().getHeight());

			JLabel pausedLabel = new JLabel("PAUSED");
			pausedLabel.setFont(new Font("배달의민족한나AirOTF", Font.BOLD | Font.ITALIC, 30));
			pausedLabel.setForeground(Color.WHITE);
			pausedLabel.setHorizontalAlignment(JLabel.CENTER);
			pausedLabel.setVerticalAlignment(JLabel.CENTER);

			JButton resumeButton = new JButton("돌아가기");
			JButton restartButton = new JButton("다시 시작");
			JButton helpButton = new JButton("도움말");

			resumeButton.addActionListener(e -> {
				resume();
				backButton.setFocusable(false);
			});
			restartButton.addActionListener(e -> restart());
			helpButton.addActionListener(e -> helpScreen());

			resumeButton.setBounds(50, 200, 100, 30);
			restartButton.setBounds(50, 250, 100, 30);
			helpButton.setBounds(50, 300, 100, 30);

			dimPanel.add(pausedLabel);
			dimPanel.add(resumeButton);
			dimPanel.add(restartButton);
			dimPanel.add(helpButton);

			layeredPane.add(dimPanel, JLayeredPane.PALETTE_LAYER);
		}
		if (!isPaused) removePauseScreen();
	}

	public void removePauseScreen() {
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		JLayeredPane layeredPane = topFrame.getLayeredPane();
		Component[] components = layeredPane.getComponentsInLayer(JLayeredPane.PALETTE_LAYER);

		for (Component component : components) {
			if (component instanceof JPanel) {
				JPanel panel = (JPanel) component;
				panel.removeAll();
				layeredPane.remove(panel);
			}
		}
		layeredPane.validate();
		layeredPane.repaint();
	}

	public void calcGameExp(){
		int gameExp = this.score/10;
		Tetris.player.setExp(gameExp + Tetris.player.getExp());
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();

			if (keycode == VK_ESCAPE) {
				if (isPaused) {
					return;
				}
				pause();
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
			}
		}
	}
}
