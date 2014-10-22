package andreas.backgammon;

/**
 * Created by Antreas Christofi on 10/10/2014.
 */
public class backgammonPlayer{
    
    //A player has these properties
    String name;
    int score;
    int playerNumber;
    String playerColor;
    int yourTurn;
    int bar;
    int numbersFromRoll1;
    int numbersFromRoll2;
    int moves;

    //Constructor
    public backgammonPlayer(){
        name = "none";
        score = 0;//o kathe paixtis eshei j ena score p stin arxi en 0
        playerNumber = 0;//enna apofasistei meta tin monin tin zarkan
        yourTurn = 0;
        bar = 0;
        playerColor = "n";//dimiourgas enan instance, pou enna tou dokeis values meta
        numbersFromRoll1 = 0;//tin ora pou sirneis zarka, to apotelesma en gia sena taxa en ta noumera p enna pai3eis
        numbersFromRoll2 = 0;//to idio pou panw
        moves = 0;
    }

    public void setYourTurn(int turn){yourTurn = turn;}
    public int getYourTurn(){return yourTurn;}

    public void setScore(){score++;}
    public int getScore(){return score;}

    public void setBar(int newBar){
        bar = newBar;
    }
    public int getBar(){
        return bar;
    }

    public void setPlayerNumber(int newPlayerNumber){playerNumber = newPlayerNumber; }
    public int getPlayerNumber(){
        return playerNumber;
    }

    public void setPlayerColor(String newPlayerColor){
        playerColor = newPlayerColor;
    }
    public String getPlayerColor() {return playerColor;}

    public void setNumbersFromRoll1(int newDiceRoll1){
        numbersFromRoll1 = newDiceRoll1;
    }
    public int getNumbersFromRoll1(){return numbersFromRoll1;}

    public void setNumbersFromRoll2(int newDiceRoll2){
        numbersFromRoll2 = newDiceRoll2;
    }
    public int getNumbersFromRoll2(){return numbersFromRoll2;}

    public void setMoves(int amount){moves = amount;}
    public int getMovesLeft(){return moves;}

    public void setName(String newName){name = newName;}
    public String getName(){return name;}



}
