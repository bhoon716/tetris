package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class TimeAttackMode extends Board{
    private final float TIME_LIMIT = 120.0f;
    private float remainingTime = TIME_LIMIT;
    private Timer taModeTimer;

    public TimeAttackMode(Tetris tetris) {
        super(tetris, "타임어택 모드");
        taModeTimer = new Timer(100, e -> checkTimeOver());
        taModeTimer.start();
    }

    // 시간이 초과되었는지 확인하는 메소드
    public void checkTimeOver() {
        if(isPaused)
            return;
        
        if(remainingTime <= 0) {
            stopGame();
            taModeTimer.stop();
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
        holdBlockLabel.setIcon(getHoldBolckImage());
	}

    @Override
	protected void backButtonListener() {
		stopGame();
        taModeTimer.stop();
		removePauseScreen();
		calcGameExp();
		tetris.setUserLevel();
		tetris.switchPanel(new MainMenu(tetris));
	}
}
