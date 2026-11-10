package com.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static final int UNIT_SIZE = 25;

    private enum GameState {
        MENU, RUNNING, GAME_OVER
    }

    private Snake snake;
    private Food food;
    private ScoreManager scoreManager;
    private GameState state;
    private Timer timer;
    private int delay = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());

        scoreManager = new ScoreManager();
        state = GameState.MENU;
    }

    public void startGame() {
        snake = new Snake();
        food = new Food();
        scoreManager.resetScore();
        delay = 100;
        state = GameState.RUNNING;

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(delay, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        switch (state) {
            case MENU -> drawMenu(g);
            case RUNNING -> drawRunning(g);
            case GAME_OVER -> drawGameOver(g);
        }
    }

    private void drawMenu(Graphics g) {
      
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Snake Game", SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2 - 80);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press SPACE to Start", SCREEN_WIDTH / 2 - 120, SCREEN_HEIGHT / 2 + 20);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("High Score: " + scoreManager.getHighScore(), SCREEN_WIDTH / 2 - 70, SCREEN_HEIGHT / 2 + 80);
    }

    private void drawRunning(Graphics g) {
       
        g.setColor(new Color(40, 40, 40));
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }

        food.draw(g);
        snake.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + scoreManager.getScore(), 20, 30);
        g.drawString("High Score: " + scoreManager.getHighScore(), 20, 60);
    }

    private void drawGameOver(Graphics g) {
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + scoreManager.getScore(), 20, 30);
        g.drawString("High Score: " + scoreManager.getHighScore(), 20, 60);

        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Game Over", SCREEN_WIDTH / 2 - 140, SCREEN_HEIGHT / 2 - 30);

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 22));
        g.drawString("Press SPACE to Restart", SCREEN_WIDTH / 2 - 130, SCREEN_HEIGHT / 2 + 40);
    }

    public void checkFood() {
        int[] head = snake.getHead();
        if (head[0] == food.getX() && head[1] == food.getY()) {
            snake.grow();
            food.generateNewFood();
            scoreManager.addScore(10);
            SoundManager.playEat();

            if (delay > 50) {
                delay -= 2;
                timer.setDelay(delay);
            }
        }
    }

    public void checkCollision() {
        if (snake.checkCollision()) {
            state = GameState.GAME_OVER;
            timer.stop();
            SoundManager.playGameOver();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (state == GameState.RUNNING) {
            snake.move();
            checkFood();
            checkCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (state == GameState.RUNNING) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT -> snake.setDirection('L');
                    case KeyEvent.VK_RIGHT -> snake.setDirection('R');
                    case KeyEvent.VK_UP -> snake.setDirection('U');
                    case KeyEvent.VK_DOWN -> snake.setDirection('D');
                }
            } else {
               
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGame();
                }
            }
        }
    }
}