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
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URISyntaxException;


/*

TO-DO:

1. Import custom arcade font
2. Import alien missiles
3. Make sure you can't just hold down SPACE

*/

public class Main extends Application {

    Pane pane = new Pane();
    EnemyAliens ea = new EnemyAliens(pane);
    ImageView shipView;
    Timer timer;

    private Parent Content() {

        ImageView background = new ImageView();
        background.setImage(new Image("/Images/background.png"));
        background.setFitWidth(600);
        background.setFitHeight(350);

        Image ship = new Image("/Images/tank.png");
        shipView = new ImageView(ship);
        shipView.setFitHeight(40);
        shipView.setFitWidth(80);
        Tools.setCoordinates(shipView, 0, 720);

//        ea.displayGrid();
        pane.getChildren().addAll(shipView);

        return pane;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));\

        ea.createAlienGrid();
        ea.displayGrid();
        Scene scene = new Scene(Content(), 1100, 750);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/Space-invaders-logo.png")));
        primaryStage.show();


        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            int velx = 0;
            double velMissileCount = 750;


            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.RIGHT && shipView.getLayoutX() <= 1020) {
                    velx += 10;
                    shipView.setLayoutX(velx);
                    System.out.println(velx);
                } else if (event.getCode() == KeyCode.LEFT && shipView.getLayoutX() >= 10) {
                    velx -= 10;
                    shipView.setLayoutX(velx);
                    System.out.println(velx);
                } else if (event.getCode() == KeyCode.SPACE) {
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

                    Fire f = new Fire(pane, velx);

                }
            }

        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
