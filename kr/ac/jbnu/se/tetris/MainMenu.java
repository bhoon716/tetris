package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    
    private JButton normalModeButton = new JButton("normalMode");
    private JButton settingButton = new JButton("Setting");
    private JButton logoutButton = new JButton("Logout");
    
    public MainMenu(Tetris tetris){        
        add(normalModeButton); 
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
