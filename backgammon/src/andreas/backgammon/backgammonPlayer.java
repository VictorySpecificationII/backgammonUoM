package andreas.backgammon;

/**
 * Created by Antreas Christofi on 10/10/2014.
 */
public class backgammonPlayer{
    
    //A player has these properties
    int score;
    int playerNumber;
    String playerColor;
    int yourTurn;
    int bar;
    int numbersFromRoll1;
    int numbersFromRoll2;

    //Constructor
    public backgammonPlayer(){
        score = 0;//o kathe paixtis eshei j ena score p stin arxi en 0
        playerNumber = 0;//enna apofasistei meta tin monin tin zarkan
        yourTurn = 0;
        bar = 0;
        playerColor = "n";//dimiourgas enan instance, pou enna tou dokeis values meta
        numbersFromRoll1 = 0;//tin ora pou sirneis zarka, to apotelesma en gia sena taxa en ta noumera p enna pai3eis
        numbersFromRoll2 = 0;//to idio pou panw
    }
    //method gia na allasei to score kathe telos tou paixnithkiou
    public void setScore(){
        score++;
    }
    //method gia na dias seira stous paixtes, taxa pios enna paiksei protos
    public void setPlayerNumber(int newPlayerNumber){
        playerNumber = newPlayerNumber;
    }
    //method gia na dokeis ston kathe paixti to xroma p enna 8kialeksei
    public void setPlayerColor(String newPlayerColor){
        playerColor = newPlayerColor;
    }

    public void setNumbersFromRoll1(int newDiceRoll1){
        numbersFromRoll1 = newDiceRoll1;
    }


    public void setNumbersFromRoll2(int newDiceRoll2){
        numbersFromRoll2 = newDiceRoll2;
    }
    public int getPlayerNumber(){
        return playerNumber;
    }

}
