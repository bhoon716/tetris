package kr.ac.jbnu.se.tetris;

import java.awt.*;
import javax.swing.*;

public class Tetris extends JFrame {
    private static AchievementList achievementList = new AchievementList();
    
    public static Player player = new Player("test", 0, 0, 1, 0, 1, achievementList);
    private String userId;

    private int bgmVolume = 100;

    public Tetris() {
        setSize(400, 400);
        setTitle("Tetris");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);
        setLayout(new BorderLayout());
        add(new MainMenu(this));
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

    public void setUserMaxScore(int score) {
        player.setMaxScore(score);
    }

    public int getUserMaxCombo() {
        return player.getMaxCombo();
    }

    public void setUserMaxCombo(int combo) {
        player.setMaxCombo(combo);
    }

    public int getUserLevel() {
        return player.getLevel();
    }

    public void setUserLevel(){
        player.setLevel();
    }

    public int getUserExp() {
        return player.getExp();
    }

    public void addUserExp(int exp) {
        player.addExp(exp);
    }

    public int getUserItemReserves() {
        return Player.getItemReserves();
    }

    public void switchPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
        panel.requestFocus();
    }

    public void setBgmVolume(int volume) {
        this.bgmVolume = volume;
    }

    public int getBgmVolume() {
        return bgmVolume;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tetris game = new Tetris();
            game.setLocationRelativeTo(null);
            game.setVisible(true);
        });
    }
}