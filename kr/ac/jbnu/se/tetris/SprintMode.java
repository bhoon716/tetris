package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class SprintMode extends Board{
    private final int LINE_TO_CLEAR = 1; // 스프린트 모드에서 클리어해야 하는 라인 수
    private long startTime; // 게임 시작 시간

    public SprintMode(Tetris tetris){
        super(tetris, "스프린트 모드");
        startTime = System.currentTimeMillis(); // 게임 시작 시간 저장
    }

    @Override
	protected void newPiece() { //새로운 블록을 생성하는 메소드
		curPiece.setRandomShape(); //새로운 블록의 모양을 랜덤으로 설정
		curX = BoardWidth / 2; //새로운 블록의 x좌표
		curY = BoardHeight - 1 + curPiece.minY(); //새로운 블록의 y좌표

		if (!tryMove(curPiece, curX, curY)) { //새로운 위치로 블록을 이동할 수 없다면
			curPiece.setShape(Tetrominoes.NoShape); //현재 블록의 모양을 NoShape(없음)으로 설정
			curStatus = "Game Over";
            stopGame();
		}

        if(isGameCleared()){
            curPiece.setShape(Tetrominoes.NoShape);
            stopGame();
            double cTime = (double) ((System.currentTimeMillis() - startTime) / 1000.0); // 클리어 타임 계산
            String clearTime = String.format("Clear Time - %.2f 초", cTime); // 클리어 타임을 xx.yy 초 형식으로 저장
            JOptionPane.showMessageDialog(null, clearTime,"Game Clear!", JOptionPane.INFORMATION_MESSAGE);
            // 최단 클리어 타임을 player 객체에 저장
        }
	}

    public boolean isGameCleared(){
        return getLineCount() >= LINE_TO_CLEAR;
    }
}