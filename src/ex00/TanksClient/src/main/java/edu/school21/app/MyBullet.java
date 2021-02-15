package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MyBullet extends Pane {
    private Integer id;

    public MyBullet(int id, double x, double y) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/png/myBullet.png")));
        imageView.setViewport(new Rectangle2D(0, 0,6, 12));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void addBullet(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public Integer getIdentify() {
        return id;
    }
}