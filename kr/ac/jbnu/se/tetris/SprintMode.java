package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class SprintMode extends Board{
    private final int LINE_TO_CLEAR = 1; // 스프린트 모드에서 클리어해야 하는 라인 수

    private Timer spModeTimer; // 스프린트 모드의 타이머
    private float runningTime = 0;

    public SprintMode(Tetris tetris){
        super(tetris, "스프린트 모드");
        spModeTimer = new Timer(100, e -> checkClear()); // 0.1초마다 checkClear() 메소드 호출
        spModeTimer.start(); // 타이머 시작
    }

    public void checkClear(){
        if (isPaused)
            return;

        if(numLinesRemoved >= LINE_TO_CLEAR){
            stopGame();
            spModeTimer.stop();
            String clearTime = String.format("Clear Time - %.1f 초", runningTime); // 클리어 타임을 xx.yy 초 형식으로 저장
            JOptionPane.showMessageDialog(null, clearTime,"Game Clear!", JOptionPane.INFORMATION_MESSAGE, null);
            
            // 최단 클리어 타임을 player 객체에 저장하는 코드 작성
        }
        else{
            runningTime += 0.1f;
            updateScorePanel();
        }
    }

    // 남은 줄을 출력하는 메소드
    @Override
    protected void updateScorePanel() {
        statusLabel.setText(curStatus);
        scoreLabel.setText("남은 줄 : " + (LINE_TO_CLEAR - numLinesRemoved));
        comboLabel.setText("Combo : " + combo);
        nextPieceLabel.setIcon(getNextPieceImage());
    }
}