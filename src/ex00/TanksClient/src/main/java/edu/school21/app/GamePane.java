package edu.school21.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;

public class GamePane extends Pane {
    private HashMap<KeyCode, Boolean> keys;
    private static int movement = 0;
    private Enemy enemy;
    private ArrayList<EnemyBullet> listEnemyBullet;
    private Border enemyLifeBorder;
    private Life enemyLife;

    private Player player;
    private ArrayList<MyBullet> listMyBullet;
    private Border playerLifeBorder;
    private Life playerLife;

    public GamePane() {
        this.setId("pane");
        keys = new HashMap<>();

        enemy = new Enemy(359, 45);
        enemyLife = new Life(new ImageView(new Image(getClass().getResourceAsStream("/png/life.png"),
                190, 30, false, false)), 635, 10);
        enemyLifeBorder = new Border(new ImageView(new Image(getClass().getResourceAsStream("/png/border.png"),
                200, 30, false, false)), 630, 10);
        this.getChildren().add(enemy);
        this.getChildren().add(enemyLife);
        this.getChildren().add(enemyLifeBorder);

        player = new Player(359, 690);
        playerLife = new Life(new ImageView(new Image(getClass().getResourceAsStream("/png/life.png"),
                190, 30, false, false)), 10, 805);
        playerLifeBorder = new Border(new ImageView(new Image(getClass().getResourceAsStream("/png/border.png"),
                200, 30, false, false)), 5, 805);
        this.getChildren().add(player);
        this.getChildren().add(playerLife);
        this.getChildren().add(playerLifeBorder);
    }

    public void updateKeys()
    {
        if (isPressed(KeyCode.LEFT))
        {
            movement = 1;
        }
        if (isPressed(KeyCode.RIGHT))
        {
            movement = 2;
        }
        if (isPressed(KeyCode.SPACE))
        {
            movement = 3;
        }
    }

    public static int getMovement() {
        int mv = movement;
        movement = 0;
        return mv;
    }

    public void update(int myX, int enemyX, Integer[][] enemyBullet, Integer[][] myBullet) {
        player.moveX(myX);
        enemy.moveX(enemyX);
        for (int i = 0; i < enemyBullet.length; i++) {
            boolean isExist = false;
            for (EnemyBullet bullet : listEnemyBullet)
            {
                if (enemyBullet[0][i].equals(bullet.getIdentify())) {
                    isExist = true;
                    bullet.addBullet(enemyBullet[1][i], enemyBullet[2][i]);
                }
            }
            if (!isExist) {
                listEnemyBullet.add(new EnemyBullet(enemyBullet[0][i], enemyBullet[1][i], enemyBullet[2][i]));
            }
        }
        for (int i = 0; i < myBullet.length; i++) {
            boolean isExist = false;
            for (MyBullet bullet : listMyBullet)
            {
                if (myBullet[0][i].equals(bullet.getIdentify())) {
                    isExist = true;
                    bullet.addBullet(myBullet[1][i], myBullet[2][i]);
                }
            }
            if (!isExist) {
                listMyBullet.add(new MyBullet(myBullet[0][i], myBullet[1][i], myBullet[2][i]));
            }
        }
    }

    public boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    public HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }
}