package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Login extends JPanel {
    private Tetris tetris;

    private JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    private JPanel signUpPanel = new JPanel(new GridLayout(6, 2, 10, 10));
    private JLabel loginId = new JLabel("ID : ");
    private JLabel loginPw = new JLabel("Password : ");
    private JLabel signUpId = new JLabel("ID : ");
    private JLabel signUpPw = new JLabel("Password : ");
    private JButton loginButton = new JButton("Login");
    private JButton signUpButton = new JButton("Sign Up");
    private JButton submitButton = new JButton("Submit");
    private JButton backButton = new JButton("Back");
    private JButton checkDuplicateButton = new JButton("ID 중복 확인");
    private JTextField loginIdField = new JTextField(12);
    private JPasswordField loginPwField = new JPasswordField(12);
    private JTextField signUpIdField = new JTextField(12);
    private JPasswordField signUpPwField = new JPasswordField(12);
    private JPasswordField signUpConfirmPwField = new JPasswordField(12);

    public Login(Tetris tetris){
        this.tetris = tetris;
        add(loginPanel);
        add(signUpPanel);

        loginPanel.setBorder(BorderFactory.createTitledBorder("로그인"));
        loginPanel.add(loginId);
        loginPanel.add(loginIdField);
        loginPanel.add(loginPw);
        loginPanel.add(loginPwField);
        loginPanel.add(setStyledButton(loginButton));
        loginPanel.add(setStyledButton(signUpButton));

        signUpPanel.setBorder(BorderFactory.createTitledBorder("회원가입"));
        signUpPanel.add(signUpId);
        signUpPanel.add(signUpIdField);
        signUpPanel.add(new JLabel()); // 빈 라벨
        signUpPanel.add(setStyledButton(checkDuplicateButton));
        signUpPanel.add(signUpPw);
        signUpPanel.add(signUpPwField);
        signUpPanel.add(new JLabel("Password 확인 : "));
        signUpPanel.add(signUpConfirmPwField);
        signUpPanel.add(setStyledButton(submitButton));
        signUpPanel.add(setStyledButton(backButton));

        signUpPanel.setVisible(false);

        loginButton.addActionListener(e -> login());
        signUpButton.addActionListener(e -> showSignUpPanel());

        checkDuplicateButton.addActionListener(e -> checkDuplicate());
        submitButton.addActionListener(e -> submitSignUp());
        backButton.addActionListener(e -> showLoginPanel());
    }

     // 로그인 로직
    private void login() {
        String id = loginIdField.getText();
        char[] pw = loginPwField.getPassword();
        if (id.isEmpty() || pw.length == 0) {
            JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean loginSuccess = checkLoginOnServer(id, new String(pw));
            if (loginSuccess) {
                // 로그인 성공
                tetris.setUserId(id);
                tetris.switchPanel(new MainMenu(tetris));
            } else {
                // 로그인 실패
                JOptionPane.showMessageDialog(null, "로그인 실패 - 아이디 또는 비밀번호가 일치하지 않음", "로그인 실패", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 회원가입 패널 표시
    private void showSignUpPanel() {
        clearFields();
        loginPanel.setVisible(false);
        signUpPanel.setVisible(true);
    }

    // ID 중복 확인
    private void checkDuplicate() {
        String id = signUpIdField.getText();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID를 입력해주세요.", "ID 중복 확인 실패", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean isDuplicate = checkDuplicateIdOnServer(id);

            if (isDuplicate) {
                JOptionPane.showMessageDialog(null, "이미 사용 중인 ID입니다.", "ID 중복 확인 실패", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "사용 가능한 ID입니다.", "ID 중복 확인 성공", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // 회원가입 로직
    private void submitSignUp() {
        String id = signUpIdField.getText();
        char[] pw = signUpPwField.getPassword();
        char[] confirmPw = signUpConfirmPwField.getPassword();
        if(id.isEmpty() || pw.length == 0 || confirmPw.length == 0){
            JOptionPane.showMessageDialog(null, "ID와 Password를 입력해주세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
        } else if(!Arrays.equals(pw, confirmPw)){
            JOptionPane.showMessageDialog(null, "Password가 일치하지 않습니다.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
        } else {
            // 회원가입 성공
            // 여기에서 서버로 회원가입 정보를 전송하고 응답을 처리해야 합니다.
            boolean registrationSuccess = sendRegistrationInfoToServer(id, new String(pw));
            
            if (registrationSuccess) {
                JOptionPane.showMessageDialog(null, "회원가입에 성공했습니다.", "회원가입 성공", JOptionPane.INFORMATION_MESSAGE);
                showLoginPanel();
            } else {
                JOptionPane.showMessageDialog(null, "회원가입 실패. 다시 시도하세요.", "회원가입 실패", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 로그인 패널 표시
    private void showLoginPanel() {
        clearFields();
        signUpPanel.setVisible(false);
        loginPanel.setVisible(true);
    }

    // id, pw, confirmPw 필드 초기화
    private void clearFields() {
        loginIdField.setText("");
        loginPwField.setText("");
        signUpIdField.setText("");
        signUpPwField.setText("");
        signUpConfirmPwField.setText("");
    }
    // 서버로 회원가입 정보를 전송하는 함수
    private boolean sendRegistrationInfoToServer(String id, String password) {
        try {
            URL url = new URL("http://localhost:3000/signup"); // 백엔드 서버의 회원가입 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // 회원가입 정보를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\", \"password\": \"" + password + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 201) {
                return true; // 회원가입 성공
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false; // 회원가입 실패
    }
    
    // 서버에서 ID 중복을 확인하는 함수
    private boolean checkDuplicateIdOnServer(String id) {
        try {
            URL url = new URL("http://localhost:3000/checkDuplicate"); // 백엔드 서버의 중복 확인 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // ID를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                // 중복된 ID가 있는 경우
                return true;
            } else if (responseCode == 204) {
                // 중복된 ID가 없는 경우
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
    
    // 서버에서 로그인 확인
    private boolean checkLoginOnServer(String id, String password) {
        try {
            URL url = new URL("http://localhost:3000/login"); // 백엔드 서버의 로그인 엔드포인트 URL로 수정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // ID와 Password를 JSON 형식으로 전송
            String jsonInputString = "{\"id\": \"" + id + "\", \"password\": \"" + password + "\"}";
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(input, 0, input.length);
            }

            // 응답 코드 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                return true; // 로그인 성공
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false; // 로그인 실패
    }

    private JButton setStyledButton(JButton button){
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
