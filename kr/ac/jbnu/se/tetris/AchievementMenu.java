package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AchievementMenu extends JPanel {
    private Tetris tetris;
    private AchievementList achievementList = new AchievementList();
    private JList<String> achievementJList = new JList<>(achievementList.getAchievement());
    private JButton backButton = new JButton("뒤로 가기");

    public AchievementMenu(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 패널 상단의 업적 제목 레이블
        JLabel titleLabel = new JLabel("업적", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 업적 목록 스크롤 패널
        JScrollPane scrollPane = new JScrollPane(achievementJList);
        achievementJList.setFocusable(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로 가기 버튼
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new MainMenu(tetris));
            }
        });
    }
}
