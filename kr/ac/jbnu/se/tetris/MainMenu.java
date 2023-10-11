package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    private Tetris tetris;
    private JButton[] modeButtons = new JButton[5];
    private JButton sprintButton = new JButton("스프린트 모드");
    private JButton timeattackButton = new JButton("타임어택 모드");
    private JButton pentatrominoesButton = new JButton("펜타트로미노 모드");
    private JButton achievementButton;
    private JButton settingButton;
    private JButton logoutButton;
    private JLabel profileLabel;
    
    public MainMenu(Tetris tetris) {
        this.tetris = tetris;
        setLayout(new BorderLayout());
        
        // 게임 모드 선택 버튼 배열 초기화
        String[] modeNames = {"쉬운 모드", "보통 모드", "어려운 모드", "매우 어려운 모드", "갓 모드"};
        JPanel modePanel = new JPanel(new GridLayout(4, 3, 10, 10)); // 간격 추가
        modePanel.setBorder(BorderFactory.createTitledBorder("게임 모드 선택"));
        for (int i = 0; i < modeButtons.length; i++) {
            modeButtons[i] = createModeButton(modeNames[i]);
            modePanel.add(modeButtons[i]);
        }

        sprintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new SprintMode(tetris));
            }
        });
        
        timeattackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new TimeAttackMode(tetris));
            }
        });

        pentatrominoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        modePanel.add(sprintButton);
        modePanel.add(timeattackButton);
        modePanel.add(pentatrominoesButton);

        // 아이콘 추가
        ImageIcon achievementIcon = new ImageIcon("achievement_icon.png"); // 아이콘 이미지 파일 경로로 수정
        ImageIcon settingIcon = new ImageIcon("setting_icon.png"); // 아이콘 이미지 파일 경로로 수정
        ImageIcon logoutIcon = new ImageIcon("logout_icon.png"); // 아이콘 이미지 파일 경로로 수정

        achievementButton = createIconButton("업적 관리", achievementIcon, buttonListener);
        settingButton = createIconButton("설정", settingIcon, buttonListener);
        logoutButton = createIconButton("로그아웃", logoutIcon, logoutListener);

        // 프로필 정보 표시 레이블 초기화
        profileLabel = new JLabel("ID : " + tetris.getUserId() + " | 최고 기록 : " + tetris.getUserMaxScore());
        profileLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // 버튼 패널 추가
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 간격 추가
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(achievementButton);
        buttonPanel.add(settingButton);
        buttonPanel.add(logoutButton);

        // 모드 선택과 버튼 패널을 중앙에 배치
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(modePanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH); // 하단에 배치

        // 상단 로고 또는 메시지 패널 추가
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel title = new JLabel("테트리스", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24)); // 폰트 및 크기 설정
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(profileLabel, BorderLayout.CENTER);

        // 전체 패널에 추가
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    // 게임 모드 선택 버튼 생성 메서드
    private JButton createModeButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 60)); // 버튼 크기 조정
        button.addActionListener(modeButtonListener);
        return button;
    }

    // 게임 모드 선택 버튼 리스너
    ActionListener modeButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String modeName = clickedButton.getText();
            System.out.println(modeName + " 선택됨");
            // 선택한 모드에 따라 게임 시작 로직 추가
            tetris.switchPanel(new Board(tetris, modeName));
        }
    };

    // 버튼 클릭 리스너
    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            String buttonText = clickedButton.getText();
            // 버튼마다의 동작 추가
            if (buttonText.equals("업적 관리")) {
                tetris.switchPanel(new Achievement(tetris));
            } else if (buttonText.equals("설정")) {
                // 설정 화면으로 이동하는 코드 추가
            	 tetris.switchPanel(new SettingMenu(tetris));
            }
        }
    };

    // 로그아웃 버튼 리스너
    ActionListener logoutListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int choice = JOptionPane.showConfirmDialog(MainMenu.this, "로그아웃 하시겠습니까?", "로그아웃 확인", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                tetris.switchPanel(new Login(tetris));
            }
        }
    };

    // 아이콘과 텍스트가 함께 표시되는 버튼 생성 메서드
    private JButton createIconButton(String text, ImageIcon icon, ActionListener listener) {
        JButton button = new JButton(text, icon);
        button.setPreferredSize(new Dimension(200, 40)); // 버튼 크기 조정
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.addActionListener(listener);
        return button;
    }
}
