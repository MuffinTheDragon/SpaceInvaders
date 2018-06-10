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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URISyntaxException;

public class Fire extends EnemyAliens{

    public static Timeline tlB;
    Image missile = new Image("/Images/missile.png");
    ImageView missileView = null;
    boolean bullet = true;
    Pane p;
    static int points = 0;
    Font arcade = Font.loadFont(Main.class.getResource("/Fonts/ARCADE_I.ttf").toExternalForm(), 25);

    static final Text score = new Text(0, 25, "Score: " + points);


//    ImageView[][] alienGrid = new ImageView[EnemyRow()][EnemyColumn()];
//    Image largeInvader = new Image("/Images/large_invader_a.png");


//    int score;

    public Fire(Pane p, int position, ImageView[][] alienGrid) {
        super();
        if (missileView == null) {
            this.p = p;
            missileView = new ImageView(missile);
            score.setFill(Color.YELLOW);
            score.setFont(arcade);
            p.getChildren().add(missileView);
            missileView.setX(position + 30); //position of ship + 35 units
            missileView.setY(640);
            Duration dB = new Duration(5);
            KeyFrame fB = new KeyFrame(dB, e -> {
                if (missileView != null) {
                    missileView.setY(missileView.getY() - 5);
//            System.out.println("Missile Y:" + missileView.getY());
                    hit(alienGrid);
                }
            });

            tlB = new Timeline(fB);
            tlB.setCycleCount(Animation.INDEFINITE);
            tlB.play();

        }
    }
//
//    public ImageView[][] alienGrid() {
//
//        for (int row = 0; row < EnemyRow(); row++) {
//            for (int col = 0; col < EnemyColumn(); col++) {
//
//                try {
//                    alienGrid[row][col] = new ImageView(largeInvader);
//                    alienGrid[row][col].setPreserveRatio(true);
//                    alienGrid[row][col].setX(col * 50);
//                    alienGrid[row][col].setY(row * 50);
//                    alienGrid[row][col].setFitWidth(EnemyEdge());
//                } catch (ArrayIndexOutOfBoundsException e) {
//
//                }
//            }
//        }
//        return alienGrid;
//    }


    //
//    Image missile = new Image("/Images/missile.png");
//    missileView = new ImageView(missile);
//    missileView.setFitWidth(30);
//    missileView.setFitHeight(50);

    public void bulletHit() {
        p.getChildren().remove(missileView);
        missileView = null;
    }

    public boolean hit(ImageView alienGrid[][]) {
        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
                if (missileView != null && alienGrid[i][j] != null) {
                    if ((missileView != null && missileView.getX() < alienGrid[i][j].getX() + alienGrid[i][j].getFitWidth()
                            && missileView.getX() + 10 > alienGrid[i][j].getX()
                            && missileView.getY() < alienGrid[i][j].getY() + alienGrid[i][j].getFitHeight()
                            && 50 + missileView.getY() > alienGrid[i][j].getY())) {
                        System.out.println("Test");
                        alienGrid[i][j].setVisible(false);
                        alienGrid[i][j] = null;
                        alienNullCount += 1;
                        points += 100;
                        score.setText("Score: " + points);
                        try {
                            Media media = new Media(getClass().getResource("/Sounds/explosion.wav").toURI().toString());
                            MediaPlayer player = new MediaPlayer(media);
                            player.play();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        bulletHit();
                        bullet = false;
                    }
                }
            }
        }


        if (missileView != null) {
            if (missileView.getY() < 0 - 50 - 1) {
                bulletHit();
            }
        }

        return bullet;
    }

}
