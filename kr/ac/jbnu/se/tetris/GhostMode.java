package kr.ac.jbnu.se.tetris;

import java.awt.Color;
import java.awt.Graphics;

public class GhostMode extends Board {

    public GhostMode(Tetris tetris) {
        super(tetris, "고스트 모드");
    }

    @Override
    protected void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        g.setColor(new Color(0, 0, 0, 0));
        g.drawRect(x, y, squareWidth(), squareHeight());
    }
}