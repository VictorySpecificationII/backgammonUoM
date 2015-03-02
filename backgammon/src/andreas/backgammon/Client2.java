package andreas.backgammon;

/**
 * Created by Antreas Christofi on 01/03/2015.
 */

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;



import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.net.*;
import java.io.*;
//todo: Finish making moves ✓
//todo: fix deck✓
//todo: implement hitting, partly works (03/02/15)
//todo: implement bearing off -> partly done, 2 methods: possible, actual act - need to implement into gameLoop function
//todo: game over condition✓;
//todo: THEN LOGIC IS FINISHED,
//todo: GUI✓
//todo: SERVER
//todo: AI
//*BUG: When hitting, eg state 1: vector 12-> 1, w. Hit from 14, turns to this: vector 12 -> 2, w - check board class for it
//BUG FIX*:Fixed above bug, definitely fixed, tested at 1551 on 05/12/14
//BUG FIX: Fixed bug where they wouldn't stack up past 5 - definitely fixed, tested at 0427 on 13/11/14

public class Client2 {
    //Global variables section
    //--------------------------------------------------------------------
    public static String enemyColor = "n";
    public static int enemyBar = 0;
    public static int target = 0;
    public static int noGames = 0;
    public static backgammonPlayer currentPlayer;
    public static backgammonPlayer enemyPlayer;
    public static backgammonPlayer player1;
    public static backgammonPlayer player2;
    public static backgammonBoard board;
    public static backgammonDice dice;
    public static String name1;

    public static int currentRock;
    public static int currentRoll;
    public static boolean twoMoves;
    public static boolean fourMoves;
    public static Scanner reader;
    public static boolean whiteRocksOnBar = false;
    public static boolean blackRocksOnBar = false;
    public static boolean bearOff = false;
    //public static boolean hit = false;
    public static Long uuid;
    //--------------------------------------------------------------------
    //So here you are: You've setup the deck, had your initial roll, and you hit the game loop.
    //You've thrown your dice, determined your moves, chosen your rock, you've check if it can move,
    // you've checked if the destination is reachable, and all of that worked out. Now it's time to interact with the
    //board and move the rocks.
    public static void makeMoves(){

        board.removeStone(currentRock, currentPlayer.getPlayerColor());//You move your stone, and imagine a pause: You move, therefore removing from one place
        board.addStone(target, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);//and adding to another.
        System.out.println(board.deck.toString());//The stones change positions on the deck and its visible
        System.out.println(board.colors.toString());//The colors of the stones follow them too, and they are visible.
        currentPlayer.setMoves(currentPlayer.getMovesLeft() - 1);//You've officially done your move, so you're a move down.

        if(twoMoves) {//If your moves are still two
            if (currentRoll == currentPlayer.getNumbersFromRoll1()) {//You check whether your current roll matches either dice. Now you check the first one. If it does,
                currentPlayer.setNumbersFromRoll1(0);//You set that to zero, because you are not allowed to play the same number twice unless it's doubles.
                System.out.println("Roll 1 " + currentPlayer.getNumbersFromRoll1());//You then mutter it out.
            }

            if (currentRoll == currentPlayer.getNumbersFromRoll2()) {//If it doesn't match the first dice, if it matches the second,
                currentPlayer.setNumbersFromRoll2(0);//You set your second number to zero, because you can't play that twice either and besides, you by this point have one moves left.
                System.out.println("Roll 2 " + currentPlayer.getNumbersFromRoll2());//You mutter it out.
            }
        }


        System.out.println("Moves left for "+ currentPlayer.getName() + ": "+currentPlayer.getMovesLeft());
        //If you've played your last move, and you have no moves left,
        if(currentPlayer.getMovesLeft() == 0){
            twoMoves = false;//you no longer have two moves,
            fourMoves = false;//you no longer have four moves.
            if(currentPlayer.getPlayerNumber() == 1){//If the current player is the first player
                //  System.out.println("first if");

                currentPlayer.setYourTurn(0); //it is no longer their turn but
                enemyPlayer.setYourTurn(1);//it is their mate's turn.

                backgammonPlayer tempPlayer;//-------▽
                tempPlayer = currentPlayer; //------>SWAP PLAYERS MAGIC
                currentPlayer = enemyPlayer;//-------△
                System.out.println("Current player: " + currentPlayer.getName());
                enemyPlayer = tempPlayer;//Now that their mate is the current player they are the enemy player.
                System.out.println("Enemy player: " + enemyPlayer.getName());

            }//OR, the current player was player 2 and not player 1
            else if(currentPlayer.getPlayerNumber() == 2){//If the current player is the second player
                // System.out.println("second if");

                currentPlayer.setYourTurn(0);//it is no longer their turn but
                enemyPlayer.setYourTurn(1);//it is their mate's turn.

                backgammonPlayer tempPlayer;//-------▽
                tempPlayer = currentPlayer; //------>SWAP PLAYERS
                currentPlayer = enemyPlayer;//-------△

                System.out.println("Current player: " + currentPlayer.getName());
                enemyPlayer = tempPlayer;//Now that their mate is the current player they are the enemy player.
                System.out.println("Enemy player: " + enemyPlayer.getName());
            }
        }
    }

