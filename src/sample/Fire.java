package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Fire {

    ImageView missileView;
    Pane p;

    public Fire(Pane p, int position) {
        Timeline tlB;
        this.p = p;
        Image missile = new Image("/Images/missile.png");
        missileView = new ImageView(missile);
        missileView.setLayoutX(position + 35); //position of ship
        missileView.setLayoutY(720);
        p.getChildren().add(missileView);

        Duration dB = new Duration(5);
        KeyFrame fB = new KeyFrame(dB, e -> {
            missileView.setY(missileView.getY() - 5);

        });

        tlB = new Timeline(fB);
        tlB.setCycleCount(Animation.INDEFINITE);
        tlB.play();
    }


    //
//    Image missile = new Image("/Images/missile.png");
//    missileView = new ImageView(missile);
//    missileView.setFitWidth(30);
//    missileView.setFitHeight(50);
}
