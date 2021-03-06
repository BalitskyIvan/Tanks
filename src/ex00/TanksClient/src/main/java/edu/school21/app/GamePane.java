package edu.school21.app;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.awt.*;
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
    private javafx.scene.control.TextArea textArea;
    private FlowPane flowPane;
    private Fail fail;

    public GamePane() {
        this.setId("pane");
        keys = new HashMap<>();
        listEnemyBullet = new ArrayList<>();
        listMyBullet = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            MyBullet myBullet = new MyBullet(i, -10, 10);
            listMyBullet.add(myBullet);
            this.getChildren().add(listMyBullet.get(i));
            listEnemyBullet.add(new EnemyBullet(i, -10, 10));
            this.getChildren().add(listEnemyBullet.get(i));

        }
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
        textArea = new javafx.scene.control.TextArea("");
        textArea.setEditable(false);
        textArea.setPrefColumnCount(10);

        flowPane = new FlowPane(Orientation.HORIZONTAL, 10, 10, textArea);
        flowPane.setAlignment(Pos.CENTER_LEFT);
        flowPane.setVisible(false);
        this.getChildren().add(flowPane);
        this.getChildren().add(player);
        this.getChildren().add(playerLife);
        this.getChildren().add(playerLifeBorder);

        fail = new Fail(-512, -512);

        this.getChildren().add(fail);
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

    public void update(int myX, int enemyX, int enSize, int mySize, Integer[][] enemyBullet, Integer[][] myBullet, int myHp, int enemyHp) {
        player.moveX(myX);
        enemy.moveX(enemyX);
        ArrayList<Integer> usedEnemyBullets = new ArrayList<>();
        ArrayList<Integer> usedMyBullets = new ArrayList<>();
        for (int i = 0; i < enSize; i++) {
            boolean isExist = false;
            usedEnemyBullets.add(enemyBullet[0][i]);
            for (EnemyBullet bullet : listEnemyBullet)
            {
                if (enemyBullet[0][i].equals(bullet.getIdentify())) {
                    isExist = true;
                    bullet.addBullet(enemyBullet[1][i], enemyBullet[2][i]);
                    bullet.setUsed(true);
                }
            }
            if (!isExist) {
                for (EnemyBullet bullet : listEnemyBullet)
                {
                    if (!bullet.isUsed()) {
                        bullet.setUsed(true);
                        bullet.setId(enemyBullet[0][i]);
                        bullet.addBullet(enemyBullet[1][i], enemyBullet[2][i]);
                    }
                }
            }
        }

        for (int i = 0; i < mySize; i++) {
            boolean isExist = false;
            usedMyBullets.add(myBullet[0][i]);
            for (MyBullet bullet : listMyBullet)
            {
                if (myBullet[0][i].equals(bullet.getIdentify())) {
                    isExist = true;
                    bullet.addBullet(myBullet[1][i], myBullet[2][i]);
                    bullet.setUsed(true);
                }
            }
            if (!isExist) {
                for (MyBullet bullet : listMyBullet)
                {
                    if (!bullet.isUsed()) {
                        bullet.setUsed(true);
                        bullet.setId(myBullet[0][i]);
                        bullet.addBullet(myBullet[1][i], myBullet[2][i]);
                    }
                }
            }
        }
        for (EnemyBullet enemyBullet1 : listEnemyBullet)
        {
            boolean isUsed = false;
            for (Integer bullet : usedEnemyBullets)
            {
                if (enemyBullet1.getIdentify() == bullet)
                    isUsed = true;
            }
            if (!isUsed && enemyBullet1.isUsed()) {
                enemyBullet1.setUsed(false);
                enemyBullet1.addBullet(-20, -20);
            }
        }
        for (MyBullet enemyBullet1 : listMyBullet)
        {
            boolean isUsed = false;
            for (Integer bullet : usedMyBullets)
            {
                if (enemyBullet1.getIdentify() == bullet)
                    isUsed = true;
            }
            if (!isUsed && enemyBullet1.isUsed()) {
                enemyBullet1.setUsed(false);
                enemyBullet1.addBullet(-20, -20);
            }
        }
        enemyLife.reduce(enemyHp);
        playerLife.reduce(myHp);
    }

    public void stop(int shots, int hits, int misses)
    {
        fail.move(180, 120);
        textArea.setText("STATISTICS:\nSHOTS: " + shots + "\nHITS: " + hits + "\nMISSES: " + misses + "\n");
        flowPane.setVisible(true);
    }
    public boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    public HashMap<KeyCode, Boolean> getKeys() {
        return keys;
    }
}
