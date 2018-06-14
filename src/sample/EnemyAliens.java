package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URISyntaxException;
import java.util.Random;

public class EnemyAliens extends Properties implements Measurements {


    /*

    Use formation.png

    */


    Pane p;


    ImageView[][] alienGrid = new ImageView[EnemyRow()][EnemyColumn()];
    ImageView[] alien = new ImageView[EnemyRow() * EnemyColumn()];
    Fire f;

    Image enemyBullet = new Image("/Images/bullet.png");
    ImageView alienBullet;
    Image largeInvader = new Image("/Images/large_invader_a.png");
    Image mediumInvader = new Image("/Images/medium_invader_a.png");
    Image mediumInvaderA;
    Image mediumInvaderB;
    Image smallInvader;
    Timeline timeline;
    Timeline alienTimeline;
    int time = 28;
    int bulletTime = 28;
    int move = 0;
    boolean right = true;
    static Rectangle pointer = new Rectangle();

    Rectangle rec = null;
    boolean bullet = true;
    boolean game = false;

    static int alienNullCount = 0;
    static boolean shipHit = false;
    Font arcade = Font.loadFont(Main.class.getResource("/Fonts/ARCADE_I.ttf").toExternalForm(), 75);


    public EnemyAliens(Pane p) {
        super();
        this.p = p;
//
//        if (alienBullet == null) {
//            alienBullet = new ImageView(enemyBullet);
//            p.getChildren().add(alienBullet);
//            Timeline alienTime;
//            Random rn = new Random();
//            int row = rn.nextInt(4);
//            int col = rn.nextInt(10);
//
//            if (alienGrid[row][col] != null) {
//                alienBullet.setX(alienGrid[row][col].getX());
//                alienBullet.setY(alienGrid[row][col].getY());
//
//
////                System.out.println("X: " + alienBullet.getX());
////                System.out.println("Y: " + alienBullet.getY());
////
////                System.out.println("SHIP X:" + Main.shipView.getLayoutX());
////                System.out.println("SHIP Y:" + Main.shipView.getLayoutY());
//
//
//                Duration duration = new Duration(5);
//                KeyFrame keyFrame = new KeyFrame(duration, e -> {
//                    if (alienBullet != null) {
//                        alienBullet.setY(alienBullet.getY() + 5);
////                        shipHit();
//                    }
//                });
//
//                alienTime = new Timeline(keyFrame);
//                alienTime.setCycleCount(Animation.INDEFINITE);
//                alienTime.play();
//
//
//            }
//        }
    }


    public EnemyAliens() {

    }

//    public ImageView getLargeInvader() {
//        largeInvader = new Image("/Images/large_invader_a.png");
//        return new ImageView(largeInvader);
//    }

//    public ImageView getMediumInvaderA() {
//        mediumInvaderA = new Image("/Images/medium_invader_a.png");
//        return new ImageView(mediumInvaderA);
//    }

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


    //
//    public int getScore() {
//        return score;
//    }
//

    public void alienMovement() {
        if (right) {
            if (pointer.getX() + EnemyEdge() >= 280) {
                right = false;
                for (int i = 0; i < alienGrid.length; i++) {
                    for (int j = 0; j < alienGrid[0].length; j++) {
                        if (alienGrid[i][j] != null) {
                            alienGrid[i][j].setY(alienGrid[i][j].getY() + 50);
                        }
                    }
                }
            }
            for (int i = 0; i < alienGrid.length; i++) {
                for (int j = 0; j < alienGrid[0].length; j++) {
                    if (alienGrid[i][j] != null) {
                        alienGrid[i][j].setX(alienGrid[i][j].getX() + AlienSpeed());
                    }
                }
            }
            pointer.setX(pointer.getX() + AlienSpeed());
        } else {
            if (pointer.getX() - ((EnemyEdge() * (EnemyColumn() + 2))) <= -490) {
                right = true;
                for (int i = 0; i < alienGrid.length; i++) {
                    for (int j = 0; j < alienGrid[0].length; j++) {
                        if (alienGrid[i][j] != null) {
                            alienGrid[i][j].setY(alienGrid[i][j].getY() + 50);
                        }
                    }
                }
            }
            for (int i = 0; i < alienGrid.length; i++) {
                for (int j = 0; j < alienGrid[0].length; j++) {
                    if (alienGrid[i][j] != null) {
                        alienGrid[i][j].setX(alienGrid[i][j].getX() - AlienSpeed());
                    }
                }
            }
            pointer.setX(pointer.getX() - AlienSpeed());
            stopMovement();

            for (int i = 0; i < alienGrid.length; i++) {
                for (int j = 0; j < alienGrid[0].length; j++) {
//                    System.out.println("HELLO: " + alienGrid[i][j].getY());
//                    if (alienGrid[i][j].getY() >= 750) {
////                    Add AlertBox functionality
//                    }
                }
            }
        }
    }


//    public void alienMovement() {
//        if (right) {
//            if (pointer.getX() + EnemyEdge() >= 280) {
//                right = false;
//                for (int i = 0; i < alien.length; i++) {
//                        if (alien[i] != null) {
//                            alien[i].setY(alien[i].getY() + 50);
//                        }
//                }
//            }
//            for (int i = 0; i < alien.length; i++) {
//                    if (alien[i] != null) {
//                        alien[i].setX(alien[i].getX() + AlienSpeed());
//                    }
//            }
//            pointer.setX(pointer.getX() + AlienSpeed());
//        } else {
//            if (pointer.getX() - ((EnemyEdge() * (EnemyColumn() + 2))) <= -490) {
//                right = true;
//                for (int i = 0; i < alien.length; i++) {
//                        if (alien[i] != null) {
//                            alien[i].setY(alien[i].getY() + 50);
//                        }
//                }
//            }
//            for (int i = 0; i < alien.length; i++) {
//                    if (alien[i] != null) {
//                        alien[i].setX(alien[i].getX() - AlienSpeed());
//                    }
//            }
//            pointer.setX(pointer.getX() - AlienSpeed());
//            stopMovement();
//
//            for (int i = 0; i < alien.length; i++) {
////                    System.out.println("HELLO: " + alienGrid[i][j].getY());
//                    if (alien[i].getY() >= 750) {
////                    Add AlertBox functionality
//                    }
//            }
//        }
//    }


