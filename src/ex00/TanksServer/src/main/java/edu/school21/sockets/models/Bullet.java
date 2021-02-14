package edu.school21.sockets.models;

import java.awt.*;

public class Bullet {
    private Rectangle rectangle;
    private static final int HEIGHT = 5;
    private static final int WIDTH = 5;
    private static final float SPEED = 0.1f;
    private float posX;
    private float posY;

    public Bullet(float x, float y) {
        posX = x;
        posY = y;
        rectangle = new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }

    public void moveUp()
    {
        posY += SPEED;
        rectangle.setRect((int) posX, (int) posY, WIDTH, HEIGHT);
    }

    public void moveDown()
    {
        posY -= SPEED;
        rectangle.setRect((int) posX, (int) posY, WIDTH, HEIGHT);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
