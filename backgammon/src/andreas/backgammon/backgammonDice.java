package andreas.backgammon;

import java.util.Random;
/**
 * Created by Antreas Christofi on 05/10/2014.
 */
public class backgammonDice { //WORKS, DONE

    //A dice has these properties
    private int dice1;   // Number showing on the first die.
    private int dice2;   // Number showing on the second die.


    //Constructor
    public backgammonDice(){
        rollDice();
    }


    //Methods for the dice

    public void rollDice(){
        dice1 = (int)(Math.random()*6) + 1;
        dice2 = (int)(Math.random()*6) + 1;

    }

    public int getDiceRoll1(){
        return dice1;
    }
    public int getDiceRoll2() { return dice2; }

}
