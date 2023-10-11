package kr.ac.jbnu.se.tetris;

import javax.swing.*;

public class TimeAttackMode extends Board{
    private static final int SPRINT_MODE_TIME_LIMIT = 10000; // 스프린트 모드의 제한 시간 (10초)

    private Timer timer; // 타임어택 모드의 타이머
    private long startTime; // 게임 시작 시간을 저장하는 변수
    private boolean isGameOver = false; // 게임이 종료되었는지 여부를 나타내는 변수

    public TimeAttackMode(Tetris tetris) {
        super(tetris, "타임어택 모드"); // 부모 클래스 생성자 호출
        startTime = System.currentTimeMillis(); // 게임 시작 시간 기록
        timer = new Timer(100, e -> checkTimeOver()); // 1초마다 checkTimeOver() 메소드를 호출하는 타이머 생성
        timer.start(); // 타이머 시작
    }

    public void checkTimeOver() {
        if (!isGameOver && System.currentTimeMillis() - startTime > SPRINT_MODE_TIME_LIMIT) { // 게임이 종료되지 않았고, 제한 시간을 초과한 경우
            stopGame(); // 게임 종료
            timer.stop();
            String removedLines = Integer.toString(getNumLinesRemoved()); // 타임어택 모드의 점수를 저장
            JOptionPane.showMessageDialog(null, removedLines + "줄 제거!", "Time Over!", JOptionPane.INFORMATION_MESSAGE, null);
        }
    }
}
