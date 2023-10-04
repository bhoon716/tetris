package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    
    private JPanel modePanel = new JPanel();
    private JButton easyModeButton = new JButton("easyModeButton");
    private JButton normalModeButton = new JButton("normalMode");
    private JButton hardModeButton = new JButton("hardModeButton");
    private JButton veryHardModeButton = new JButton("veryHardModeButton");
    private JButton godModeButton = new JButton("godModeButton");
    private JButton settingButton = new JButton("Setting");
    private JButton logoutButton = new JButton("Logout");
    
    public MainMenu(Tetris tetris){    
        setLayout(new FlowLayout());
        modePanel.setLayout(new GridLayout(3, 2));
        modePanel.add(normalModeButton); 
        modePanel.add(hardModeButton);
        modePanel.add(veryHardModeButton);
        modePanel.add(easyModeButton);
        modePanel.add(godModeButton);
        add(modePanel);
        add(settingButton);
        add(logoutButton);

        normalModeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                tetris.switchPanel(new Board(tetris));
            }
        });

        settingButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
            }
        });
    }
}
