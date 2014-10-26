package andreas.backgammon;

import java.util.Scanner;

public class Main {

    public static void makeMoves(){

        board.removeStone(currentRock, currentPlayer.getPlayerColor());
        board.addStone(target, currentPlayer.getPlayerColor());
        System.out.println(board.deck.toString());
        System.out.println(board.colors.toString());
        currentPlayer.setMoves(currentPlayer.getMovesLeft() - 1);
        System.out.println("Moves left for "+ currentPlayer.getName() + ": "+currentPlayer.getMovesLeft());
        //if player has ran out of moves, check who goes next
        if(currentPlayer.getMovesLeft() == 0){
            if(currentPlayer.getPlayerNumber() == 1){
                System.out.println("first if");
                //set your turn of current player to 0
                currentPlayer.setYourTurn(0);
                //set your turn of opponent to 1
                enemyPlayer.setYourTurn(1);
                backgammonPlayer tempPlayer;//-------▽
                tempPlayer = currentPlayer; //------>SWAP PLAYERS
                currentPlayer = enemyPlayer;//-------△
                System.out.println("Current player: " + currentPlayer.getName());
                enemyPlayer = tempPlayer;
                System.out.println("Enemy player: " + enemyPlayer.getName());

            }
            else if(currentPlayer.getPlayerNumber() == 2){
                System.out.println("second if");
                //set your turn of current player to 0
                currentPlayer.setYourTurn(0);
                //set your turn of opponent to 1
                enemyPlayer.setYourTurn(1);
                backgammonPlayer tempPlayer;//-------▽
                tempPlayer = currentPlayer; //------>SWAP PLAYERS
                currentPlayer = enemyPlayer;//-------△
                System.out.println("Current player: " + currentPlayer.getName());
                enemyPlayer = tempPlayer;
                System.out.println("Enemy player: " + enemyPlayer.getName());
            }
        }
    }



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
    public static String name2;
    public static int currentRock;
    public static int currentRoll;
    
    public static Scanner reader;

    //--------------------------------------------------------------------

    public static void setupBoard(){
        board.setupBoard();//setup the deck
    }

    public static boolean isCurrentRockAllowed(backgammonPlayer currentPlayer, int currentRock){
        String currentPlayerColor = currentPlayer.getPlayerColor();//return current player's color
        String desiredRockColor = board.colors.get(currentRock);//get color
        System.out.println("current player color: "+currentPlayerColor);
        System.out.println("desired rock color: "+desiredRockColor);

        if(currentPlayerColor == desiredRockColor){//if there's more than one stones and the colors permit moving
            System.out.println("Ok, rock allowed to move");
            return true;
        }
        System.out.println("Rock not allowed to move");
        return false;
    }


    public static boolean isCurrentMoveLegit(backgammonPlayer currentPlayer, int currentRock, int currentRoll) {


        //if current player has rocks on his bar and is white
        if ((currentPlayer.playerColor == "w") && (board.thereExistRocksOnBar(currentPlayer.getPlayerColor())) == 1) {
            System.out.println("current player has rocks on his bar and is white");
            return false;
        }
        //if current player has rocks on his bar and is black
        else if ((currentPlayer.playerColor == "b") && (board.thereExistRocksOnBar(currentPlayer.getPlayerColor())) == 2) {
            System.out.println("current player has rocks on his bar and is black");
            return false;
        }
        //calculate the target vector if white player
        if (currentPlayer.playerColor == "w"){
            target = currentRock + currentRoll;
        }
        //calculate the target vector if black player
        if (currentPlayer.playerColor == "b"){
            target = currentRock - currentRoll;//going the other way around
        }

        int targetVector = board.deck.get(target);//get status of target vector
        String targetVectorC = board.colors.get(target);//get color of target vector

        //check if violates the boudaries
        if(target > 23 || target < 0){
            System.out.println("violates board's boundaries");
            return false;//can't go out of boundaries
        }

        if(targetVector == 0){
            System.out.println("Target vector empty, proceed");
            return true;
        }
        //target has one rock of current player's color
        else if((targetVector == 1)&&(targetVectorC.equals(currentPlayer.getPlayerColor()))){
            System.out.println("target has one rock of current player's color, proceed");
            return true;
        }
        //target has one rock of enemy player's color
        else if((targetVector == 1)&&(targetVectorC.equals(enemyPlayer.getPlayerColor()))){
            System.out.println("target has one rock of enemy's color, proceed to hit and land");
            boolean hit = true;
            return true;
        }
        //target has more than one stones of enemy color
        if((targetVector > 1)&&(targetVectorC.equals(enemyPlayer.getPlayerColor()))){
            System.out.println("More than one stones of enemy color, move not allowed");
            return false;
        }
        System.out.println("Last return in method");
        return false;//return false dy default because the criteria isn't satisfied
    }



