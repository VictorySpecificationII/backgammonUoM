package andreas.backgammon;

import java.util.Scanner;

public class Main {

    public static void makeMoves(){

    //todo:check here if he's bearing off
    //todo:code add/remove stones
    //todo:finish vectors 18-23
    }



    public static String enemyColor = "n";
    public static int enemyBar = 0;
    public static int target = 0;

    public static boolean isCurrentRockAllowed(backgammonPlayer currentPlayer, backgammonBoard board, int currentRock){
        String playerC = currentPlayer.getPlayerColor();//return current player's color
        currentRock = board.deck.get(currentRock);//get number of rocks
        String desiredRockColor = board.colors.get(currentRock);//get color
        if(currentRock >0){
            if(playerC == desiredRockColor){//if the color desired exists in the vector wanted, and that vector is not empty
                return true;
            }
        }
        return false;
    }
    public static boolean isCurrentMoveLegit(backgammonPlayer currentPlayer, backgammonPlayer enemyPlayer,
                                             backgammonBoard board, int currentRock, int currentRoll) {
        //if current player has rocks on his bar and is white
        if ((currentPlayer.playerColor == "w") && (board.thereExistRocksOnBar(currentPlayer.playerColor)) == 1) {
            return false;
        }
        //if current player has rocks on his bar and is black
        if ((currentPlayer.playerColor == "b") && (board.thereExistRocksOnBar(currentPlayer.playerColor)) == 2) {
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
        //check if violates the boudaries
        if(target > 23 || target < 0){
            return false;//can't go out of boundaries
        }

        int targetVector = board.deck.get(target);
        String targetVectorC = board.colors.get(target);
        if(targetVector == 0){
            return true;
        }
        //target has one rock of current player's color
        else if((targetVector == 1)&&(targetVectorC.equals(currentPlayer.playerColor))){
            return true;
        }
        //target has one rock of enemy player's color
        else if((targetVector == 1)&&(targetVectorC.equals(enemyPlayer.playerColor))){
            return true;
        }

        if((targetVector > 1)&&(targetVectorC.equals(enemyPlayer.playerColor))){
            return false;
        }

        return false;//return false dy default because the criteria isn't satisfied
    }

    public void getOffBar(backgammonPlayer player, backgammonBoard board, int noMoves, int diceOneResult, int diceTwoResult){


        if(player.playerColor == "w") {
            enemyColor = "b";
            enemyBar = 25;
        }


        if(player.playerColor == "b") {
            enemyColor = "w";
            enemyBar = 24;
        }

            {//if the player has stones on their bar
                int noOfStones = board.deck.get(player.bar);//get the amount of rocks in the bar
                int numberOfStonesAtLandingVector = board.deck.get(diceOneResult);//get the amount of rocks at the landing vector
                if(numberOfStonesAtLandingVector == 0){
                    noOfStones--;//minus a stone cause it just landed
                    board.deck.put(player.bar, noOfStones);//minus a stone from the bar
                    board.deck.put(diceOneResult, board.deck.get(diceOneResult)+1);//add rock
                    board.colors.put(diceOneResult, player.playerColor);//add color
                    noMoves--;
                }
                 else if(numberOfStonesAtLandingVector == 1){
                    noOfStones--;//minus a stone cause it just landed
                    board.deck.put(player.bar, noOfStones);//minus a stone from the bar
                    board.deck.put(diceOneResult, board.deck.get(diceOneResult)+1);//add your rock
                    board.colors.put(diceOneResult, player.playerColor);//add your color
                    board.deck.put(enemyBar,board.deck.get(enemyBar)+1);//add enemy rock to enemy bar
                    noMoves--;
                }
                else{
                    System.out.println("Can't land");
                    noMoves--;
                }
        }
    }

    public static void initialGame(backgammonPlayer player1, backgammonPlayer player2, backgammonDice dice1, backgammonDice dice2,
                     backgammonBoard board, backgammonPlayer currentPlayer, backgammonPlayer enemyPlayer){

        board.setupBoard();//stineis to deck

        dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
        System.out.println("Dice roll for player 1: " + dice1.getDiceRoll());

        dice2.rollDice();//seirneis to deftero j 8ories to apotelesma
        System.out.println("Dice roll for player 2: " + dice2.getDiceRoll());

        player1.setNumbersFromRoll1(dice1.getDiceRoll());//o paixtis eshei touto to noumero
        player2.setNumbersFromRoll2(dice2.getDiceRoll());//seirnei j o pareas s, j pianeis to noumero j sigkrineis

        if (player1.numbersFromRoll1 == player2.numbersFromRoll2) {
            System.out.println("Same dice, rerolling.");
            while (player1.numbersFromRoll1 == player2.numbersFromRoll2) {//sigkrineis na deis an en ta idia
                //System.out.println("Dice roll for player 1");
                dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
                System.out.println("Dice roll for player 1: " + dice1.getDiceRoll());
                //System.out.println("Dice roll for player 2");
                dice2.rollDice();//seirneis to deftero j 8ories to apotelesma
                System.out.println("Dice roll for player 2: " + dice2.getDiceRoll());
                player1.setNumbersFromRoll1(dice1.getDiceRoll());//ksanafilageis to noumero p eseires
                player2.setNumbersFromRoll2(dice2.getDiceRoll());//seirnei j o pareas s, j pianeis to noumero j sigkrineis
            }
        }


        if (player1.numbersFromRoll1 > player2.numbersFromRoll2) {//an to zari s en pio megalo p t antipalou paeis protos
            player1.setPlayerNumber(1);//to noumero s en 1
            player1.yourTurn = 1;//edokes mesa
            player1.setPlayerColor("w");//to xroma s en aspro men me rotiseis giati
            player1.bar = 24;
            currentPlayer = player1;
            enemyPlayer = player2;
            System.out.println("Player 1 goes first");
        }


        if (player1.numbersFromRoll1 < player2.numbersFromRoll2) {//an to zari s en pio mitsi p t antipalou s paeis defteros
            player2.setPlayerNumber(2);//to noumero s en 2
            player2.yourTurn = 0;//ennen i seira s kame pisw
            player2.setPlayerColor("b");//to xroma s en mavro men me rotiseis giati
            player2.bar = 25;
            currentPlayer = player2;
            enemyPlayer = player1;
            System.out.println("Player 2 goes first");
        }

    }
    public static void gameLoop(int currentRock, int currentRoll, backgammonPlayer currentPlayer, backgammonPlayer enemyPlayer, backgammonPlayer player1,
                         backgammonPlayer player2, backgammonBoard board, backgammonDice dice1, backgammonDice dice2) {

        Scanner reader = new Scanner(System.in);

        while ((board.gameOver() == 0)&&(currentPlayer.yourTurn == 1)) {
            if(currentPlayer.getMovesLeft() == 0){
                dice1.rollDice();//sirneis zarkan
                dice2.rollDice();//sirneis zarkan

                if (currentPlayer.numbersFromRoll1 == currentPlayer.numbersFromRoll2) {//an en ta idia, diples, 4 kiniseis
                    currentPlayer.setMoves(4);
                    currentPlayer.setNumbersFromRoll1(dice1.getDiceRoll());//to proto noumero p esheis
                    currentPlayer.setNumbersFromRoll2(dice2.getDiceRoll());//to deftero noumero p esheis

                }
                if (currentPlayer.numbersFromRoll1 != currentPlayer.numbersFromRoll2) {//an den einai ta idia, 2 kiniseis
                    currentPlayer.setMoves(2);
                    currentPlayer.setNumbersFromRoll1(dice1.getDiceRoll());//to proto noumero p esheis
                    currentPlayer.setNumbersFromRoll2(dice2.getDiceRoll());//to deftero noumero p esheis

                }
            }
            System.out.print("which rock would you like to move?");
            currentRock = reader.nextInt();
            boolean check = isCurrentRockAllowed(currentPlayer, board, currentRock);//this method only checks if the rock selected can be moved, doesn't check destination
            if (check == false) {
                while (check == false) {
                    System.out.println("Invalid entry, the stone you are trying to move either isn't there or isn't yours");
                    System.out.print("which rock would you like to move?");
                    currentRock = reader.nextInt();
                    check = isCurrentRockAllowed(currentPlayer, board, currentRock);
                    if(check == true){
                        System.out.println("Ok, rock allowed");
                        break;
                    }
                }
            } else{
                System.out.println("Something's wrong at check rock");
                break;
            }
            //2.makes sense here too
            System.out.println("Roll 1: " + currentPlayer.numbersFromRoll1 + ", " + "Roll 2: " + currentPlayer.numbersFromRoll2);
            System.out.println("Which roll are you playing first?");
            currentRoll = reader.nextInt();
            if ((currentRoll != currentPlayer.numbersFromRoll1) || (currentRoll != currentPlayer.numbersFromRoll2)) {
                System.out.println("Not allowed");
                while((currentRoll != currentPlayer.numbersFromRoll1) || (currentRoll != currentPlayer.numbersFromRoll2)){

                }

            }
            System.out.println("Checking if move is legal.");
            isCurrentMoveLegit(currentPlayer, enemyPlayer, board, currentRock, currentRoll);//check legality of move to be performed
             if (isCurrentMoveLegit(currentPlayer, enemyPlayer, board, currentRock, currentRoll) == true) {
               makeMoves();
               System.out.println("Game status: " + board.gameOver());
              }
            }
           }







    public static void main(String args[]) {

        //Variables section
        //--------------------------------------------------------------------
        int noGames = 0;
        int currentRock = 0;
        int currentRoll = 0;
        backgammonPlayer currentPlayer;
        backgammonPlayer enemyPlayer;
        backgammonPlayer player1;
        backgammonPlayer player2;
        backgammonBoard board;
        backgammonDice dice1;
        backgammonDice dice2;
        Scanner reader;

        //--------------------------------------------------------------------
        reader = new Scanner(System.in);
        currentPlayer = new backgammonPlayer();
        enemyPlayer = new backgammonPlayer();
        player1 = new backgammonPlayer();//skeftou to etsi: ksekina to paixnidi, eisai ena instance enos paixti
        player2 = new backgammonPlayer();//skeftou to etsi: ksekina to paixnidi, eisai ena instance enos paixti
        board = new backgammonBoard();//annoieis to deck
        dice1 = new backgammonDice();//estises to deck, tora fkalleis to proto zari
        dice2 = new backgammonDice();//tora fkalleis to deftero zari

        System.out.println("Hello, welcome to awe-gammon! How many games do you wish to play?");
        noGames = reader.nextInt();
        while(noGames <0) {//checking for number of games, if neg then ask again
            System.out.println("Invalid number of games, must be > 0");
            noGames = reader.nextInt();
        }

        System.out.println("Number of games: "+noGames+".");
        System.out.println("Game starting...");

        initialGame(player1, player2, dice1, dice2, board, currentPlayer, enemyPlayer);
        //1.makes sense so far


            gameLoop(currentRock, currentRoll, currentPlayer, enemyPlayer, player1, player2, board, dice1,
                    dice2);

        }




    }