    //They setup the board.
    public static void setupBoard(){
        board.setupBoard();//They setup the board and put the rocks in their places while sipping on their frape.
        CLI.draw(board);
    }

    //In this episode of Roadkill, we check whether the player tends to forget his color. We check whether
    //the board he is trying to move can actually be moved. That is, if the rock he selected has the same color
    //as the color he's playing.
    public static boolean isCurrentRockAllowed(backgammonPlayer currentPlayer, int currentRock){

        String currentPlayerColor = currentPlayer.getPlayerColor();//It's your turn now. You recall the color you have been assigned.
        String desiredRockColor = board.colors.get(currentRock);//You check whether the color of the rock you are trying to move.
        System.out.println("current player color: "+currentPlayerColor);//Your brain outputs this,
        System.out.println("desired rock color: "+desiredRockColor);//and this.

        if(currentPlayerColor == desiredRockColor){//You're currently thinking; If my color matches the color of the rock I want to move,
            System.out.println("Ok, rock allowed to move");
            return true;//it can be moved.
        }
        System.out.println("Rock not allowed to move");
        return false;//It can't be moved otherwise.
    }


    public static boolean isCurrentMoveLegit(backgammonPlayer currentPlayer, int currentRock, int currentRoll) {
        //Now, you check whether the move you chose is legit.

        //You recall that you have a color. You think: If your color is white, but you have rocks on the bar
        if ((currentPlayer.playerColor == "w") && (board.thereExistRocksOnBar(currentPlayer.getPlayerColor())) == 1) {
            System.out.println("current player has rocks on his bar and is white");
            whiteRocksOnBar = true;
            return false;//You can't move.
        }
        //OR, if your color is black, but you have rocks on the bar,
        else if ((currentPlayer.playerColor == "b") && (board.thereExistRocksOnBar(currentPlayer.getPlayerColor())) == 2) {
            System.out.println("current player has rocks on his bar and is black");
            blackRocksOnBar = true;
            return false;//you still can't move.
        }


        //You've established that there's no rocks on the bar, so all is good. Now, you check where you can move according to the roll you chose to play.
        if (currentPlayer.playerColor == "w"){//If you are moving the white rocks,
            target = currentRock + currentRoll;//you are moving right.
        }
        //Then, it hits you. If you're not moving white rocks,
        else if (currentPlayer.playerColor == "b"){//if you're moving black rocks,
            target = currentRock - currentRoll;//then you're moving to the left.
        }

        int targetVector = board.deck.get(target);//You now look at the vector and the rocks on it,
        String targetVectorC = board.colors.get(target);//and you also look at the colors

        //For obvious reasons, you cannot move beyond the deck.
        if(target > 23 || target < 0){//If your current position and your roll go over the number of vectors,
            System.out.println("violates board's boundaries");

            return false;//you can't move that rock.
        }

        //Interesting. So far, all is good - you're not moving in a silly way, or moving when there's rocks on the bar.
        //You look at the vectors themselves now, trying to figure out whether you can actually land and hit or just land.

        if(targetVector == 0){//If the vector you want to move to is empty,
            System.out.println("Target vector empty, proceed");
            return true;//by all means, go for it!
        }
        //OR, if the vector you want to move to has one rock of your color,
        else if((targetVector == 1)&&(targetVectorC.equals(currentPlayer.getPlayerColor()))){
            System.out.println("target has one rock of current player's color, proceed");
            return true;//lucky you, close it up!
        }
        //OR, if the vector you want to move to has more than 1 rocks of your color,
        else if((targetVector > 1)&&(targetVectorC.equals(currentPlayer.getPlayerColor()))){
            System.out.println("target has more than one rocks of current player's color, proceed");
            return true;//Stack them up!
        }
        //OR, the vector you want to move to has one rock of your opponent's color,
        else if((targetVector == 1)&&(targetVectorC.equals(enemyPlayer.getPlayerColor()))){
            System.out.println("target has one rock of enemy's color, proceed to hit and land");
            //hit = true;//hit him!
            return true;//Yes, you can move there, all is good.
        }
        //OR, the vector you want to move to, has more than one rocks of your enemy's color,
        else if((targetVector > 1)&&(targetVectorC.equals(enemyPlayer.getPlayerColor()))){
            System.out.println("More than one stones of enemy color, move not allowed");
            return false;//tough luck, can't move there.
        }
        else{
            //Congratulations, you have somehow managed to break the universe. YOU ARE KING!
            System.out.println("Something is wrong at the isCurrentMoveLegit method");
            return false;//Aaaand you return false cause it's somehow broken.
        }
    }

