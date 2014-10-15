package andreas.backgammon;


import java.net.*;
import java.io.*;


public class Main {

    public static int makeMoves(int noMoves, int diceOneResult, int diceTwoResult){

    return 0;
    }

    public static void main(String args[]) {

        int noMoves;

        backgammonBoard newBoard = new backgammonBoard();
        newBoard.setupBoard();
        backgammonDice dice1 = new backgammonDice();
        dice1.rollDice();
        System.out.println("Dice roll 1: " + dice1.getDiceRoll());
        backgammonDice dice2 = new backgammonDice();
        dice2.rollDice();
        System.out.println("Dice roll 2: " + dice2.getDiceRoll());

        if(dice1.getDiceRoll() == dice2.getDiceRoll()){
            noMoves = 4;
        }
        else{
            noMoves = 2;
        }

        makeMoves(noMoves, dice1.getDiceRoll(), dice2.getDiceRoll());

    }
}



