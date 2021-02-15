package edu.school21.sockets.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;

public class Tank {
    private int hp;
    private Rectangle rectangle;
    private static final int HEIGHT = 105;
    private static final int WIDTH = 81;
    private static final float SPEED = 2f;
    private float posX;
    private float posY;

    public Tank(float x, float y) {
        posX = x;
        posY = y;
        hp = 100;
        rectangle = new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
    }

    public int getHp() {
        return hp;
    }

    public void moveRight()
    {
        posX += SPEED;
        rectangle.setRect((int) posX, (int) posY, WIDTH, HEIGHT);
    }

    public void moveLeft()
    {
        posX -= SPEED;
        rectangle.setRect((int) posX, (int) posY, WIDTH, HEIGHT);
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public boolean isGetShoot(Rectangle bullet)
    {
        if (rectangle.intersects(bullet)) {
            hp -= 5;
            return true;
        }
        return false;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public JSONObject toJson(int reverse)
    {
        JSONObject valuesObject = new JSONObject();
        valuesObject.put("hp", getHp());
        valuesObject.put("x", rectangle.x);
        valuesObject.put("y", reverse - rectangle.y);
        return valuesObject;
    }
}
