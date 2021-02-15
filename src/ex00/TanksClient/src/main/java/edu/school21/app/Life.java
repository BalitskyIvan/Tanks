package edu.school21.app;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Life extends Pane {
    private ImageView imageView;

    public Life(ImageView imageView, double x, double y) {
        this.imageView = imageView;
        this.imageView.setViewport(new Rectangle2D(0, 0,200, 30));
        getChildren().addAll(imageView);
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.imageView.setFitWidth(200);
    }

    public void reduce(double hp) {
        imageView.setFitWidth(2 * hp);
    }

//    public double getWigth() {
//        return imageView.getFitWidth();
//    }
}
