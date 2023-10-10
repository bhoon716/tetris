package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
    private AchievementList achievementList = new AchievementList();
    private Bgm bgm;
    private String userId;
    private int userMaxScore = 100000;
    private int userMaxCombo = 0;
    private int userLevel = 0;

    public Tetris() {
        initializeUI();
        bgm = new Bgm();
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
    // BGM 인스턴스를 반환하는 메서드
    public Bgm getBgm() {
        return bgm;
    }
    // BGM 볼륨을 조절하는 메서드
    public void adjustVolume(float volume) {
        bgm.setVolume(volume);
    }
    public void restartBgm() {
        bgm.replay();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setLocationRelativeTo(null);
            game.setVisible(true);
        });
    }
}