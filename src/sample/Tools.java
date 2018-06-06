package sample;

import javafx.scene.Node;
import javafx.scene.media.MediaPlayer;

public class Tools {


    public static void setCoordinates(Object a, int x, int y) {
        ((Node) a).setLayoutX(x);
        ((Node) a).setLayoutY(y);
    }

    public static void stopMusic(MediaPlayer mediaPlayer) {
        mediaPlayer.stop();
    }

}