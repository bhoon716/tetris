package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelection extends JPanel {
    Tetris tetris;

    public ModeSelection(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("모드를 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 32));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        // 각 모드 버튼
        String[] modes = {"Easy", "Normal", "Hard", "Very Hard", "God"};
        for (String mode : modes) {
            JButton modeButton = new JButton(mode);
            modeButton.addActionListener(new ModeButtonListener(mode));
            centerPanel.add(setStyledButton(modeButton, 200, 40));
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("뒤로 가기");
        backButton.setBackground(new Color(70, 130, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        backButton.addActionListener(e -> {
            System.out.println("뒤로 가기 버튼 선택됨");
            tetris.switchPanel(new MainMenu(tetris));
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private class ModeButtonListener implements ActionListener {
        private String selectedMode;

        public ModeButtonListener(String mode) {
            this.selectedMode = mode;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(selectedMode + " 모드 선택됨");
            tetris.switchPanel(new Ranking(tetris, selectedMode));
        }
    }

    private JButton setStyledButton(JButton button, int width, int height) {
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
