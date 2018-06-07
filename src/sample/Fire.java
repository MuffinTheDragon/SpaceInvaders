package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class Fire {

    Timeline tlB;
    ImageView missileView;
//    boolean bullet = true;
    Pane p;
//    Rectangle rec = null;
//    int score;

    public Fire(Pane p, int position) {
        this.p = p;
        Image missile = new Image("/Images/missile.png");
        missileView = new ImageView(missile);
        missileView.setLayoutX(position + 35); //position of ship
        missileView.setLayoutY(720);
        p.getChildren().add(missileView);

        Duration dB = new Duration(5);
        KeyFrame fB = new KeyFrame(dB, e -> {
//            if (rec != null) {
                missileView.setY(missileView.getY() - 5);
//            }
        });

        tlB = new Timeline(fB);
        tlB.setCycleCount(Animation.INDEFINITE);
        tlB.play();
    }

//    public void bulletHit() {
//        p.getChildren().remove(rec);
//        rec = null;
//        try {
//            Media media = new Media(getClass().getResource("/Sounds/explosion.wav").toURI().toString());
//            MediaPlayer player = new MediaPlayer(media);
//            player.play();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public int getScore() {
//        return score;
//    }
//
//    public boolean hit(ImageView alienGrid[]) {
//        for (int i = 0; i < alienGrid.length; i++) {
//            if (rec != null && alienGrid[i] != null) {
//                if ((rec != null && rec.getX() < alienGrid[i].getX() + alienGrid[i].getFitWidth()) && rec.getX() + rec.getWidth() > alienGrid[i].getX()
//                        && rec.getY() < alienGrid[i].getY() + alienGrid[i].getFitHeight()
//                        && rec.getHeight() + rec.getY() > alienGrid[i].getY()) {
//                    alienGrid[i].setVisible(false);
//                    alienGrid[i] = null;
//                    bulletHit();
//                    bullet = false;
//                    score += 100;
//
//                }
//            }
//        }
//
//        if (rec != null) {
//            if (rec.getY() < 0 - rec.getHeight() - 1) {
//                bulletHit();
//            }
//        }
//
//        return bullet;
//    }

    //
//    Image missile = new Image("/Images/missile.png");
//    missileView = new ImageView(missile);
//    missileView.setFitWidth(30);
//    missileView.setFitHeight(50);
}
