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

//Extending Properties class and implementing interface for screenWidth and screenHeight
class EnemyAliens extends Properties implements GameProperties {

    private Pane p;

    //Creating 2D array to that will hold each enemy alien
    private ImageView[][] alienGrid = new ImageView[EnemyRow()][EnemyColumn()];
    private Image ufo = new Image("/Images/ufo.png");
    private ImageView ufoM = null;

    private Image largeInvader = new Image("/Images/large_invader_a.png");
    private Timeline timeline;
    private Timeline ufoTimeline;

    private int time = 28;
    static int alienNullCount = 0;
    static int ufoHitCount = 0;
    static boolean shipHit = false;
    static boolean ufoHit = false;

    private boolean right = true;
    private boolean ufoMove = true;
    private boolean stopAlienMovement = false;
    private boolean stopUFOMovement = false;

    private static Rectangle pointer = new Rectangle();
    private static Rectangle ufoPointer = new Rectangle();

    private Font arcade = Font.loadFont(Main.class.getResource("/Fonts/ARCADE_I.ttf").toExternalForm(), 75);


    EnemyAliens(Pane p) {
        super();
        this.p = p;
    }


    EnemyAliens() {

    }

    private void alienMovement() {
        if (right) {
            if (pointer.getX() + EnemyEdge() >= 280) {
                right = false;
                for (int i = 0; i < EnemyRow(); i++) {
                    for (int j = 0; j < EnemyColumn(); j++) {
                        if (alienGrid[i][j] != null) {
                            alienGrid[i][j].setY(alienGrid[i][j].getY() + 50);
                        }
                    }
                }
            }
            for (int i = 0; i < EnemyRow(); i++) {
                for (int j = 0; j < EnemyColumn(); j++) {
                    if (alienGrid[i][j] != null) {
                        alienGrid[i][j].setX(alienGrid[i][j].getX() + AlienSpeed());
                        //The x-coordinates for each alien increases depending on its speed (e.g. +10 and -10)
                    }
                }
            }
            pointer.setX(pointer.getX() + AlienSpeed());
        } else {
            if (pointer.getX() - ((EnemyEdge() * (EnemyColumn() + 2))) <= -490) {
                right = true;
                for (int i = 0; i < EnemyRow(); i++) {
                    for (int j = 0; j < EnemyColumn(); j++) {
                        if (alienGrid[i][j] != null) {
                            alienGrid[i][j].setY(alienGrid[i][j].getY() + 50);
                        }
                    }
                }
            }
            for (int i = 0; i < EnemyRow(); i++) {
                for (int j = 0; j < EnemyColumn(); j++) {
                    if (alienGrid[i][j] != null) {
                        alienGrid[i][j].setX(alienGrid[i][j].getX() - AlienSpeed());
                    }
                }
            }
            pointer.setX(pointer.getX() - AlienSpeed());
            stopAlienMovement();
        }
    }


    private void ufoMovement() {
        if (ufoMove) {
            if (ufoPointer.getX() + EnemyEdge() >= 600) {
                ufoMove = false;
                if (ufoM != null) {
                    ufoM.setY(ufoM.getY() + 30);
                }
            }

            if (ufoM != null) {
                ufoM.setX(ufoM.getX() + ufoSpeed());
                //The x-coordinate for the enemy UFO increases based on its speed
            }
            ufoPointer.setX(ufoPointer.getX() + ufoSpeed());

        } else {
            if (ufoPointer.getX() - ((EnemyEdge() * (EnemyColumn() + 2))) <= -490) {
                ufoMove = true;

                if (ufoM != null) {
                    ufoM.setY(ufoM.getY() + 30);
                }
            }
            if (ufoM != null) {
                ufoM.setX(ufoM.getX() - ufoSpeed());
            }

            ufoPointer.setX(ufoPointer.getX() - ufoSpeed());
            stopUFOMovement();
        }
    }

    private void displayUFO() {
        int ufoTime = 50;
//        ufoArray[0].setFitWidth(250);
//        ufoArray[0].setFitHeight(150);
        if (ufoM == null) {
            ufoM = new ImageView(ufo);
            Tools.setCoordinates(ufoM, 0, 60);
            ufoM.setFitHeight(120);
            ufoM.setFitWidth(170);
            p.getChildren().add(ufoM);
            ufoTime -= 3;

            Duration dur = new Duration(ufoTime);
            KeyFrame frame = new KeyFrame(dur, e -> ufoMovement());
            ufoTimeline = new Timeline(frame);
            ufoTimeline.setCycleCount(Animation.INDEFINITE);
            ufoTimeline.play();
        }
    }


