package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Item{
    public Board board;
    private Random random = new Random();

    public Item(Board board) {
        this.board = board;
    }

    // 아이템 사용 메소드
    public void useItem() {
        if (Player.getItemReserves() > 0) {
            addScore();
            removeAllBlocks();
            Player.subItemReserves();
        }
    }

    // 점수를 추가하는 메소드
    private void addScore() {
        int blockCount = countBlocks() * 25;
        board.score += blockCount; // 블록 수만큼 점수 추가
    }

    // 보드의 모든 블록을 제거하는 메소드
    private void removeAllBlocks() {
        for (int i = 0; i < board.BoardHeight * board.BoardWidth; ++i) {
            board.board[i] = Tetrominoes.NoShape;
        }

        board.repaint(); // 게임 보드 다시 그리기
        board.updateScorePanel(); // 스코어 패널 업데이트하기
    }

    // 현재 보드에 있는 블록 수를 세는 메소드
    private int countBlocks() {
        int blockCount = 0;

        for (int i = 0; i < board.BoardHeight * board.BoardWidth; ++i) {
            if(board.board[i] != Tetrominoes.NoShape)
                blockCount++;
        }

        return blockCount;
    }
}