package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class SprintMode extends Board{
    private final int LINE_TO_CLEAR = 40; // 스프린트 모드에서 클리어해야 하는 라인 수

    public SprintMode(Tetris tetris){
        super(tetris, "스프린트 모드");
    }

    @Override
	protected void newPiece() { //새로운 블록을 생성하는 메소드
		curPiece.setRandomShape(); //새로운 블록의 모양을 랜덤으로 설정
		curX = BoardWidth / 2; //새로운 블록의 x좌표
		curY = BoardHeight - 1 + curPiece.minY(); //새로운 블록의 y좌표

		if (!tryMove(curPiece, curX, curY)) { //새로운 위치로 블록을 이동할 수 없다면
			curStatus = "Game Over";
            stopGame();
		}

        if(getLineCount() >= LINE_TO_CLEAR){
            stopGame();
            String clearTime = String.format("Clear Time - %.2f 초", (System.currentTimeMillis() - super.startTime) / 1000.0); // 클리어 타임을 xx.yy 초 형식으로 저장
            JOptionPane.showMessageDialog(null, clearTime,"Game Clear!", JOptionPane.INFORMATION_MESSAGE, null);
            
            // 최단 클리어 타임을 player 객체에 저장하는 코드 작성
        }
	}

    // 남은 줄을 출력하는 메소드
    @Override
    protected void updateScorePanel() {
        statusLabel.setText(curStatus);
        scoreLabel.setText("남은 줄 : " + (LINE_TO_CLEAR - numLinesRemoved));
        comboLabel.setText("Combo : " + combo);
    }
}