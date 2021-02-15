package edu.school21.app;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class Fail extends Pane {
    public Fail(double x, double y) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/png/fail.png")));
        imageView.setViewport(new Rectangle2D(0, 0,512, 512));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);

    }

    public void move(int x, int y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
