package sample;

public class Properties {


    private int enemyEdge = 40;
    private int enemyRow = 4;
    private int enemyColumn = 10;
    private int alienSpeed = 1;


    public int EnemyEdge() {

        //The edge enemy aliens can go to

        return enemyEdge;
    }

    public int EnemyRow() {

        //Number of enemy alien rows

        return enemyRow;
    }


    public int EnemyColumn() {

        //Number of enemy alien columns

        return enemyColumn;
    }

    public int AlienSpeed() {

        //Speed of enemy aliens

        return alienSpeed;
    }

}

