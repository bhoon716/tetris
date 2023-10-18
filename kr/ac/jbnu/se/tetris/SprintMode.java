package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class SprintMode extends Board{
    private final int LINE_TO_CLEAR = 40;

    private Timer spModeTimer;
    private float runningTime = 0;

    public SprintMode(Tetris tetris){
        super(tetris, "스프린트 모드");
        spModeTimer = new Timer(100, e -> checkClear());
        spModeTimer.start();
    }

    public void checkClear(){
        if (isPaused)
            return;

        if(getNumLinesRemoved() >= LINE_TO_CLEAR){
            stopGame();
            spModeTimer.stop();
            String clearTime = String.format("Clear Time - %.1f 초", runningTime);
            JOptionPane.showMessageDialog(null, clearTime,"Game Clear!", JOptionPane.INFORMATION_MESSAGE, null);
            gameOverScreen();
        }
        else{
            runningTime += 0.1f;
            updateScorePanel();
        }
    }

    @Override
    protected void updateScorePanel() {
        scoreLabel.setText("남은 줄 : " + (LINE_TO_CLEAR - getNumLinesRemoved()));
        comboLabel.setText("Combo : " + combo);
        nextPieceLabel.setIcon(getNextPieceImage());
        holdBlockLabel.setIcon(getHoldBolckImage());
    }

    @Override
    protected void backButtonListener() {
        stopGame();
        spModeTimer.stop();
        removePauseScreen();
        calcGameExp();
        tetris.setUserLevel();
        tetris.switchPanel(new MainMenu(tetris));
    }
}