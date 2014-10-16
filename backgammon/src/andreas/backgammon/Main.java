package andreas.backgammon;

import java.util.Scanner;

public class Main {
    public static void updateBoard() {
    }

    public static void gameOverSession(){

    }







    //Accept input parameters
    public static void AcceptInputParameters(int noOfGames){
        int noGames = noOfGames;
    }



    public static int makeMoves(backgammonBoard board,backgammonPlayer player, int noMoves, int diceOneResult, int diceTwoResult){
    while(noMoves != 0){
        if(board.thereExistRocksOnBar(player.playerColor) > 0)
            getOffBar(player, board, noMoves,player.numbersFromRoll1, player.numbersFromRoll2);
            }
        return 0;
    }



    public static String enemyColor = "n";
    public static int enemyBar = 0;
    public static void getOffBar(backgammonPlayer player, backgammonBoard board, int noMoves, int diceOneResult, int diceTwoResult){


        if(player.playerColor == "w") {
            enemyColor = "b";
            enemyBar = 25;
        }


        if(player.playerColor == "b") {
            enemyColor = "w";
            enemyBar = 24;
        }
            //this if below should be the criteria in makemoves and should not be here, fix:todo
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


    public static void Game(){
        int noMoves = 0;//poses kiniseis eshei
        int resultFromOtherClient = 0;//to result pou tin deferi zarka enna filaxtei dame

        Scanner reader = new Scanner(System.in);//tzainourko instance scanner
        System.out.print("Enter the number of games you wish to play:");//grapse tou
        int noGames = reader.nextInt();//piasto noumero
        AcceptInputParameters(noGames);//dosto tou functions

        backgammonPlayer player = new backgammonPlayer();//skeftou to etsi: ksekina to paixnidi, eisai ena instance enos paixti
        backgammonBoard board = new backgammonBoard();//annoieis to deck
        board.setupBoard();//stineis to deck
        backgammonDice dice1 = new backgammonDice();//estises to deck, tora fkalleis to proto zari
        backgammonDice dice2 = new backgammonDice();//tora fkalleis to deftero zari

        dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
        player.setNumbersFromRoll1(dice1.getDiceRoll());//o paixtis eshei touto to noumero
        //notify player and send number to be added where this comment line is
        //perimene ospou na seirei zari j na ertei poda to noumero tou
        player.setNumbersFromRoll2(resultFromOtherClient);//seirnei j o pareas s, j pianeis to noumero j sigkrineis
        while(player.numbersFromRoll1 == player.numbersFromRoll2){//sigkrineis na deis an en ta idia
            dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
            player.setNumbersFromRoll1(dice1.getDiceRoll());//ksanafilageis to noumero p eseires
            //notify player and send number to be added where this comment line is
            //notify player and get his result
            player.setNumbersFromRoll2(resultFromOtherClient);//seirnei j o pareas s, j pianeis to noumero j sigkrineis
        }

        if(player.numbersFromRoll1 > player.numbersFromRoll2){//an to zari s en pio megalo p t antipalou paeis protos
            player.setPlayerNumber(1);//to noumero s en 1
            player.yourTurn = 1;//edokes mesa
            player.setPlayerColor("w");//to xroma s en aspro men me rotiseis giati
            player.bar = 24;
            //send number 2 and to other client
        }
        if(player.numbersFromRoll1 < player.numbersFromRoll2){//an to zari s en pio mitsi p t antipalou s paeis defteros
            player.setPlayerNumber(2);//to noumero s en 2
            player.yourTurn = 0;//ennen i seira s kame pisw
            player.setPlayerColor("b");//to xroma s en mavro men me rotiseis giati
            player.bar = 25;
            //send number 1 and to other client
        }
        if(player.yourTurn == 1) {//an en i seira s
            dice1.rollDice();//sirneis zarkan
            dice2.rollDice();//sirneis zarkan
            player.setNumbersFromRoll1(dice1.getDiceRoll());//to proto noumero p esheis
            player.setNumbersFromRoll2(dice2.getDiceRoll());//to deftero noumero p esheis

            if (player.numbersFromRoll1 == player.numbersFromRoll2) {//an en ta idia, diples, 4 kiniseis
                noMoves = 4;
            }
            if (player.numbersFromRoll1 != player.numbersFromRoll2) {//an den einai ta idia, 2 kiniseis
                noMoves = 2;
            }
        }
        while((player.yourTurn == 1)&&(noMoves != 0)){ // oson en i seira s j en efaes tes kiniseis s
            //block p2 from acting

            makeMoves(board, player, noMoves, player.numbersFromRoll1, player.numbersFromRoll2);//kamneis kiniseis
            updateBoard();//kamneis update to deck opos tarasseis tes petres stin pragmatiki zoi

            if((board.gameOver() == 1)&&(player.playerColor == "w")){
                System.out.println("white player has won (no it's not racist, it's computer science)");
                gameOverSession();
            }
            else if((board.gameOver() == 2)&&(player.playerColor == "b")){
                System.out.println("black player has won (no it's not racist, it's computer science)");
                gameOverSession();
            }
            else{//to paixnidi sinexizetai
                System.out.println("Go on, you're good to go!");
            }

        }
    }

    public static void main(String args[]) {
        Game();
    }


}



