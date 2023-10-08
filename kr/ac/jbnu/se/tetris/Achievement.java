package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Achievement extends JPanel {
    private Tetris tetris;
    private AchievementList achievementList = new AchievementList();
    private JList<String> achievementJList = new JList<>(achievementList.getAchievement());
    private JButton backButton = new JButton("뒤로 가기");

    public Achievement(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 패널 상단의 업적 제목 레이블
        JLabel titleLabel = new JLabel("업적", SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 업적 목록 스크롤 패널
        JScrollPane scrollPane = new JScrollPane(achievementJList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로 가기 버튼
        backButton.setPreferredSize(new Dimension(100, 40));
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new MainMenu(tetris));
            }
        });
    }
}
