package kr.ac.jbnu.se.tetris;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class AchievementMenu extends JPanel {
    private Tetris tetris;
    private List<AchievementItem> achievementList = new ArrayList<>();

    public AchievementMenu(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());

        // 상단 패널
        JLabel titleLabel = new JLabel("업적", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        titleLabel.setBackground(new Color(70, 130, 180));
        titleLabel.setOpaque(true);
        titleLabel.setForeground(Color.ORANGE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(7, 0, 7, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 중앙 패널
        JPanel centerPanel = new JPanel(new GridLayout(10, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        setAchievement();

        // 생성된 업적 목록을 스크롤 패널에 추가
        for (AchievementItem item : achievementList) {
            JLabel itemLabel = new JLabel(item.getName() + " : " + item.getDescription());
            itemLabel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
            itemLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16)); 
            itemLabel.setForeground(new Color(70, 130, 180));
            centerPanel.add(itemLabel);
        }

        // 스크롤 패널로 중앙 패널 감싸기
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로 가기 버튼
        JButton backButton = new JButton("뒤로 가기");
        backButton.setPreferredSize(new Dimension(100, 40));
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new MainMenu(tetris));
            }
        });
    }

    // 업적 목록 생성
    public void setAchievement() {
        if (tetris.getUserMaxScore() >= 5000)
            achievementList.add(new AchievementItem("초보자", "최고 점수 5000점 달성"));
        if (tetris.getUserMaxScore() >= 10000)
            achievementList.add(new AchievementItem("중수", "최고 점수 10000점 달성"));
        if (tetris.getUserMaxScore() >= 15000)
            achievementList.add(new AchievementItem("고수", "최고 점수 50000점 달성"));
        if (tetris.getUserMaxScore() >= 20000)
            achievementList.add(new AchievementItem("마스터", "최고 점수 100000점 달성"));
        if (tetris.getUserMaxScore() >= 30000)
            achievementList.add(new AchievementItem("전설", "최고 점수 250000점 달성"));
    }

    // 업적 아이템 클래스
    private class AchievementItem {
        private String name;
        private String description;

        public AchievementItem(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }
}
