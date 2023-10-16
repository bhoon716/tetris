package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class TimeAttackMode extends Board{
    private final float TIME_LIMIT = 120.0f; // 타임어택 모드의 제한 시간

    private float remainingTime = TIME_LIMIT;
    private Timer taModetimer; // 타임어택 모드의 타이머

    public TimeAttackMode(Tetris tetris) {
        super(tetris, "타임어택 모드"); // 부모 클래스 생성자 호출
        taModetimer = new Timer(100, e -> checkTimeOver()); // 0.1초마다 checkTimeOver() 메소드 호출
        taModetimer.start(); // 타이머 시작
    }

    // 시간이 초과되었는지 확인하는 메소드
    public void checkTimeOver() {
        if(isPaused)
            return;
        
        if(remainingTime <= 0) {
            stopGame();
            taModetimer.stop();
            JOptionPane.showMessageDialog(null, numLinesRemoved + "줄 제거!", "Time Over!", JOptionPane.INFORMATION_MESSAGE, null);
            gameOverScreen();
        }
        else {
            remainingTime -= 0.1f;
            updateScorePanel();
        }
    }

    // 제거한 줄을 출력하는 메소드
    @Override
    protected void updateScorePanel() {
		scoreLabel.setText("제거한 줄 : " + numLinesRemoved);
		comboLabel.setText("Combo : " + combo);
        nextPieceLabel.setIcon(getNextPieceImage());
	}

    @Override
	public void backButtonListener() {
		stopGame();
        taModetimer.stop();
		removePauseScreen();
		calcGameExp();
		Tetris.player.setLevel();
		tetris.switchPanel(new MainMenu(tetris)); // 메인 메뉴 화면으로 전환
	}
}