    public static void initialGame(){
        currentPlayer = player1;//p1 is the current player
        dice.rollDice();//player 1 throws their dice
        System.out.println("Dice roll for " + player1.getName() + ":" + dice.getDiceRoll1());
        currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//player 1 has rolled number x
        currentPlayer = player2;
        dice.rollDice();//player 2 throws their dice
        System.out.println("Dice roll for " + player2.getName() + ":" + dice.getDiceRoll2());
        currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//player 2 has rolled number y

        //if numbers are the same/*
    if (player1.getNumbersFromRoll1() == player2.getNumbersFromRoll2()) {

            System.out.println("");
            System.out.println("Same dice, rerolling.");
            System.out.println("");}

            //while they are the same
            while (player1.getNumbersFromRoll1() == player2.getNumbersFromRoll2()) {//while they are the same

                dice.rollDice();//player1 throws again
                System.out.println("");
                System.out.println("Dice roll for " + player1.getName()+ ":" + dice.getDiceRoll1());
                player1.setNumbersFromRoll1(dice.getDiceRoll1());//player 1 re-rolled f


                dice.rollDice();//player 2 throws again
                System.out.println("Dice roll for " + player2.getName() + ":" + dice.getDiceRoll2());
                player2.setNumbersFromRoll2(dice.getDiceRoll2());//player 2 re-rolled g

            }

       if (player1.getNumbersFromRoll1() > player2.getNumbersFromRoll2()) {//if player's one dice has a larger number than player 2
            player1.setPlayerNumber(1);//set player1 as the first player
            player1.setYourTurn(1);//it's his turn, so set that too
            player1.setPlayerColor("w");//he's playing with white stones
            player1.setBar(24);//his bar is number 24

            player2.setPlayerNumber(2);//player 2 gets number 2
            player2.setYourTurn(0);//not his turn
            player2.setPlayerColor("b");//to xroma s en aspro men me rotiseis giati
            player2.setBar(25);

            currentPlayer = player1;//is the current player
            enemyPlayer = player2;
           System.out.println("Current player object attributes:");
           System.out.println("player name: " + currentPlayer.getName());
           System.out.println("player number: " + currentPlayer.getPlayerNumber());
           System.out.println("player color: " + currentPlayer.getPlayerColor());
           System.out.println("player roll 1: " + currentPlayer.getNumbersFromRoll1());
           System.out.println("player roll 2: " + currentPlayer.getNumbersFromRoll2());
           System.out.println("player bar number: " + currentPlayer.getBar());
           System.out.println("player moves left: " + currentPlayer.getMovesLeft());
           System.out.println("player score: " + currentPlayer.getScore());
           System.out.println("player your turn: " + currentPlayer.getYourTurn());
           System.out.println("");

           System.out.println("Enemy player object attributes:");
           System.out.println("player name: " + enemyPlayer.getName());
           System.out.println("player number: " + enemyPlayer.getPlayerNumber());
           System.out.println("player color: " + enemyPlayer.getPlayerColor());
           System.out.println("player roll 1: " + enemyPlayer.getNumbersFromRoll1());
           System.out.println("player roll 2: " + enemyPlayer.getNumbersFromRoll2());
           System.out.println("player bar: " + enemyPlayer.getBar());
           System.out.println("player moves left: " + enemyPlayer.getMovesLeft());
           System.out.println("player score: " + enemyPlayer.getScore());
           System.out.println("player your turn: " + enemyPlayer.getYourTurn());

        }


       else{
            player2.setPlayerNumber(1);//set player2 as the first player
            player2.setYourTurn(1);//it's his turn, so set that too
            player2.setPlayerColor("w");//he's playing with white stones
            player2.setBar(24);//his bar is number 24



            player1.setPlayerNumber(2);//player 1 gets number 2
            player1.setYourTurn(0);//not his turn
            player1.setPlayerColor("b");//to xroma s en aspro men me rotiseis giati
            player1.setBar(25);

            currentPlayer = player2;//is the current player
            enemyPlayer = player1;

           System.out.println("Current player object attributes:");
           System.out.println("player name: " + currentPlayer.getName());
           System.out.println("player number: " + currentPlayer.getPlayerNumber());
           System.out.println("player color: " + currentPlayer.getPlayerColor());
           System.out.println("player roll 1: " + currentPlayer.getNumbersFromRoll1());
           System.out.println("player roll 2: " + currentPlayer.getNumbersFromRoll2());
           System.out.println("player bar: " + currentPlayer.getBar());
           System.out.println("player moves left: " + currentPlayer.getMovesLeft());
           System.out.println("player score: " + currentPlayer.getScore());
           System.out.println("player your turn: " + currentPlayer.getYourTurn());
           System.out.println("");

           System.out.println("Enemy player object attributes:");
           System.out.println("player name: " + enemyPlayer.getName());
           System.out.println("player number: " + enemyPlayer.getPlayerNumber());
           System.out.println("player color: " + enemyPlayer.getPlayerColor());
           System.out.println("player roll 1: " + enemyPlayer.getNumbersFromRoll1());
           System.out.println("player roll 2: " + enemyPlayer.getNumbersFromRoll2());
           System.out.println("player bar: " + enemyPlayer.getBar());
           System.out.println("player moves left: " + enemyPlayer.getMovesLeft());
           System.out.println("player score: " + enemyPlayer.getScore());
           System.out.println("player your turn: " + enemyPlayer.getYourTurn());
        }
        player1.setNumbersFromRoll1(0);//now he has to roll to get numbers again
        player1.setNumbersFromRoll2(0);//now he has to roll to get numbers again
        player2.setNumbersFromRoll1(0);//now he has to roll to get numbers again
        player2.setNumbersFromRoll2(0);//now he has to roll to get numbers again
    }

public static void gameLoop() {

        Scanner reader = new Scanner(System.in);

        while ((board.gameOver() == 0)&&(currentPlayer.yourTurn == 1)) {
            if(currentPlayer.getMovesLeft() == 0){
                dice.rollDice();//throw dice
                System.out.println("Die 1: " + dice.getDiceRoll1()+", "+ "Die 2: "+ dice.getDiceRoll2());
                currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());
                currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());


                if (currentPlayer.numbersFromRoll1 == currentPlayer.numbersFromRoll2) {//same dice -> 4 moves
                    currentPlayer.setMoves(4);
                    currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//1st number
                    currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//second number
                    System.out.println("You get 4 moves");

                }
                else if (currentPlayer.numbersFromRoll1 != currentPlayer.numbersFromRoll2) {//different dice -> 2 moves
                    currentPlayer.setMoves(2);
                    currentPlayer.setNumbersFromRoll1(dice.getDiceRoll1());//1st number
                    currentPlayer.setNumbersFromRoll2(dice.getDiceRoll2());//second number
                    System.out.println("You get 2 moves");
                }
            }
            System.out.print(currentPlayer.getName()+", which rock would you like to move?");
            currentRock = reader.nextInt();
            boolean check = isCurrentRockAllowed(currentPlayer, currentRock);//this method only checks if the rock selected can be moved, doesn't check destination
               while (check == false) {
                    System.out.println("Invalid entry, the stone you are trying to move either isn't there or isn't yours");
                    System.out.print("which rock would you like to move?");
                    currentRock = reader.nextInt();
                    check = isCurrentRockAllowed(currentPlayer, currentRock);
                    if(check == true){
                        System.out.println("Ok, rock allowed");
                        break;
                    }
                }