    //Repetition:
    //The following code checks to see if the user has lost Level 1 or won Level 1
    // by iterating through the 2D array using for-loops
    boolean stopAlienMovement() {
        for (int i = 0; i < EnemyRow(); i++) { //For each row in the array
            for (int j = 0; j < EnemyColumn(); j++) { //For each column in the array

                //If any elements in the 2D array are not set to null
                // (have not died. This means that some of the aliens are still alive)
                // and the aliens have passed the fortifications (bottom of the screen)
                // OR the user's ship has been hit:

                if ((alienGrid[i][j] != null && alienGrid[i][j].getY() >= 700) || shipHit) {
                    timeline.stop(); //This stops the alien animation from moving
                    Text gameOver = new Text(0, 350, "Game Over!"); //Creates game over text

                    //Setting properties for game over text and adding it onto the scene
                    gameOver.setFill(Color.YELLOW);
                    gameOver.setFont(arcade);
                    p.getChildren().add(gameOver);

                    //Playing the Game Over soundtrack
                    try {
                        Media media = new Media(getClass().getResource("/Sounds/Game Over.mp3").toURI().toString());
                        MediaPlayer player = new MediaPlayer(media);
                        player.play();
                        stopAlienMovement = true; //This variable indicates that the game is over
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //If the above if statement is false, this means the user wins the first Level and  moves onto the final stage
        if (alienNullCount == 40) { //User killed all 40 enemy aliens
            displayUFO(); //Calling the displayUFO() method which adds the enemy UFO onto the scene
        }
        return stopAlienMovement;
    }

    boolean stopUFOMovement() {

        if (ufoM != null && ufoHitCount != 10 && ufoM.getY() > 550 || ufoHit) {

            //Stop the game if the UFO ImageView hasn't been hit 100 times and has reached the bottom of the screen,
            // or if the user's ship has been hit
            ufoTimeline.stop();

            //Create text to inform user that they have lost
            Text gameOver = new Text(0, 350, "Game Over!");
            gameOver.setFill(Color.YELLOW);
            gameOver.setFont(arcade);

            //Add the text to scene
            p.getChildren().add(gameOver);

            //Enclosed in try-catch to catch URISyntaxException.
            // This exception occurs when the string can't be parsed into a URI.
            // For adding any Media, this exception has to be used
            try {
                Media media = new Media(getClass().getResource("/Sounds/Game Over.mp3").toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                player.play();
                stopUFOMovement = true;
            } catch (URISyntaxException e) {
                System.out.println("Error: " + e);
            }

        } else if (ufoHitCount == 10) {

            //Stop the game if the UFO ImageView has been hit 100 times
            ufoTimeline.stop();
            ufoM.setVisible(false);

            //Create text to inform user they have won
            Text win = new Text(130, 350, "Winner!");
            win.setFill(Color.YELLOW);
            win.setFont(arcade);

            //Add the text to the scene
            p.getChildren().add(win);

            try {
                Media media = new Media(getClass().getResource("/Sounds/Victory.mp3").toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                player.play();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            //Control Structure:
            //Once the user wins the game, begin assigning bonus points
            // Bonus points are only assigned if user wins defeats the boss

            if (Main.hitCounter <= 140) { //If the user fires 140 shots or less (1 shot = 1 kill): 5000 bonus points

                //Text to inform user of bonus points earned and number of shots fired
                Text bonus = new Text(130, 370, "Bonus Points: " + 5000);
                Text missilesFired = new Text(130, 390, "Missile's Fired: " + Main.hitCounter);

                //Add 5000 to user's base points
                Fire.points += 5000;

                //Create instance of Fire class and call the scores method to check for high scores
                Fire fire = new Fire();
                fire.scores();

                //Set the score text to user's new total score along with setting the text's properties
                Fire.score.setText("Score: " + Fire.points);
                bonus.setStyle("-fx-font-size: 12");
                bonus.setFill(Color.YELLOW);
                bonus.setFont(arcade);
                //Setting properties for Missile's Fired text and adding everything onto the scene
                missilesFired.setStyle("-fx-font-size: 12");
                missilesFired.setFill(Color.YELLOW);
                missilesFired.setFont(arcade);
                p.getChildren().addAll(bonus, missilesFired);


            }else if (Main.hitCounter <= 200) {//If the user fires 200 shots or less: 3000 bonus points

                //Text to inform user of bonus points earned and number of shots fired
                Text bonus = new Text(130, 370, "Bonus Points: " + 3000);
                Text missilesFired = new Text(130, 390, "Missile's Fired: " + Main.hitCounter);

                //Add 3000 to the user's base points
                Fire.points += 3000;

                //Create instance of Fire class and call the scores method again to check for any new high scores
                Fire fire = new Fire();
                fire.scores();

                //Set the score text to user's new total score
                Fire.score.setText("Score: " + Fire.points);
                bonus.setStyle("-fx-font-size: 12");
                bonus.setFill(Color.YELLOW);
                bonus.setFont(arcade);
                //Setting properties for Missile's Fired text and adding everything onto the scene
                missilesFired.setStyle("-fx-font-size: 12");
                missilesFired.setFill(Color.YELLOW);
                missilesFired.setFont(arcade);
                p.getChildren().addAll(bonus, missilesFired);

            } else if (Main.hitCounter <= 250) {//If user fires 250 shots or less: 1000 bonus points

                //Text to inform user of bonus points earned and number of shots fired
                Text bonus = new Text(130, 370, "Bonus Points: " + 1000);
                Text missilesFired = new Text(130, 390, "Missile's Fired: " + Main.hitCounter);

                //Add 1000 to the user's base points
                Fire.points += 1000;

                //Create instance of Fire class and call the scores method to check for any new high scores
                Fire fire = new Fire();
                fire.scores();

                //Set the score text to the user's new total score
                Fire.score.setText("Score: " + Fire.points);
                bonus.setStyle("-fx-font-size: 12");
                bonus.setFill(Color.YELLOW);
                bonus.setFont(arcade);
                //Setting properties for Missile's Fired text and adding everything onto the scene
                missilesFired.setStyle("-fx-font-size: 12");
                missilesFired.setFill(Color.YELLOW);
                missilesFired.setFont(arcade);
                p.getChildren().addAll(bonus, missilesFired);

            } else {//If user fires more than 250 shots: No bonus points

                //Text to inform user of no bonus points earned and the number of shots they fired
                Text bonus = new Text(130, 370, "Bonus Points: " + 0);
                Text missilesFired = new Text(130, 390, "Missile's Fired: " + Main.hitCounter);

                //Set the properties of the bonus and missilesFired text and add it onto the scene
                bonus.setStyle("-fx-font-size: 12");
                bonus.setFill(Color.YELLOW);
                bonus.setFont(arcade);
                missilesFired.setStyle("-fx-font-size: 12");
                missilesFired.setFill(Color.YELLOW);
                missilesFired.setFont(arcade);
                p.getChildren().addAll(bonus, missilesFired);
            }
            stopUFOMovement = true; //This variable indicates that the game is now over
        }

        return stopUFOMovement;
    }

    //Data Structures:
    //This method uses the created 2D ImageView array and fills it up with enemy aliens
    // The array is composed of 4 rows and 10 columns
    void displayGrid() {

        for (int row = 0; row < EnemyRow(); row++) { //For each row in the array
            for (int col = 0; col < EnemyColumn(); col++) { //For each column in the array

                //As the array is filled, it is surrounded by try-catch so the array can't go out of the index range
                try {

                    //Fill each row column spot with the enemy alien image
                    alienGrid[row][col] = new ImageView(largeInvader);

                    //Preserving the aspect ratio of the original image when it is fitted into the array
                    alienGrid[row][col].setPreserveRatio(true);

                    //Space out the aliens in the array rather than having them all on top of each other
                    alienGrid[row][col].setX(col * 50);
                    alienGrid[row][col].setY(row * 50);
                    alienGrid[row][col].setFitWidth(EnemyEdge()); //Set width of each individual alien in the array
                    p.getChildren().add(alienGrid[row][col]); //Add each alien to the scene

                } catch (ArrayIndexOutOfBoundsException e) {
//
                }
            }
        }
        time -= 3;

        Duration dur = new Duration(time);
        KeyFrame frame = new KeyFrame(dur, e -> alienMovement());
        timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    ImageView[][] getAlienGrid() {
        return alienGrid;
    }

    ImageView getUfoM() {
        return ufoM;
    }
}



/*

    Reference:

    1. Enemy Movement
        GitHub - https://github.com/Shindanaide/SpaceInvaders

*/
