package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainMenu extends JPanel {

    private JPanel topPanel = new JPanel(new BorderLayout());
    private JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    private JPanel bottomPanel = new JPanel(new FlowLayout());
    private JButton normalModeButton = new JButton("기본 모드");
    private JButton sprintButton = new JButton("스프린트 모드");
    private JButton timeattackButton = new JButton("타임어택 모드");
    private JButton shadowModeButton = new JButton("고스트 모드");
    private JButton achievementButton = new JButton("업적 관리");
    private JButton rankingButton = new JButton("랭킹");
    private JButton settingButton = new JButton("설정");
    private JButton logoutButton = new JButton("로그아웃");
    


    public MainMenu(Tetris tetris) {
        setLayout(new FlowLayout());
        setBackground(Color.WHITE);

        // 타이틀 + 프로필 패널 (상단 패널)
        // 타이틀 라벨
        JLabel title = new JLabel("테트리스", SwingConstants.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 32));

        // 프로필 라벨
        JLabel profileLabel = new JLabel("ID : " + tetris.getUserId() + " | Level : " + tetris.getUserLevel() + " | 최고 기록 : " + tetris.getUserMaxScore(), SwingConstants.CENTER);
        profileLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        sendUserMaxScoreToServer();
        // 상단 패널에 타이틀과 프로필 라벨 추가
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 10, 50));
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(profileLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        
        
        // 게임 모드 버튼 (중앙 패널)
        // 기본 모드 버튼(+ 팝업 메뉴)
        JPopupMenu difficultyPopupMenu = new JPopupMenu();
        String[] difficulty = {"Easy", "Normal", "Hard", "Very Hard", "God"};
        for(String diff : difficulty){
            JMenuItem menuItem = new JMenuItem(diff);
            menuItem.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println("노말 모드(" + diff + ") 선택됨");
                    tetris.switchPanel(new Board(tetris, diff));
                }
            }); difficultyPopupMenu.add(menuItem);
        }
        normalModeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                difficultyPopupMenu.show(normalModeButton, normalModeButton.getWidth() / 2, normalModeButton.getHeight());
            }
        }); centerPanel.add(setStyledButton(normalModeButton, 200, 40));

        // 스프린트 모드 버튼
        sprintButton.addActionListener(e -> {
            System.out.println("스프린트 모드 선택됨");
            tetris.switchPanel(new SprintMode(tetris));
        }); centerPanel.add(setStyledButton(sprintButton, 200, 40));

        // 타임어택 모드 버튼
        timeattackButton.addActionListener(e -> {
            System.out.println("타임어택 모드 선택됨");
            tetris.switchPanel(new TimeAttackMode(tetris));
        }); centerPanel.add(setStyledButton(timeattackButton, 200, 50));

        // 그림자 모드 버튼
        shadowModeButton.addActionListener(e -> {
            System.out.println("고스트 모드 선택됨");
            tetris.switchPanel(new GhostMode(tetris));
        }); centerPanel.add(setStyledButton(shadowModeButton, 200, 50));

        // 중앙 패널에 게임 모드 버튼 추가
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createTitledBorder("게임 모드"));
        centerPanel.setPreferredSize(new Dimension(250, 200));
        add(centerPanel, BorderLayout.CENTER);


        // 업적 관리, 랭킹, 설정, 로그아웃 버튼 (하단 패널)
        // 업적 관리 버튼
        achievementButton.addActionListener(e -> {
            System.out.println("업적 관리 선택됨");
            tetris.switchPanel(new AchievementMenu(tetris));
        }); bottomPanel.add(setStyledButton(achievementButton, 75, 40));
        
        // 랭킹 버튼
        rankingButton.addActionListener(e -> {
            System.out.println("랭킹 선택됨");
            tetris.switchPanel(new Ranking(tetris));
        }); bottomPanel.add(setStyledButton(rankingButton, 75, 40));

        // 설정 버튼
        settingButton.addActionListener(e -> {
            System.out.println("설정 선택됨");
            tetris.switchPanel(new SettingMenu(tetris));
        }); bottomPanel.add(setStyledButton(settingButton, 75, 40));

        // 로그아웃 버튼
        logoutButton.addActionListener(e -> {
            System.out.println("로그아웃 선택됨");
            tetris.switchPanel(new Login(tetris));
        }); bottomPanel.add(setStyledButton(logoutButton, 75, 40));

        // 하단 패널에 버튼 추가
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(350, 60));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JButton setStyledButton(JButton button, int width, int height){
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
    // 사용자 ID와 최고 점수를 올바르게 전달 백엔ㄷ
    private void sendUserMaxScoreToServer() {
        String userId = tetris.getUserId();
        int maxScore = tetris.getUserMaxScore();

        // Use the existing sendScoreToServer method to send the user's max score
        boolean scoreSent = sendScoreToServer(userId, maxScore);

        if (scoreSent) {
            System.out.println("Max score sent to the server successfully.");
        } else {
            System.out.println("Failed to send max score to the server.");
        }
    }

    // Existing method to send the score to the server
    private boolean sendScoreToServer(String userId, int maxScore) {
        try {
            URL url = new URL("http://localhost:3000/score"); // Update with your server's endpoint URL

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // JSON format for the request body
            String jsonInputString = "{ \"user_id\": \"" + userId + "\", \"score\": " + maxScore + " }";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // Response code verification
            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
                return true; // Score sent successfully
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false; // Score sending failed
    }
}
