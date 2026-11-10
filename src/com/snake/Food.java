package com.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Food {

    private int x;
    private int y;
    private Random random = new Random();

    public Food() {
        generateNewFood();
    }

    public void generateNewFood() {
        x = random.nextInt(GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE);
        y = random.nextInt(GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x * GamePanel.UNIT_SIZE,
                   y * GamePanel.UNIT_SIZE,
                   GamePanel.UNIT_SIZE,
                   GamePanel.UNIT_SIZE);
    }
}