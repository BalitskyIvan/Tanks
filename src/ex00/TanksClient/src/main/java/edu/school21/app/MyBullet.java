package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MyBullet extends Pane {
    private Integer id;
    private boolean isUsed = false;

    public MyBullet(int id, double x, double y) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/png/playerBullet.png")));
        imageView.setViewport(new Rectangle2D(0, 0,600, 12));
        getChildren().addAll(imageView);
        this.id = id;
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public void addBullet(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public Integer getIdentify() {
        return id;
    }
}