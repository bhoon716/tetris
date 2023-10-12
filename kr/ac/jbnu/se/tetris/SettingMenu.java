package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class SettingMenu extends JPanel {
    private Tetris tetris;
    private JSlider volumeSlider;

    public SettingMenu(Tetris tetris) {
        this.tetris = tetris;

        setLayout(new BorderLayout());

        // 볼륨 조절 슬라이더 생성
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, tetris.getBgmVolume()); // 볼륨 범위를 0에서 100으로 설정하고 기본값을 50으로 설정
        volumeSlider.setMajorTickSpacing(10); // 주요 눈금 간격 설정
        volumeSlider.setPaintTicks(true); // 눈금 표시
        volumeSlider.setPaintLabels(true); // 눈금 라벨 표시
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = volumeSlider.getValue(); // 슬라이더의 값 가져오기 (0에서 100 사이)
                tetris.setBgmVolume(volume); // 볼륨 조절
            }
        });

        // 저장 버튼 생성
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 설정을 저장하고 설정 메뉴를 닫도록 구현
                //saveSettings(); // 설정 저장 메서드 호출
                tetris.switchPanel(new MainMenu(tetris)); // 메인 메뉴로 이동
            }
        });

        // 설정 메뉴에 슬라이더와 저장 버튼을 추가
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(volumeSlider);
        panel.add(saveButton);

        add(panel, BorderLayout.CENTER);
    }
}
