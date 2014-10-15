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



    public static int makeMoves(int noMoves, int diceOneResult, int diceTwoResult){

    return 0;
    }

    public static void main(String args[]) {
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
            //send number 2 and to other client
        }
        if(player.numbersFromRoll1 < player.numbersFromRoll2){//an to zari s en pio mitsi p t antipalou s paeis defteros
            player.setPlayerNumber(2);//to noumero s en 2
            player.yourTurn = 0;//ennen i seira s kame pisw
            player.setPlayerColor("b");//to xroma s en mavro men me rotiseis giati
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

            makeMoves(noMoves, player.numbersFromRoll1, player.numbersFromRoll2);//kamneis kiniseis
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


}



