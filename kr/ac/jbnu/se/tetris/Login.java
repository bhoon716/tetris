package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel {
    Tetris tetris;

    private JPanel loginPanel = new JPanel();
    private JPanel signUpPanel = new JPanel();
    private JButton loginButton = new JButton("Login");
    private JButton signUpButton = new JButton("Sign Up");
    private JButton submitButton = new JButton("Submit");
    private JButton backButton = new JButton("Back");
    private JTextField loginIdField = new JTextField(10);
    private JPasswordField loginPwField = new JPasswordField(10);
    private JTextField signUpIdField = new JTextField(10);
    private JPasswordField signUpPwField = new JPasswordField(10);
    private JPasswordField signUpConfirmPwField = new JPasswordField(10);

    public Login(Tetris tetris){
        this.tetris = tetris;
        addKeyListener(new TAdapter()); //키보드 입력을 받을 수 있도록 설정

        add(loginPanel);
        add(signUpPanel);

        loginPanel.setLayout(new GridLayout(3, 2));
        loginPanel.add(new JLabel("ID:"));
        loginPanel.add(loginIdField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(loginPwField);
        loginPanel.add(loginButton);
        loginPanel.add(signUpButton);

        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tetris.switchPanel(new MainMenu(tetris));
            }
        });

        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                loginPanel.setVisible(false);
                signUpPanel.setVisible(true);
            }
        });

        signUpPanel.setLayout(new GridLayout(4, 2));
        signUpPanel.add(new JLabel("ID:"));
        signUpPanel.add(signUpIdField);
        signUpPanel.add(new JLabel("Password:"));
        signUpPanel.add(signUpPwField);
        signUpPanel.add(new JLabel("Confirm Password:"));
        signUpPanel.add(signUpConfirmPwField);
        signUpPanel.add(submitButton);
        signUpPanel.add(backButton);
        signUpPanel.setVisible(false);

        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                signUpPanel.setVisible(false);
                loginPanel.setVisible(true);
            }
        });

        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                signUpPanel.setVisible(false);
                loginPanel.setVisible(true);
            }
        });
    }

    class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			if (keycode == 'a' || keycode == 'A') {
				System.out.println("login");
			}
        }
    }
}