    public boolean stopMovement() {
        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
//                System.out.println("HELLO X: " + alienGrid[i][j].getX());
//                System.out.println("HELLO Y: " + alienGrid[i][j].getY());
                if (alienGrid[i][j] != null && alienGrid[i][j].getY() >= 700 || shipHit) {
//                    Add AlertBox functionality
                    timeline.stop();
                    Text gameOver = new Text(0, 350, "Game Over!");
                    gameOver.setFill(Color.YELLOW);
                    gameOver.setFont(arcade);
                    p.getChildren().add(gameOver);
                    try {
                        Media media = new Media(getClass().getResource("/Sounds/Game Over.mp3").toURI().toString());
                        MediaPlayer player = new MediaPlayer(media);
                        player.setVolume(1000);
                        player.play();
                        game = true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                } else if (alienNullCount == 40) {
                    timeline.stop();
                    Text win = new Text(130, 350, "Winner!");
                    win.setFill(Color.YELLOW);
                    win.setFont(arcade);
                    p.getChildren().add(win);
                    try {
                        Media media = new Media(getClass().getResource("/Sounds/Victory.mp3").toURI().toString());
                        MediaPlayer player = new MediaPlayer(media);
                        player.setVolume(1000);
                        player.play();
                        game = true;
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return game;
    }

    public void alienFire() {
        alienBullet = new ImageView(enemyBullet);
        p.getChildren().add(alienBullet);
//        System.out.println("WHILE TEST");

        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {

                if (alienGrid[i][j] != null) {
                    alienBullet.setX(alienGrid[i][j].getX() + 5);
                    alienBullet.setY(alienGrid[i][j].getY());

//                } else {
//
//                    System.out.println("NO");
//                }
                }
            }
        }

            for (int i = 0; i < alienGrid.length; i++) {
                for (int j = 0; j < alienGrid[0].length; j++) {

                    if (alienGrid[i][j] != null) {
                        alienBullet.setY(alienBullet.getY() + 10);
                    } else {
                        System.out.println("send help");
                    }
                }
            }
//
//        Duration duration = new Duration(5);
//        KeyFrame keyFrame = new KeyFrame(duration, e -> {
//            if (alienGrid[row][j] != null) {
//                alienBullet.setY(alienBullet.getY() + 5);
//            }
//        });
//        alienTimeline = new Timeline(keyFrame);
//        alienTimeline.setCycleCount(Animation.INDEFINITE);
        }


    public void displayGrid() {

        for (int row = 0; row < EnemyRow(); row++) {
            for (int col = 0; col < EnemyColumn(); col++) {

                try {
                    alienGrid[row][col] = new ImageView(largeInvader);
                    alienGrid[row][col].setPreserveRatio(true);
                    alienGrid[row][col].setX(col * 50);
                    alienGrid[row][col].setY(row * 50);
                    alienGrid[row][col].setFitWidth(EnemyEdge());
                    alien[row * EnemyColumn() + col] = new ImageView(largeInvader);
                    alien[row * EnemyColumn() + col].setPreserveRatio(true);
                    alien[row * EnemyColumn() + col].setX(col * 50);
                    alien[row * EnemyColumn() + col].setY(row * 50);
                    alien[row * EnemyColumn() + col].setFitWidth(EnemyEdge());
                    p.getChildren().add(alienGrid[row][col]);
//                    p.getChildren().add(alien[row * EnemyColumn() + col]);
                } catch (ArrayIndexOutOfBoundsException e) {
//
                }


                //aliendGrid[row * enemyColumn][row * enemyColumn + 1] = ....;
//
//                alienGrid[row * EnemyColumn() + col] = new ImageView(largeInvader);
//                alienGrid[row * EnemyColumn() + col].setPreserveRatio(true);
//                alienGrid[row * EnemyColumn() + col].setX(col * 50);
//                alienGrid[row * EnemyColumn() + col].setY(row * 50);
//                alienGrid[row * EnemyColumn() + col].setFitWidth(enemyEdge);
//                p.getChildren().add(alienGrid[row * EnemyColumn() + col]);
//                if (col == EnemyColumn() - 1 && row == 0) {
//                    pointer.setWidth(EnemyEdge());
//                    pointer.setHeight(EnemyEdge());
//                    pointer.setFill(Color.TRANSPARENT);
//                    pointer.setX(alienGrid[col].getX() + EnemyEdge());
//                }

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

//
//        Duration duration = new Duration(5);
//        KeyFrame keyFrame = new KeyFrame(duration, e -> alienFire());
//        alienTimeline = new Timeline(keyFrame);
//        alienTimeline.setCycleCount(Animation.INDEFINITE);
//        alienTimeline.play();


    }

    //21:46:06

//
//    public ImageView[][] getAlienGrid() {
//        return alienGrid;
//    }


    public ImageView[][] getAlienGrid() {
        return alienGrid;
    }

}



/*

    Reference:

    1. Enemy Movement
        GitHub - https://github.com/Shindanaide/SpaceInvaders

*/
