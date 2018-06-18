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

import java.io.*;
import java.net.URISyntaxException;
import java.util.Random;

class Fire extends EnemyAliens { //Inherit EnemyAliens class

    private Pane p;
    private ImageView alienBullet = null;
    private ImageView missileView = null;
    private ImageView ufoRocket = null;

    private boolean bullet = true;

    static int points = 0;
    private int lastScore = 0;


    private Font arcade = Font.loadFont(Main.class.getResource("/Fonts/ARCADE_I.ttf").toExternalForm(), 25);

    static final Text score = new Text(0, 25, "Score: " + points);
    static final Text gameHighScore = new Text(300, 25, "HighScore: " + points);
    static final Text ufoHits = new Text(0, 55, "UFO Hits: " + ufoHitCount);

    //Class constructor that requires 4 parameters
    Fire(Pane p, int position, ImageView[][] alienGrid, ImageView alienUFO) {

        super(); //Inheriting EnemyAliens

        if (missileView == null) { //Checks to see that the ImageView is not loaded with any images
            this.p = p;
            Image missile = new Image("/Images/missile.png"); //Creating the missile image
            missileView = new ImageView(missile); //Loading ImageView with Image
            score.setFill(Color.YELLOW); //Score text color
            score.setFont(arcade); //Score text font
            gameHighScore.setFill(Color.YELLOW); //High score Text color
            gameHighScore.setFont(arcade); //High score Text font
            p.getChildren().add(missileView); //Adding missile onto stage

            missileView.setX(position + 30); //position of ship + 35 units. Location where missile fires from
            missileView.setY(640); //Located right above user's ship

            //Following code is responsible for moving the missile's y-coordinates

            Duration duration = new Duration(5);
            KeyFrame keyFrame = new KeyFrame(duration, e -> {

                //If the ImageView is loaded with the missile image, set its new y-coordinates to be 5 less.
                // Do this every 5 milliseconds
                if (missileView != null) {
                    missileView.setY(missileView.getY() - 5);
                    alienHit(alienGrid);
                    scores();
                }
            });

            Timeline timeline = new Timeline(keyFrame);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play(); //Play the animation

        }

//        Enemy fire code
//
        if (alienBullet == null) {
            Image enemyBullet = new Image("/Images/bullet.png");
            alienBullet = new ImageView(enemyBullet);
            p.getChildren().add(alienBullet);
            Timeline alienTimeline;
            Random rn = new Random();
            int row = rn.nextInt(4);
            int col = rn.nextInt(10);


            if (alienGrid[row][col] != null) {
                alienBullet.setX(alienGrid[row][col].getX());
                alienBullet.setY(alienGrid[row][col].getY());

                Duration duration = new Duration(5);
                KeyFrame keyFrame = new KeyFrame(duration, e -> {
                    if (alienBullet != null) {
                        alienBullet.setY(alienBullet.getY() + 1);
                        shipHit();
                    }
                });

                alienTimeline = new Timeline(keyFrame);
                alienTimeline.setCycleCount(Animation.INDEFINITE);
                alienTimeline.play();

            }
        }

        //UFO Fire Code

        //The following code is for the final stage of the game when the user kills all 40 enemy aliens
        if (alienNullCount == 40) {
            if (ufoRocket == null) {
                Image ufoBullet = new Image("Images/rocket.png");
                ufoRocket = new ImageView(ufoBullet);
                ufoRocket.setFitWidth(64);
                ufoRocket.setFitHeight(64);
                p.getChildren().add(ufoRocket);
                Timeline ufoTimeline;
                Random rn = new Random();
                int rocketPosition = rn.nextInt(680);

                ufoRocket.setX(rocketPosition);
                ufoRocket.setY(0);

                Duration duration = new Duration(5);
                KeyFrame keyFrame = new KeyFrame(duration, e -> {
                    if (ufoRocket != null) {
                        ufoRocket.setY(ufoRocket.getY() + 1);
                        ufoHit(alienUFO);
                        rocketHit();
                    }
                });

                ufoTimeline = new Timeline(keyFrame);
                ufoTimeline.setCycleCount(Animation.INDEFINITE);
                ufoTimeline.play();
            }
        }
    }

    Fire() {

    }

    private void bulletHit() {
        p.getChildren().remove(missileView);
        missileView = null;
    }

