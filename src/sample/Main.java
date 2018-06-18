package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.net.URISyntaxException;


/*

Dhaval Malhotra

*/

public class Main extends Application implements GameProperties {

    private Pane pane = new Pane();
    static ImageView shipView = null;
    static int velx = 0;
    static int hitCounter = 0;
    private EnemyAliens ea = new EnemyAliens(pane); //Creating object for EnemyAliens class
    private Fire fire = new Fire(); //Creating object for Fire class



    private Parent Content() {

        Image ship = new Image("/Images/Spaceship.png");
        shipView = new ImageView(ship);
        shipView.setFitHeight(40);
        shipView.setFitWidth(80);
        Tools.setCoordinates(shipView, 0, 670);

        Image teaser = new Image("/Images/Teaser2.jpg");
        ImageView alienTeaser = new ImageView(teaser);
        alienTeaser.setFitHeight(250);
        alienTeaser.setFitWidth(550);
        Tools.setCoordinates(alienTeaser, 100, 230);
        Timeline timeline = new Timeline(
                 new KeyFrame(Duration.ZERO, new KeyValue(alienTeaser.imageProperty(), teaser)),
                 new KeyFrame(Duration.millis(3), new KeyValue(alienTeaser.opacityProperty(), 1.0)),
                 new KeyFrame(Duration.seconds(4), new KeyValue(alienTeaser.opacityProperty(), 0))
                );
        timeline.play();

        pane.getChildren().addAll(shipView, Fire.score, Fire.gameHighScore, Fire.ufoHits, alienTeaser);

        return pane;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        //Console output explaining the game rules
        System.out.println("Rules: \n" + "Kill 40 Enemy Aliens without getting hit before they reach the bottom of " +
                "the screen and pass your fortifications. \n" + "Afterwards, kill the UFO by hitting it 100 times " +
                "without getting hit by its rockets before it reaches the bottom of the screen and passes your " +
                "fortifications. \nYou will receive bonus points if you win depending on how many shots you fired " +
                "(time your shots, don't spam space). \n\n" + "Win Condition: Kill the UFO");

        ea.displayGrid(); //Calling the method in the EnemyAliens class, which displays the 2D enemy alien array

        //Calling the method in the Fire class, which prints out the score and high score text on the game screen
        // and updates them
        fire.scores();

        Scene scene = new Scene(Content(), screenWidth, screenHeight);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/space-invaders-logo.png")));
        primaryStage.show();

        //Input - Processing - Output: Following code manages user inputs
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            long timerBefore = 0; //variable to track the time (milliseconds) before the user presses the space key
            long timerAfter; //variable to track the time (milliseconds) after the user presses the space key

            @Override
            public void handle(KeyEvent event) { //Handle KeyEvents

                //If user presses the right arrow key and the ship is within the window and the game hasn't ended:
                if (event.getCode() == KeyCode.RIGHT && shipView.getLayoutX() <= 620 && !ea.stopAlienMovement
                        && !ea.stopUFOMovement) {

                    velx += 10; //increase the x-coordinate by 10
                    shipView.setLayoutX(velx); //set the ship's x-coordinate

                    //If user presses the left arrow key and the ship is within the window and the game hasn't ended:
                } else if (event.getCode() == KeyCode.LEFT && shipView.getLayoutX() >= 10 && !ea.stopAlienMovement
                        && !ea.stopUFOMovement) {

                    velx -= 10; //decrease the x-coordinate by 10
                    shipView.setLayoutX(velx); //set the ship's x-coordinate

                    //If user presses the space key and the game hasn't ended:
                } else if (event.getCode() == KeyCode.SPACE && !ea.stopAlienMovement && !ea.stopUFOMovement) {

                    hitCounter += 1; //Track of how many times user shoots. This is used later to assign bonus points
                    timerAfter = System.currentTimeMillis(); //Track the time (milliseconds) after user presses space

                    if (timerAfter - timerBefore <= 100) {
                        //If user presses space repeatedly, ignore the inputs (spamming is unethical)
                        event.consume();

                    } else {
                        try {
                            Media media = new Media(getClass().getResource("/Sounds/shoot.wav").toURI()
                                    .toString());
                            MediaPlayer player = new MediaPlayer(media);
                            player.setVolume(50);
                            player.play();
                            //Whenever user shoots, play the sound effect

                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }

                        Fire f = new Fire(pane, velx, ea.getAlienGrid(), ea.getUfoM());
                        //Create instance of the Fire class and display the missile onto the screen

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






/*

    References:

    1. Fading out Image:
        StackOverflow - https://stackoverflow.com/questions/23325488/add-timer-for-images-in-javafx?noredirect=1&lq=1
        StackOverflow - https://stackoverflow.com/questions/31798493/java-fx-using-fade-and-timeline-transition

   2. Parent Content():
        GitHub - https://github.com/Shindanaide/SpaceInvaders

*/