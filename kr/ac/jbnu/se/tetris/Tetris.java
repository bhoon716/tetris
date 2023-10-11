package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
    private AchievementList achievementList = new AchievementList();
<<<<<<< HEAD
    
    private Player player = new Player("test", 0, 0, 0, achievementList);
=======
    private Bgm bgm;
>>>>>>> 1b3495f991f33aef2a949b8cc44a355dcc1f32f9
    private String userId;

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