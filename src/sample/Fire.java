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

public class Fire extends EnemyAliens {

    public static Timeline tlB;
    Image missile = new Image("/Images/missile.png");
    Image enemyBullet = new Image("/Images/bullet.png");
    ImageView alienBullet = null;
    ImageView missileView = null;
    boolean bullet = true;
    Pane p;
    static int points = 0;
    int lastScore = 0;
    int highScore = 0;

    //Saving
    String saveDataPath;
    String fileName = "SaveData";
    String n;
    String sRecord;

    Font arcade = Font.loadFont(Main.class.getResource("/Fonts/ARCADE_I.ttf").toExternalForm(), 25);

    static final Text score = new Text(0, 25, "Score: " + points);
    static final Text gameHighScore = new Text(300, 25, "HighScore: " + points);


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
            gameHighScore.setFill(Color.YELLOW);
            gameHighScore.setFont(arcade);
            p.getChildren().add(missileView);
            missileView.setX(position + 30); //position of ship + 35 units
            missileView.setY(640);
            Duration dB = new Duration(5);
            KeyFrame fB = new KeyFrame(dB, e -> {
                if (missileView != null) {
                    missileView.setY(missileView.getY() - 5);
//            System.out.println("Missile Y:" + missileView.getY());
                    alienHit(alienGrid);
                    scores();
                }
            });

            tlB = new Timeline(fB);
            tlB.setCycleCount(Animation.INDEFINITE);
            tlB.play();

        }
        if (alienBullet == null) {
            alienBullet = new ImageView(enemyBullet);
            p.getChildren().add(alienBullet);
            Timeline timeline;
            Random rn = new Random();
            int row = rn.nextInt(4);
            int col = rn.nextInt(10);

            if (alienGrid[row][col] != null) {
                alienBullet.setX(alienGrid[row][col].getX());
                alienBullet.setY(alienGrid[row][col].getY());


                System.out.println("X: " + alienBullet.getX());
                System.out.println("Y: " + alienBullet.getY());

                System.out.println("SHIP X:" + Main.shipView.getLayoutX());
                System.out.println("SHIP Y:" + Main.shipView.getLayoutY());


                Duration duration = new Duration(5);
                KeyFrame keyFrame = new KeyFrame(duration, e -> {
                    if (alienBullet != null) {
                        alienBullet.setY(alienBullet.getY() + 5);
//                        shipHit();
                        scores();
                    }
                });

                timeline = new Timeline(keyFrame);
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

            }
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

//    public boolean shipHit() {
//        if (alienBullet.getX() == Main.shipView.getLayoutX()) {
//            System.out.println("YSESSSSSSS");
//            try {
//                Media m = new Media(getClass().getResource("/Sounds/explosion.wav").toURI().toString());
//                MediaPlayer p = new MediaPlayer(m);
//                p.play();
//                shipHit = true;
//            } catch (URISyntaxException e) {
//                System.out.println("Error: " + e);
//            }
//        }
//        return shipHit;
//    }

    public boolean alienHit(ImageView alienGrid[][]) {
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


//    private void createSaveData() {
//        try {
//            File file = new File(saveDataPath, fileName);
//
//            FileWriter output = new FileWriter(file);
//            BufferedWriter writer = new BufferedWriter(output);
//            writer.write("" + 0);
//            writer.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadHighScore() {
//        try {
//            File f = new File(saveDataPath, fileName);
//            if (!f.isFile()) {
//                createSaveData();
//            }
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
//            highScore = Integer.parseInt(reader.readLine());
//            reader.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setHighScore() {
//        FileWriter output = null;
//
//        try {
//            File f = new File(saveDataPath, fileName);
//            output = new FileWriter(f);
//            BufferedWriter writer = new BufferedWriter(output);
//
//            writer.write("" + highScore);
//
//            writer.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public void scores() {
        score.setText("Score: " + points);
//                        setHighScore();
//                        if (points >= highScore) {
//                            highScore = points;
//                            gameHighScore.setText("High Score: " + highScore);
//                        }

        String k = System.getProperty("user.home");
        n = k + File.separator + "GameSaveData.txt";
//                        System.out.println(n);


        try {
            FileReader fr = new FileReader(n);
            BufferedReader br = new BufferedReader(fr);
            sRecord = br.readLine();
            lastScore = Integer.parseInt(sRecord);
            gameHighScore.setText("High Score: " + lastScore);
//            System.out.println("" + lastScore);
            br.close();
            fr.close();
        } catch (Exception e) {

        }
        if (points >= lastScore) {
            highScore = points;
            gameHighScore.setText("High Score: " + highScore);
//                        if (points > highScore) {


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