    private void shipHit() {
        //The + 1 is to give the alien bullet a width of 1 unit and the + 5 is to give the alien bullet a height of 5
        // units
        if (alienBullet != null) {
            if (alienBullet.getX() < Main.velx + Main.shipView.getFitWidth() && alienBullet.getX() + 1 > Main.velx &&
                    alienBullet.getY() < 670 + Main.shipView.getFitHeight() && 5 + alienBullet.getY() > 670) {

                //This variable determines whether or not the user's ship has been hit by enemy aliens
                //It is then used in the stopAlienMovement() method in the EnemyAliens class to stop the game
                shipHit = true;

            }
        }
    }

    private void rocketHit() {
        if (ufoRocket != null) {
            if (ufoRocket.getX() < Main.velx + Main.shipView.getFitWidth() && ufoRocket.getX() + 55
                    > Main.velx && ufoRocket.getY() < 670 + Main.shipView.getFitHeight() && 40 +
                    ufoRocket.getY() > 670) {

                //This variable determines whether or not the user's ship has been hit by the enemy UFO
                //It is then used in the stopUFOMovement() method in the EnemyAliens class to stop the game
                ufoHit = true;
            }
        }
    }

    private void alienHit(ImageView[][] alienGrid) {
        for (int i = 0; i < alienGrid.length; i++) {
            for (int j = 0; j < alienGrid[0].length; j++) {
                if (missileView != null && alienGrid[i][j] != null) {
                    if ((missileView != null && missileView.getX() < alienGrid[i][j].getX() + alienGrid[i][j].getFitWidth()
                            && missileView.getX() + 10 > alienGrid[i][j].getX()
                            && missileView.getY() < alienGrid[i][j].getY() + alienGrid[i][j].getFitHeight()
                            && 50 + missileView.getY() > alienGrid[i][j].getY())) {
//                        System.out.println("Test");
                        Image explosion = new Image("/Images/explosion.gif");
                        ImageView shipExplosion = new ImageView(explosion);
                        shipExplosion.setX(alienGrid[i][j].getX());
                        shipExplosion.setY(alienGrid[i][j].getY());
                        p.getChildren().add(shipExplosion);
                        alienGrid[i][j].setVisible(false);
                        alienGrid[i][j] = null;
                        alienNullCount += 1;
                        points += 100;

                        try {
                            Media media = new Media(getClass().getResource("/Sounds/explosion.wav").toURI().toString());
                            MediaPlayer player = new MediaPlayer(media);
                            player.setVolume(50);
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

    }

    //This method keeps tracks of how many times user hits enemy UFO
    private void ufoHit(ImageView alienUFO) {
        if (missileView != null && alienUFO != null) {
            if (missileView.getX() < alienUFO.getX() + alienUFO.getFitWidth() && missileView.getX() + 10 > alienUFO.getX()
                    && missileView.getY() < alienUFO.getY() + alienUFO.getFitHeight() && 50 + missileView.getY() >
                    alienUFO.getY()) {
                ufoHitCount += 1; //Each time user successfully hits the UFO, the counter increases by 1
                points += 50;
                ufoHits.setFill(Color.YELLOW);
                ufoHits.setFont(arcade);
                ufoHits.setText("UFO Hits: " + ufoHitCount);

                try {
                    Media media = new Media(getClass().getResource("/Sounds/ufoHit.wav").toURI().toString());
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                bulletHit();
                bullet = false;
                scores();
            }
        }

        if (missileView != null) {
            if (missileView.getY() < 0 - 50 - 1) {
                bulletHit();
            }
        }
    }

    void scores() {
        score.setText("Score: " + points);

        String k = System.getProperty("user.home");
        String n = k + File.separator + "GameSaveData.txt";
//                        System.out.println(n);

        try {
            FileReader fr = new FileReader(n);
            BufferedReader br = new BufferedReader(fr);
            String sRecord = br.readLine();
            lastScore = Integer.parseInt(sRecord);
            gameHighScore.setText("High Score: " + lastScore);
//            System.out.println("" + lastScore);
            br.close();
            fr.close();
        } catch (Exception ignored) {

        }
        if (points >= lastScore) {
            int highScore = points;
            gameHighScore.setText("High Score: " + highScore);


            try {
                FileWriter fw = new FileWriter(n);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write("" + highScore);
                bw.close();
                fw.close();
            } catch (Exception e) {
                points = 0;
            }
        }
    }
}
