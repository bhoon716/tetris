package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Bgm {
    private Clip audioClip;
    private String audioFilePath = "kr\\ac\\jbnu\\se\\tetris\\bgm.wav";
    public Bgm() {
        try {
            // 오디오 파일을 로드합니다.
            File audioFile = new File(audioFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // 오디오 포맷을 얻어옵니다.
            AudioFormat format = audioStream.getFormat();

            // 데이터 라인을 열고 시작합니다.
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // 배경 음악을 무한 반복하도록 설정
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        // 배경 음악 재생을 시작합니다.
        audioClip.start();
    }

    public void stop() {
        // 배경 음악 재생을 중지합니다.
        audioClip.stop();
    }
}
