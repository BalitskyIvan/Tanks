package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Border extends Pane {
    public Border(ImageView imageview, double x, double y) {
        ImageView imageView = imageview;
        imageView.setViewport(new Rectangle2D(0, 0,200, 30));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
