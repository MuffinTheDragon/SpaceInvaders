package sample;

class Properties {


    private int enemyEdge = 40;
    private int enemyRow = 4;
    private int enemyColumn = 10;
    private int alienSpeed = 3;
    private int ufoSpeed = 8;


    int EnemyEdge() {

        //The edge enemy aliens can go to

        return enemyEdge;
    }

    int EnemyRow() {

        //Number of enemy alien rows

        return enemyRow;
    }


    int EnemyColumn() {

        //Number of enemy alien columns

        return enemyColumn;
    }

    int AlienSpeed() {

        //Speed of enemy aliens

        return alienSpeed;
    }

    int ufoSpeed() {

        //Speed of enemy UFO

        return ufoSpeed;
    }

}

