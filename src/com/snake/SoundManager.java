package com.snake;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

    public static void play(String fileName) {
        try {
            File file = new File("sounds/" + fileName);
            if (!file.exists()) {
                
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            
            System.out.println("音效播放失败: " + fileName);
        }
    }

    public static void playEat() {
        play("eat.wav");
    }

    public static void playGameOver() {
        play("gameover.wav");
    }
}