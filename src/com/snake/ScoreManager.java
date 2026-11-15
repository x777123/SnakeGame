package com.snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreManager {

    private int score;
    private int highScore;
    private final String HIGH_SCORE_FILE = "highscore.txt";

    public ScoreManager() {
        score = 0;
        highScore = loadHighScore();
    }

    public void addScore(int points) {
        score += points;
        if (score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }


    private int loadHighScore() {
        File file = new File(HIGH_SCORE_FILE);

        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader =
                new BufferedReader(new FileReader(file))) {

            String line = reader.readLine();

            if (line != null) {
                return Math.max(0, Integer.parseInt(line.trim()));
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Failed to load high score: " + e.getMessage());
        }

        return 0;
    }

    private void saveHighScore() {
        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(HIGH_SCORE_FILE))) {

            writer.write(String.valueOf(highScore));

        } catch (IOException e) {
            System.err.println("Failed to save high score: " + e.getMessage());
        }
    }
}  