    public static void initialGame(){
        //Welcome to the initial piece of the game.

        currentPlayer = player1;//Someone has to go first.
        dice.rollDice();//The die is rolled,
        System.out.println("Dice roll for " + player1.getName() + ":" + dice.getDiceRoll1());
        currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//and the first player registers the first number.

        //currentPlayer = player2;//Then, the second player picks up the die
        //dice.rollDice();//and throws it.
        //System.out.println("Dice roll for " + player2.getName() + ":" + dice.getDiceRoll2());
        //currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//They then register the number they got.
        sendRollToServer();
        receiveRollFromServer();
        //Now you look at your opponent and ask him, "what number did you roll?"
        if (player1.getNumbersFromRoll1() == player2.getNumbersFromRoll1()) {//He then tells you, and you tell him, and you check. If they are equal,

            System.out.println("");
            System.out.println("Same dice, rerolling.");
            System.out.println("");}
        //You reroll cause you can't both start first.
        //it goes on for a while, and you keep rolling the same numbers.
        while ((player1.getNumbersFromRoll1() == player2.getNumbersFromRoll1())||(player2.getNumbersFromRoll1() == 0)) {//While they are the same...

            currentPlayer = player1;//Someone has to go first.
            dice.rollDice();//The first player throws again
            System.out.println("");
            System.out.println("Dice roll for " + player1.getName()+ ":" + dice.getDiceRoll1());
            currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//and registers the number he rolled.
            sendRollToServer();
            receiveRollFromServer();
            //currentPlayer = player2;//Then, it's your opponent's turn.
            //dice.rollDice();//They throw again,
            //System.out.println("Dice roll for " + player2.getName() + ":" + dice.getDiceRoll2());
            //currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//and register the number. They compare after this.

        }

        //Finally, no doubles.

        if (player1.getNumbersFromRoll1() > player2.getNumbersFromRoll1()) {//If your die has a larger number than your opponent's,
            player1.setPlayerNumber(1);//lucky you, you are player 1!
            player1.setYourTurn(1);//It's obviously your turn, so you establish that,
            player1.setPlayerColor("w");//along with the fact that you're moving white rocks.
            player1.setBar(0);//Lastly, your bar is number 0.

            player2.setPlayerNumber(2);//Unlucky him, he's player 2
            player2.setYourTurn(0);//It's not his turn, and you let him know.
            player2.setPlayerColor("b");//You also let him know that he is moving black rocks.
            player2.setBar(1);//Lastly, his bar is number 1.

            currentPlayer = player1;//You are the current player,
            enemyPlayer = player2;//and your mate is your enemy player.
//
            System.out.println("Current player object attributes:");
            System.out.println("player name: " + currentPlayer.getName());
            System.out.println("player number: " + currentPlayer.getPlayerNumber());
            System.out.println("player color: " + currentPlayer.getPlayerColor());
            //System.out.println("player roll 1: " + currentPlayer.getNumbersFromRoll1());
            //System.out.println("player roll 2: " + currentPlayer.getNumbersFromRoll2());
            //System.out.println("player bar number: " + currentPlayer.getBar());
            //System.out.println("player moves left: " + currentPlayer.getMovesLeft());
            //System.out.println("player score: " + currentPlayer.getScore());
            System.out.println("player your turn: " + currentPlayer.getYourTurn());
            System.out.println("");

            // System.out.println("Enemy player object attributes:");
            // System.out.println("player name: " + enemyPlayer.getName());
            // System.out.println("player number: " + enemyPlayer.getPlayerNumber());
            // System.out.println("player color: " + enemyPlayer.getPlayerColor());
            //System.out.println("player roll 1: " + enemyPlayer.getNumbersFromRoll1());
            //System.out.println("player roll 2: " + enemyPlayer.getNumbersFromRoll2());
            //System.out.println("player bar: " + enemyPlayer.getBar());
            //System.out.println("player moves left: " + enemyPlayer.getMovesLeft());
            //System.out.println("player score: " + enemyPlayer.getScore());
            //System.out.println("player your turn: " + enemyPlayer.getYourTurn());

        }


        else{//OR, you snap out of that and you realize that his number is larger than yours in which case,
            player2.setPlayerNumber(1);//he is player 1,
            player2.setYourTurn(1);//and it's his turn, you establish that.
            player2.setPlayerColor("w");//You let him know that he's playing with white stones,
            player2.setBar(0);//and his bar is number 0.



            player1.setPlayerNumber(2);//You are playing second,
            player1.setYourTurn(0);//and so far it's not your turn.
            player1.setPlayerColor("b");//You are playing with black rocks,
            player1.setBar(1);//and your bar is number 1.

            currentPlayer = player2;//Your opponent is the current player
            enemyPlayer = player1;//and you are the enemy player.

            System.out.println("Current player object attributes:");
            System.out.println("player name: " + currentPlayer.getName());
            System.out.println("player number: " + currentPlayer.getPlayerNumber());
            System.out.println("player color: " + currentPlayer.getPlayerColor());
            //System.out.println("player roll 1: " + currentPlayer.getNumbersFromRoll1());
            // System.out.println("player roll 2: " + currentPlayer.getNumbersFromRoll2());
            //  System.out.println("player bar: " + currentPlayer.getBar());
            //  System.out.println("player moves left: " + currentPlayer.getMovesLeft());
            //  System.out.println("player score: " + currentPlayer.getScore());
            //  System.out.println("player your turn: " + currentPlayer.getYourTurn());
            System.out.println("");

            // System.out.println("Enemy player object attributes:");
            // System.out.println("player name: " + enemyPlayer.getName());
            // System.out.println("player number: " + enemyPlayer.getPlayerNumber());
            //  System.out.println("player color: " + enemyPlayer.getPlayerColor());
            // System.out.println("player roll 1: " + enemyPlayer.getNumbersFromRoll1());
            // System.out.println("player roll 2: " + enemyPlayer.getNumbersFromRoll2());
            // System.out.println("player bar: " + enemyPlayer.getBar());
            // System.out.println("player moves left: " + enemyPlayer.getMovesLeft());
            // System.out.println("player score: " + enemyPlayer.getScore());
            // System.out.println("player your turn: " + enemyPlayer.getYourTurn());
            System.out.println("");
        }
        player1.setNumbersFromRoll1(0);//now he has to roll to get numbers again
        player1.setNumbersFromRoll2(0);//now he has to roll to get numbers again
        player2.setNumbersFromRoll1(0);//now he has to roll to get numbers again
        player2.setNumbersFromRoll2(0);//now he has to roll to get numbers again
    }

