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
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class EnemyAliens extends Properties implements Measurements {


    /*

    Use formation.png

    */


    Pane p;


    ImageView[][] alienGrid = new ImageView[EnemyRow()][EnemyColumn()];
    ImageView[] alien = new ImageView[EnemyRow() * EnemyColumn()];
    Fire f;

    Image largeInvader = new Image("/Images/large_invader_a.png");
    Image mediumInvader = new Image("/Images/medium_invader_a.png");
    Image mediumInvaderA;
    Image mediumInvaderB;
    Image smallInvader;
    Timeline timeline;
    Timeline tl;
    int score;
    int time = 28;
    int move = 0;
    boolean right = true;
    static Rectangle pointer = new Rectangle();
    Text gameOver = new Text("Game Over!");

    Rectangle rec = null;
    boolean bullet = true;



    public EnemyAliens(Pane p) {
        super();
        this.p = p;
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


        public void stopMovement() {
        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
//                System.out.println("HELLO X: " + alienGrid[i][j].getX());
//                System.out.println("HELLO Y: " + alienGrid[i][j].getY());
                if (alienGrid[i][j] != null && alienGrid[i][j].getY() >= 700) {
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
//                    gameOver.setFont(Font.font("Verdana", 20));
//                    gameOver.setFill(Color.YELLOW);
//                    p.getChildren().add(gameOver);


//    Text Not Being Added
                }
            }
        }
    }

//        if (f != null) {
//            score += f.getScore();
//            scoreText.setText("Score: " + score);
//        }


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
