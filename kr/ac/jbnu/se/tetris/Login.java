package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JPanel {
    private Tetris tetris;

    private JPanel loginPanel = new JPanel();
    private JPanel signUpPanel = new JPanel();
    private JButton loginButton = new JButton("Login");
    private JButton signUpButton = new JButton("Sign Up");
    private JButton submitButton = new JButton("Submit");
    private JButton backButton = new JButton("Back");
    private JButton checkDuplicateButton = new JButton("Check Duplicate");
    private JTextField loginIdField = new JTextField(15);
    private JPasswordField loginPwField = new JPasswordField(15);
    private JTextField signUpIdField = new JTextField(15);
    private JPasswordField signUpPwField = new JPasswordField(15);
    private JPasswordField signUpConfirmPwField = new JPasswordField(15);

    public Login(Tetris tetris) {
        this.tetris = tetris;

        setLayout(new CardLayout());
        add(loginPanel, "login");
        add(signUpPanel, "signup");

        createLoginPanel();
        createSignUpPanel();

        CardLayout cardLayout = (CardLayout) getLayout();
        cardLayout.show(this, "login");
    }

    private void createLoginPanel() {
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        loginPanel.add(loginIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        loginPanel.add(loginPwField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JPanel buttonPanel = createButtonPanel(loginButton, signUpButton);
        loginPanel.add(buttonPanel, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tetris.switchPanel(new MainMenu(tetris));
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getLayout();
                cardLayout.show(Login.this, "signup");
            }
        });
    }

    private void createSignUpPanel() {
        signUpPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        signUpPanel.add(new JLabel("ID:"), gbc);

        gbc.gridx = 1;
        signUpPanel.add(signUpIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signUpPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        signUpPanel.add(signUpPwField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signUpPanel.add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        signUpPanel.add(signUpConfirmPwField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel buttonPanel = createButtonPanel(checkDuplicateButton, submitButton, backButton);
        signUpPanel.add(buttonPanel, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getLayout();
                cardLayout.show(Login.this, "login");
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getLayout();
                cardLayout.show(Login.this, "login");
            }
        });

        checkDuplicateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idToCheck = signUpIdField.getText();
                JOptionPane.showMessageDialog(Login.this, "아이디 중복 확인: " + idToCheck);
            }
        });
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel buttonPanel = new JPanel(new FlowLayout());
        for (JButton button : buttons) {
            buttonPanel.add(button);
        }
        return buttonPanel;
    }
}
