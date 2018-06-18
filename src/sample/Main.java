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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URISyntaxException;


/*

TO-DO:

1. Import custom arcade font
2. Import alien missiles
3. Make sure you can't just hold down SPACE
4. Add Game Soundtrack!

*/

public class Main extends Application implements GameProperties {

    private Pane pane = new Pane();
    static ImageView shipView = null;
    static int velx = 0;
    static int hitCounter = 0;
    private EnemyAliens ea = new EnemyAliens(pane);
    private Fire fire = new Fire();



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


        pane.getChildren().addAll(shipView, Fire.score, Fire.gameHighScore, Fire.ufoHits);

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

        ea.displayGrid();
        fire.scores();
        Scene scene = new Scene(Content(), screenWidth, screenHeight);
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders!");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Images/Space Invaders Game.png")));
        primaryStage.show();

        //Input - Processing - Output: Following code manages user inputs
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            long timerBefore = 0; //variable to track the time (milliseconds) before the user presses the space key
            long timerAfter; //variable to track the time (milliseconds) after the user presses the space key

            @Override
            public void handle(KeyEvent event) { //Handle KeyEvents

                //If user presses the right arrow key and the ship is within the window and the game hasn't ended:
                if (event.getCode() == KeyCode.RIGHT && shipView.getLayoutX() <= 620 && !ea.stopAlienMovement()) {

                    velx += 10; //increase the x-coordinate by 10
                    shipView.setLayoutX(velx); //set the ship's x-coordinate

                //If user presses the right arrow key and the ship is within the window and the game hasn't ended:
                } else if (event.getCode() == KeyCode.LEFT && shipView.getLayoutX() >= 10 && !ea.stopAlienMovement()) {

                    velx -= 10; //decrease the x-coordinate by 10
                    shipView.setLayoutX(velx); //set the ship's x-coordinate

                //If user presses the space key and the game hasn't ended:
                } else if (event.getCode() == KeyCode.SPACE && !ea.stopAlienMovement()) {

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
