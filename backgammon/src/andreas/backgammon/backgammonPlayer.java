package andreas.backgammon;

/**
 * Created by Antreas Christofi on 10/10/2014.
 */
public class backgammonPlayer{
    
    //A player has these properties
    int score;
    int playerNumber;
    String playerColor;

    public backgammonPlayer(int newPlayerNumber, String newPlayerColor){
        score = 0;//initial score 0
        playerNumber = newPlayerNumber;//to be decided after single dice throw
        playerColor = newPlayerColor;//player1 is black, player 2 is white
    }

    public void incrementScore(int newScore){
       if(this.playerNumber == 1 && backgammonBoard.gameOver() == 1)
        score++;

        if(this.playerNumber == 2 && backgammonBoard.gameOver() == 2)
            score++;

    }

}
