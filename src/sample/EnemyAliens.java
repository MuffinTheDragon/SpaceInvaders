package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class EnemyAliens extends Properties implements Measurements {


    /*

    Use formation.png

    */


    Pane p;

    ImageView[] alienGrid = new ImageView[enemyColumn * enemyRow];
    Fire f;

    Image largeInvader = new Image("/Images/large_invader_a.png");
    Image mediumInvaderA;
    Image mediumInvaderB;
    Image smallInvader;
    Timeline timeline;
    int score;
    int time = 28;
    int move = 0;
    boolean right = true;
    static Rectangle pointer = new Rectangle();
    Text gameOver = new Text("Game Over!");



    public EnemyAliens(Pane p) {
        this.p = p;
    }

//    public ImageView getLargeInvader() {
//        largeInvader = new Image("/Images/large_invader_a.png");
//        return new ImageView(largeInvader);
//    }

    public ImageView getMediumInvaderA() {
        mediumInvaderA = new Image("/Images/medium_invader_a.png");
        return new ImageView(mediumInvaderA);
    }

    public ImageView getMediumInvaderB() {
        mediumInvaderB = new Image("/Images/medium_invader_b.png");
        return new ImageView(mediumInvaderB);
    }

    public ImageView getSmallInvader() {
        smallInvader = new Image("/Images/small_invader_a.png");
        return new ImageView(smallInvader);
    }
//    public void createAlienGrid() {
//        int row = 0;
//        alienGrid = new ImageView[enemyColumn * enemyRow];
//        while (row < 4) {
//            for (int col = 0; col < alienGrid[0].length; col++) {
//                if (row == 0) {
//                    alienGrid[0][col] = getLargeInvader();
//                } else if (row == 1) {
//                    alienGrid[1][col] = getMediumInvaderA();
//                } else if (row == 2) {
//                    alienGrid[2][col] = getMediumInvaderB();
//                } else if (row == 3) {
//                    alienGrid[3][col] = getSmallInvader();
//                }
//            }
//            row++;
//
//        }
//
//    }
//


    public void alienMovement() {
        if (right) {
            if (pointer.getX() + enemyEdge >= screenWidth) {
                right = false;
                for (int i = 0; i < alienGrid.length; i++) {
                    if (alienGrid[i] != null) {
                        alienGrid[i].setY(alienGrid[i].getY() + 50);
                    }
                }
            }
            for (int j = 0; j < alienGrid.length; j++) {
                if (alienGrid[j] != null) {
                    alienGrid[j].setX(alienGrid[j].getX() + alienSpeed);
                }
            }
            pointer.setX(pointer.getX() + alienSpeed);
        } else {
            if (pointer.getX() - ((enemyEdge * (enemyColumn + 2))) <= 0) {
                right = true;
                for (int i = 0; i < alienGrid.length; i++) {
                    if (alienGrid[i] != null) {
                        alienGrid[i].setY(alienGrid[i].getY() + 50);
                    }
                }
            }
            for (int i = 0; i < alienGrid.length; i++) {
                if (alienGrid[i] != null) {
                    alienGrid[i].setX(alienGrid[i].getX() - alienSpeed);
                }
            }
            pointer.setX(pointer.getX() - alienSpeed);
            stopMovement();

//            for (int i = 0; i < alienGrid.length; i++) {
//                System.out.println("HELLO: " + alienGrid[i].getY());
//                if (alienGrid[i].getY() >= 750) {
//                    Add AlertBox functionality
//                }
        }
    }


     public void stopMovement() {
        for (int i = 0; i < alienGrid.length; i++) {
            System.out.println("HELLO: " + alienGrid[i].getY());
            if (alienGrid[i].getY() >= 950) {
//                    Add AlertBox functionality
                timeline.stop();
                try {
                    Media media = new Media(getClass().getResource("/Sounds/Game Over.mp3").toURI().toString());
                    MediaPlayer player = new MediaPlayer(media);
                    player.setVolume(1000);
                    player.play();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                gameOver.setFont(Font.font("Verdana", 20));
                gameOver.setFill(Color.YELLOW);
                p.getChildren().add(gameOver);
                break;

                //Text Not Being Added
            }
        }
    }

//        if (f != null) {
//            score += f.getScore();
//            scoreText.setText("Score: " + score);
//        }


    public void displayGrid() {

        for (int row = 0; row < enemyRow; row++) {
            for (int col = 0; col < enemyColumn; col++) {

                //aliendGrid[row * enemyColumn][row * enemyColumn + 1] = ....;

                alienGrid[row * enemyColumn + col] = new ImageView(largeInvader);
                alienGrid[row * enemyColumn + col].setPreserveRatio(true);
                alienGrid[row * enemyColumn + col].setX(col * 50);
                alienGrid[row * enemyColumn + col].setY(row * 50);
                alienGrid[row * enemyColumn + col].setFitWidth(enemyEdge);
                p.getChildren().add(alienGrid[row * enemyColumn + col]);
                if (col == enemyColumn - 1 && row == 0) {
                    pointer.setWidth(enemyEdge);
                    pointer.setHeight(enemyEdge);
                    pointer.setFill(Color.TRANSPARENT);
                    pointer.setX(alienGrid[col].getX() + enemyEdge);
                }
//
//                Tools.setCoordinates(alienGrid[row * alienGrid[0].length + col], (col * 50), (row * 50));
////                ImageView image = new ImageView(alienGrid[row][col]);
////                Tools.setCoordinates(alienGrid[row][col], 500, 250); //Figure out how to print them in array format
//                p.getChildren().add(alienGrid[row * alienGrid[0].length + col][row * alienGrid[0].length + col]);
////                if (col == alienGrid[0].length - 1 && row == 0) {

//                }
            }
        }
        time -= 3;

        Duration dur = new Duration(time);
        KeyFrame frame = new KeyFrame(dur, e -> alienMovement());
        timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
