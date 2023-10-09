package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
    private AchievementList achievementList = new AchievementList();
    
    private Player player = new Player("test", 0, 0, 0, achievementList);
    private String userId;

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
        player.setUserId(userId);
    }

    public String getUserId() {
        return player.getUserId();
    }

    public int getUserMaxScore() {
        return player.getMaxScore();
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