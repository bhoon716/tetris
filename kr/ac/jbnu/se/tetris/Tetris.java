package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
    private AchievementList achievementList = new AchievementList();
    
    private String userId;
    private int userMaxScore = 100000;
    private int userMaxCombo = 0;
    private int userLevel = 0;

    public Tetris() {
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(new BorderLayout());
        add(new Login(this));
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserMaxScore() {
        return userMaxScore;
    }

    public void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
        panel.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setLocationRelativeTo(null);
            game.setVisible(true);
        });
    }
}