            System.out.println("Roll 1: " + currentPlayer.getNumbersFromRoll1() + ", " + "Roll 2: " + currentPlayer.getNumbersFromRoll2());
            System.out.println("Which roll are you playing first?");
            currentRoll = reader.nextInt();
            if((currentRoll == currentPlayer.getNumbersFromRoll1()) || (currentRoll == currentPlayer.getNumbersFromRoll2())) {
                System.out.println("Checking if move is legal.");
            }
            else{
                System.out.println("Not allowed");
                System.out.println("Which roll are you playing first?");
                currentRoll = reader.nextInt();

            }

            //check legality of move to be performed
            check = isCurrentMoveLegit(currentPlayer, currentRock, currentRoll);
            System.out.println(check);
             if (check == true)
               makeMoves();
               System.out.println("Game status: " + board.gameOver());
            }
           }





    public static void main(String args[]) {


        reader = new Scanner(System.in);
        currentPlayer = new backgammonPlayer();
        enemyPlayer = new backgammonPlayer();
        player1 = new backgammonPlayer();//instance of player 1
        player2 = new backgammonPlayer();//instance of player 2
        board = new backgammonBoard();//instance of deck
        dice = new backgammonDice();//instance of dice 1
        dice = new backgammonDice();//instance of dice 2

        System.out.print("Hello, welcome to awe-gammon! How many games do you wish to play?");
        noGames = reader.nextInt();
        while(noGames <1) {//checking for number of games, if neg then ask again
            System.out.println("Invalid number of games, must be > 0");
            noGames = reader.nextInt();
        }

        Scanner input = new Scanner(System.in);
        System.out.print("What is your name?");
        name1 = input.nextLine();
        player1.setName(name1);


        System.out.print("And yours?");
        name2 = input.nextLine();
        player2.setName(name2);

        System.out.println("Number of games: "+noGames);
        System.out.println("Game starting...");
        System.out.println("");

        setupBoard();//setup board
        initialGame();//initial moves of the game to determine who goes first
        gameLoop();

        }




    }




