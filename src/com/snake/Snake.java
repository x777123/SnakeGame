package com.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<int[]> body;
    private char direction;
    private boolean growing;

    public Snake() {
        body = new ArrayList<>();
      
        body.add(new int[]{5, 5});
        body.add(new int[]{4, 5});
        body.add(new int[]{3, 5});
        direction = 'R';
        growing = false;
    }

    public void move() {
        int[] head = body.get(0);
        int newX = head[0];
        int newY = head[1];

        switch (direction) {
            case 'U' -> newY--;
            case 'D' -> newY++;
            case 'L' -> newX--;
            case 'R' -> newX++;
        }

        body.add(0, new int[]{newX, newY});

        if (!growing) {
           
            body.remove(body.size() - 1);
        } else {
            growing = false;
        }
    }

    public void grow() {
        growing = true;
    }

    public void setDirection(char newDirection) {
        
        if ((direction == 'U' && newDirection == 'D') ||
            (direction == 'D' && newDirection == 'U') ||
            (direction == 'L' && newDirection == 'R') ||
            (direction == 'R' && newDirection == 'L')) {
            return;
        }
        direction = newDirection;
    }

    public List<int[]> getBody() {
        return body;
    }

    public int[] getHead() {
        return body.get(0);
    }

    public void draw(Graphics g) {
        for (int i = 0; i < body.size(); i++) {
            int[] part = body.get(i);
            if (i == 0) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(new Color(45, 180, 0));
            }
            g.fillRect(part[0] * GamePanel.UNIT_SIZE,
                       part[1] * GamePanel.UNIT_SIZE,
                       GamePanel.UNIT_SIZE,
                       GamePanel.UNIT_SIZE);
        }
    }

    public boolean checkCollision() {
        int[] head = getHead();

       
        if (head[0] < 0 || head[0] >= GamePanel.SCREEN_WIDTH / GamePanel.UNIT_SIZE ||
            head[1] < 0 || head[1] >= GamePanel.SCREEN_HEIGHT / GamePanel.UNIT_SIZE) {
            return true;
        }

        
        for (int i = 1; i < body.size(); i++) {
            if (head[0] == body.get(i)[0] && head[1] == body.get(i)[1]) {
                return true;
            }
        }
        return false;
    }
}