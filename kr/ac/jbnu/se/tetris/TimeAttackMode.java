package kr.ac.jbnu.se.tetris;

import java.awt.event.*;

public class TimeAttackMode extends Board {
    private static final int SPRINT_MODE_TIME_LIMIT = 120000; // 스프린트 모드의 제한 시간 (2분)
    private long startTime; // 게임 시작 시간을 저장하는 변수

    public TimeAttackMode(Tetris tetris) {
        super(tetris, "스프린트 모드"); // 부모 클래스 생성자 호출
        startTime = System.currentTimeMillis(); // 게임 시작 시간 기록
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - startTime;

        if (elapsedTime >= SPRINT_MODE_TIME_LIMIT) {
            stopGame();
        }
    }
}
