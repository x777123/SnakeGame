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

    public void generateNewFood(Snake snake) {
        if (snake == null) {
            generateNewFood();
            return;
        }

        int columns = GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE;
        int rows = GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE;

        if (snake.getBody().size() >= columns * rows) {
            return;
        }

        boolean onSnake;

        do {
            x = random.nextInt(columns);
            y = random.nextInt(rows);

            onSnake = false;

            for (int[] part : snake.getBody()) {
                if (part[0] == x && part[1] == y) {
                    onSnake = true;
                    break;
                }
            }
        } while (onSnake);
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