    public static int targetVectorToLandOn;

    public static void getOffTheBarFirst(){
        Scanner diceInput = new Scanner(System.in);

        System.out.print(currentPlayer.getName() + ", where do you wish to land?");//You think, where do I land now??
        int diceChosen = diceInput.nextInt();//You then pick a spot, by looking at your dice.

        if(currentPlayer.getPlayerColor() == "b"){//if black, land in enemy area
            System.out.println("Player color b, get off");
            switch(diceChosen){
                case 1: targetVectorToLandOn = 0;break;
                case 2: targetVectorToLandOn = 1;break;
                case 3: targetVectorToLandOn = 2;break;
                case 4: targetVectorToLandOn = 3;break;
                case 5: targetVectorToLandOn = 4;break;
                case 6: targetVectorToLandOn = 5;break;
            }

            if (board.deck.get(targetVectorToLandOn) == 0) {
                System.out.println("1b");//WORKS
                board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);//remove one stone from the bar
                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);//add to target

                currentPlayer.setMoves(currentPlayer.getMovesLeft() - 1);//and now has one less move
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) == 1) && (board.colors.get(currentPlayer.getPlayerColor()) == "b")) {
                System.out.println("2b");
                //board.removeStoneFromTheBar(currentPlayer.getBar(),targetVectorToLandOn, currentPlayer.getPlayerColor());
                board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                //board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }

            else if ((board.deck.get(targetVectorToLandOn) > 1) && (board.colors.get(currentPlayer.getPlayerColor()) == "b")) {
                System.out.println("3b");
                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                // currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) == 1) && (board.colors.get(enemyPlayer.getPlayerColor()) == "w")) {
                System.out.println("4b");
                //board.removeStoneFromTheBar(currentPlayer.getBar(),targetVectorToLandOn, currentPlayer.getPlayerColor());
                //board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                board.addStone(target, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                //board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) > 1) && (board.colors.get(enemyPlayer.getPlayerColor()) == "w")){
                System.out.println("Can't land there, more than one rocks of enemy color");
            }
            else
                System.out.println("Something's off @ getOff method 1");
        }//todo: zero move chosen for get off bar
        //todo: fix problems when > or = 1 stones present at vector,

        if(currentPlayer.getPlayerColor() == "w"){
            System.out.println("Player color b, get off");
            switch(diceChosen){
                case 1: targetVectorToLandOn = 23;break;
                case 2: targetVectorToLandOn = 22;break;
                case 3: targetVectorToLandOn = 21;break;
                case 4: targetVectorToLandOn = 20;break;
                case 5: targetVectorToLandOn = 19;break;
                case 6: targetVectorToLandOn = 18;break;
            }

            if (board.deck.get(targetVectorToLandOn) == 0) {
                System.out.println("1w");//WORKS
                board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                // board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft() - 1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) == 1) && (board.colors.get(currentPlayer.getPlayerColor()) == "w")) {
                System.out.println("2w");
                // board.removeStoneFromTheBar(currentPlayer.getBar(),targetVectorToLandOn, currentPlayer.getPlayerColor());
                board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                //  board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }

            else if ((board.deck.get(targetVectorToLandOn) > 1) && (board.colors.get(currentPlayer.getPlayerColor()) == "w")) {
                System.out.println("3w");

                board.addStone(targetVectorToLandOn, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                //  board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) == 1) && (board.colors.get(enemyPlayer.getPlayerColor()) == "b")) {
                System.out.println("4w");

                //board.removeStoneFromTheBar(currentPlayer.getBar(),targetVectorToLandOn, currentPlayer.getPlayerColor());
                board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                board.addStone(target, currentPlayer.getPlayerColor(), enemyPlayer, currentPlayer);
                //  board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getBar())-1);
                currentPlayer.setMoves(currentPlayer.getMovesLeft()-1);
                System.out.println("Moves left for current player:" + currentPlayer.getMovesLeft());
                if(twoMoves){
                    if(diceChosen == currentPlayer.getNumbersFromRoll1()){
                        currentPlayer.setNumbersFromRoll1(0);
                    }
                    else if(diceChosen == currentPlayer.getNumbersFromRoll2()){
                        currentPlayer.setNumbersFromRoll2(0);
                    }

                }
            }
            else if ((board.deck.get(targetVectorToLandOn) > 1) && (board.colors.get(enemyPlayer.getPlayerColor()) == "b")){
                System.out.println("Can't land there, more than one rocks of enemy color");
            }
            else
                System.out.println("Something's off @ getOff method 2");
        }
    }


    public static int targetVectorToBearOff;
    public static boolean bearOffPossible(){
        /*to be able to bear off, all 15 stones must be within the current player's home board,
        and none must be on the bar or the battlefield.
         */
//First two ifs are to choose vector depending on color of currentplayer
        if(currentPlayer.getPlayerColor() == "b"){//if b
            System.out.println("Player color b, get off");
            switch(currentRoll){
                case 1: targetVectorToBearOff = 0;break;
                case 2: targetVectorToBearOff = 1;break;
                case 3: targetVectorToBearOff= 2;break;
                case 4: targetVectorToBearOff= 3;break;
                case 5: targetVectorToBearOff= 4;break;
                case 6: targetVectorToBearOff= 5;break;
            }
            if(currentPlayer.getPlayerColor() == "w"){//if w
                System.out.println("Player color b, get off");
                switch(currentRoll){
                    case 1: targetVectorToBearOff = 23;break;
                    case 2: targetVectorToBearOff = 22;break;
                    case 3: targetVectorToBearOff= 21;break;
                    case 4: targetVectorToBearOff= 20;break;
                    case 5: targetVectorToBearOff= 19;break;
                    case 6: targetVectorToBearOff= 18;break;
                }

                String[] tempColorsArray = new String[26];//create new array
                int i;//counter for array
                int j = 0;//counter for hashmap
                int limiter = 18;//limit of array

                for (HashMap.Entry<Integer, String>entry : board.colors.entrySet())
                {
                    tempColorsArray[j] = entry.getValue();
                }

                for(i = 0; i < limiter; i++){
                    if((tempColorsArray[i] != currentPlayer.getPlayerColor()&&(i < limiter))){
                        bearOff = true;
                        i++;
                    }
                    else
                        bearOff = false;
                }
            }
        }
        if (bearOff == true)
            return true;
        else
            return false;
    }

    public static void bearOff(){


        board.bar.put(currentPlayer.getBar(), board.bar.get(currentPlayer.getPlayerColor())+1);
        board.deck.put(targetVectorToBearOff, board.deck.get(targetVectorToBearOff) - 1);
        if (board.deck.get(targetVectorToBearOff) == 0)
            board.colors.put(targetVectorToBearOff, "n");
        else
            System.out.println("Something's off, bear off method");
    }


    public static void gameLoop() {

        Scanner reader = new Scanner(System.in);

        while ((board.gameOver() == 0) && (currentPlayer.yourTurn == 1)) {//If the game hasn't ended and it's still your turn,

            CLI.draw(board);


            if (currentPlayer.getMovesLeft() == 0) {//if you have no turns, you haven't rolled your dice yet,
                dice.rollDice();//you throw the dice.
                System.out.println("Die 1: " + dice.getDiceRoll1() + ", " + "Die 2: " + dice.getDiceRoll2());//You look at your dice and register the numbers in your head:
                currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//You look and register number 1
                currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//and you look and register number 2

                //Now comes the part where you recall from your head how many moves you're playing according to the dice numbers.

                if (currentPlayer.numbersFromRoll1 == currentPlayer.numbersFromRoll2) {// You look at the dice, and the numbers are the same
                    currentPlayer.setMoves(4);//Lucky you, you get doubles.
                    currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//You remember the first number,
                    currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//you remember the second number
                    fourMoves = true;//You realize you have 4 moves,
                    System.out.println("You get 4 moves");// and you keep in mind the numbers cause those are your 4 steps of the numbers you just rolled.

                }//Then, your thought shifts onto the next scenario.
                else if (currentPlayer.numbersFromRoll1 != currentPlayer.numbersFromRoll2) {//You look at the numbers, and they are different.
                    currentPlayer.setMoves(2);//Two moves, sorry.
                    currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//You register the first number, that's move one,
                    currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//and you register the second number, that'll be move two.
                    twoMoves = true;//You realize you have two moves,
                    System.out.println("You get 2 moves");// you mutter it for the record and you prepare to play.

                }//Now that you've got your number of moves nad actual moves,  you start moving the stones around.
            }
            //By this point, you have your numbers - now you check whether you have stones ont teh bar
            if (board.thereExistRocksOnBar(currentPlayer.getPlayerColor()) != 0) {
                getOffTheBarFirst();//and if you do, well, time to get them off
                continue;
            } else {

                System.out.print(currentPlayer.getName() + ", which rock would you like to move?");//You think, which one?
                currentRock = reader.nextInt();//Then you pick a number.
                boolean check = isCurrentRockAllowed(currentPlayer, currentRock);//You look around the deck, to see whether you can move or not.
                while (check == false) {// While you can't find a good destination for your stone...
                    System.out.println("Invalid entry, the stone you are trying to move either isn't there or isn't yours");//You realize you are moving the wrong rock, or a rock that doesn't exist.
                    System.out.print("which rock would you like to move?");//You think again.
                    currentRock = reader.nextInt();//You then decide your next move.
                    check = isCurrentRockAllowed(currentPlayer, currentRock);//You run a check on your move.
                    if (check == true) {//You realize it. This might be correct. If it's really true,
                        System.out.println("Ok, rock allowed");//You think: Yes, I can move it there.
                        break;//Then, you snap out of it - Congratulations, you have found a move.
                    }
                }

                System.out.println("Roll 1: " + currentPlayer.getNumbersFromRoll1() + ", " + "Roll 2: " + currentPlayer.getNumbersFromRoll2());//Here comes the move. You recall the numbers you have.
                System.out.println("Which roll are you playing?");//You think: Which move should go first?
                currentRoll = reader.nextInt();//You decide you will use that move.
                if ((currentRoll == currentPlayer.getNumbersFromRoll1()) || (currentRoll == currentPlayer.getNumbersFromRoll2())) {//To be able to move, your move number must equal the result on the equivalent dice.
                    System.out.println("Checking if move is legal.");//You then check if the move its legit
                } else {//OR, then you realize that it isn't. Then it hits you - you confused your number and moved it in the wrong way, and disobeyed your dice. Sorry, can't move.
                    System.out.println("Not allowed");//You realize it,
                    System.out.println("Which roll are you playing?");//and you think again.
                    currentRoll = reader.nextInt();//You decide again.

                }


                check = isCurrentMoveLegit(currentPlayer, currentRock, currentRoll);//You check whether your move is legit.
                System.out.println("" + check);//You then mutter out the result.
                if (check == true)//Knowing the result, if it's true
                    makeMoves();//you proceed to make your move
                System.out.println("Game status: " + board.gameOver());//and you then check to see whether the game has ended.
                System.out.println(board.deck.toString());
                System.out.println(board.colors.toString());
                System.out.println(board.bar.toString());
                System.out.println(board.barColors.toString());
                CLI.draw(board);
            }
        }
    }



    public static void receiveTurn(Socket socket) throws IOException {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());//create stream
            InputStreamReader isr = new InputStreamReader(in);//create stream reader
            currentPlayer.yourTurn = isr.read();//read stream
            isr.close();
            in.close();//close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTurn(Socket socket){
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(out);
            osw.write(currentPlayer.yourTurn);
            osw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void sendRollToServer(){
        //Sending
        String host = "localhost";//change for different server
        int port = 6061;//port for devices to connect to
        StringBuffer instr = new StringBuffer();
        String Timestamp;
        System.out.println("SocketClient initialized succesfully.");
        try{
            InetAddress address = InetAddress.getByName(host);//server realizes its own address
            Socket connection = new Socket(address, port);//establishes a socket for connection on that address, on that port
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());//create output stream
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");//create write for output
            Timestamp = new Date().toString();//get timestamp of connection
            String process = "SEND: Connected to "+ host +", on port "+port+", at "+Timestamp + (char) 13;
            osw.write(process);//write to outputstreamwriter
            osw.flush();//flush anything left in the buffer
            DataOutputStream in = new DataOutputStream(connection.getOutputStream());//create stream
            osw = new OutputStreamWriter(in);//create stream reader
            osw.write(currentPlayer.getNumbersFromRoll1());//read stream
            System.out.println("player "+currentPlayer.getName()+" threw " + currentPlayer.getNumbersFromRoll1());
            //osw.close();

            //Receiving
            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());//input stream
            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");// read input stream
            int c = 0;//integer 13 in text, remember?
            while((c = isr.read()) != 13){//while we've not met the EOF
                instr.append((char) c);//append to the string

            }
            System.out.println(instr);//print out string
            connection.close();//close socket

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void receiveRollFromServer(){
        //Sending
        String host = "localhost";//change for different server
        int port = 6061;//port for devices to connect to
        StringBuffer instr = new StringBuffer();
        String Timestamp;
        System.out.println("SocketClient initialized succesfully.");
        try{
            InetAddress address = InetAddress.getByName(host);//server realizes its own address
            Socket connection = new Socket(address, port);//establishes a socket for connection on that address, on that port
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());//create output stream
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");//create write for output
            Timestamp = new Date().toString();//get timestamp of connection
            String process = "RECV: Connected to "+ host +", on port "+port+", at "+Timestamp + (char) 13;
            osw.write(process);//write to outputstreamwriter
            osw.flush();//flush anything left in the buffer


            //Receiving
            DataInputStream in = new DataInputStream(connection.getInputStream());//create stream
            InputStreamReader isr = new InputStreamReader(in);//create stream reader
            System.out.println(player2.getNumbersFromRoll1());
            if (player2.getNumbersFromRoll1() == 0) {
                System.out.println("Player 2 has yet to roll his dice..\b.");
            }

            isr.close();
            in.close();//close

            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());//input stream
            isr = new InputStreamReader(bis, "US-ASCII");// read input stream
            int c;//integer 13 in text, remember?
            while((c = isr.read()) != 13){//while we've not met the EOF
                instr.append((char) c);//append to the string

            }
            System.out.println(instr);//print out string
            connection.close();//close socket

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String args[]) {


        uuid = UUID.randomUUID().getMostSignificantBits();//generate unique id as players have not been assigned numbers yet
        reader = new Scanner(System.in);//You realize you need to have an input for your head to get info from.
        currentPlayer = new backgammonPlayer();//You realize you're a player (stop it, you :D), and as such a player has some attributes.
        enemyPlayer = new backgammonPlayer();//You realize your opponent is also a player. And that he has attributes too.
        player1 = new backgammonPlayer();//Somebody is going to be player 1.
        player2 = new backgammonPlayer();//Somebody is going to be player 2.
        board = new backgammonBoard();//there's going to be a deck between you on the table, with rocks
        dice = new backgammonDice();//and a pair of dice.

        //Now, with that in mind - you and your opponent start communicating.

        Scanner input = new Scanner(System.in);//The computer needs a way of getting your names. You then give it one.
        System.out.print("What is your name?");//It then asks for the first name.
        name1 = input.nextLine();//One of you types it in.
        player1.setName(name1);//He remembers that one of his attributes as a player is a name, and now he has one.

        setupBoard();//It sets up the board for them,
        initialGame();//and then they roll dice to decide who goes first.
        gameLoop();//After everything is done, the game begins...


    }
}





