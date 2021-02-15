package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy extends Pane {
    public Enemy(double x, double y) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/png/enemy.png")));
        imageView.setViewport(new Rectangle2D(0, 0,81, 105));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }

    public void moveX(int x)
    {
        this.setTranslateX(x);
    }
}
