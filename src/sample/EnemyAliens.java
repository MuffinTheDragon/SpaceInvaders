package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EnemyAliens extends Properties {


    /*

    Use formation.png

    */


    Pane p;

    ImageView[][] alienGrid;

    Image largeInvader;
    Image mediumInvaderA;
    Image mediumInvaderB;
    Image smallInvader;

    public EnemyAliens(Pane p) {
        this.p = p;
    }

    public ImageView getLargeInvader() {
        largeInvader = new Image("/Images/large_invader_a.png");
        return new ImageView(largeInvader);
    }

    public ImageView getMediumInvaderA() {
        mediumInvaderA = new Image("/Images/medium_invader_a.png");
        return new ImageView(mediumInvaderA);
    }

    public ImageView getMediumInvaderB() {
        mediumInvaderB = new Image("/Images/medium_invader_b.png");
        return new ImageView(mediumInvaderB);
    }

    public ImageView getSmallInvader() {
        smallInvader = new Image("/Images/small_invader_a.png");
        return new ImageView(smallInvader);
    }

    public void createAlienGrid() {
        int row = 0;
        alienGrid = new ImageView[4][10];
        while (row < 4) {
            for (int col = 0; col < alienGrid[0].length; col++) {
                if (row == 0) {
                    alienGrid[0][col] = getLargeInvader();
                } else if (row == 1) {
                    alienGrid[1][col] = getMediumInvaderA();
                } else if (row == 2) {
                    alienGrid[2][col] = getMediumInvaderB();
                } else if (row == 3) {
                    alienGrid[3][col] = getSmallInvader();
                }
            }
            row++;

        }

    }

    public void displayGrid() {

        for (int row = 0; row < alienGrid.length; row++) {
            for (int col = 0; col < alienGrid[0].length; col++) {
//                ImageView image = new ImageView(alienGrid[row][col]);
                Tools.setCoordinates(alienGrid[row][col], 500, 250); //Figure out how to print them in array format
                p.getChildren().add(alienGrid[row][col]);
            }
        }
    }
}

