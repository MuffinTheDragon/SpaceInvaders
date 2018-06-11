package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.lang.*;

import javax.swing.*;
import javafx.scene.text.Text;
import java.net.URISyntaxException;


/*

TO-DO:

1. Import custom arcade font
2. Import alien missiles
3. Make sure you can't just hold down SPACE

*/

public class Main extends Application implements Measurements {

    Pane pane = new Pane();
    ImageView shipView;
    Timer timer;
    EnemyAliens ea = new EnemyAliens(pane);


    private Parent Content() {

        ImageView background = new ImageView();
        background.setImage(new Image("/Images/background.png"));
        background.setFitWidth(600);
        background.setFitHeight(350);

        Image ship = new Image("/Images/Spaceship.png");
        shipView = new ImageView(ship);
        shipView.setFitHeight(40);
        shipView.setFitWidth(80);
        Tools.setCoordinates(shipView, 0, 670);


//        ea.displayGrid();
        pane.getChildren().addAll(shipView, Fire.score, Fire.gameHighScore);

        return pane;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));\

//        ea.createAlienGrid();

        ea.displayGrid();
//        ea.alienFire();
        Scene scene = new Scene(Content(), screenWidth, screenHeight);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/Space-invaders-logo.png")));
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            double velMissileCount = 750;
            int velx = 0;
            long timerBefore = 0;
            long timerAfter;

            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.RIGHT && shipView.getLayoutX() <= 620 && !ea.stopMovement()) {
                    velx += 10;
                    shipView.setLayoutX(velx);
//                    System.out.println(velx);
                } else if (event.getCode() == KeyCode.LEFT && shipView.getLayoutX() >= 10 && !ea.stopMovement()) {
                    velx -= 10;
                    shipView.setLayoutX(velx);
//                    System.out.println(velx);
                } else if (event.getCode() == KeyCode.SPACE && !ea.stopMovement()) {
                    timerAfter = System.currentTimeMillis();

                    if (timerAfter - timerBefore <= 100) {
                        event.consume();
                    } else {
//                    while (velMissileCount >= 0) {
////                        velMissileCount -= 10;
////                        missileView.setLayoutY(velMissileCount);
////                        System.out.println(velMissileCount);
//                    }
//                    velMissileCount = 1100;
                        try {
                            Media media = new Media(getClass().getResource("/Sounds/shoot.wav").toURI().toString());
                            MediaPlayer player = new MediaPlayer(media);
                            player.play();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                        Fire f = new Fire(pane, velx, ea.getAlienGrid());

                    }
                    timerBefore = System.currentTimeMillis();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
