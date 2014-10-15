package andreas.backgammon;

import java.util.Random;
/**
 * Created by Antreas Christofi on 05/10/2014.
 */
public class backgammonDice { //WORKS, DONE

    //A dice has these properties
    Random diceNumbers;
    int result;


    //Constructor
    public backgammonDice(){
       int diceOutput = result;
    }


    //Methods for the dice

    public void rollDice(){
        diceNumbers = new Random();
        result = diceNumbers.nextInt(7 - 1) + 1;

    }

    public int getDiceRoll(){
        return result;
    }
    public void setResult(int newResult){
      result = newResult;
    }
}
