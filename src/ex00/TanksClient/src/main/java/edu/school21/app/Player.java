package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {
    private final double speed;

    public Player(double x, double y) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/png/player.png")));
        imageView.setViewport(new Rectangle2D(0, 0,81, 105));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.speed = 3;
    }

    public void moveX(int x) {
        this.setTranslateX(x);
    }
}
