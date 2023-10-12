package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

public class GhostMode extends Board {

    public GhostMode(Tetris tetris) {
        super(tetris, "고스트 모드");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }
    }

    @Override
    protected void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        g.setColor(new Color(0, 0, 0, 0));
        g.drawRect(x, y, squareWidth(), squareHeight());
    }

    @Override
    protected void newPiece() {
        curPiece.setRandomShape();
        curX = BoardWidth / 2;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curStatus = "Game Over";
            stopGame();
        }
    }
}