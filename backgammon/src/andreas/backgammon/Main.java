package andreas.backgammon;

import java.util.Scanner;

public class Main {

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
        int input = reader.nextInt();//piasto noumero
        AcceptInputParameters(input);//dosto tou functions

        backgammonPlayer newPlayer = new backgammonPlayer();//skeftou to etsi: ksekina to paixnidi, eisai ena instance enos paixti
        backgammonBoard newBoard = new backgammonBoard();//annoieis to deck
        newBoard.setupBoard();//stineis to deck
        backgammonDice dice1 = new backgammonDice();//estises to deck, tora fkalleis to proto zari
        backgammonDice dice2 = new backgammonDice();//tora fkalleis to deftero zari

        dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
        newPlayer.setNumbersFromRoll1(dice1.getDiceRoll());//o paixtis eshei touto to noumero
        //notify player and send number to be added where this comment line is
        //perimene ospou na seirei zari j na ertei poda to noumero tou
        newPlayer.setNumbersFromRoll2(resultFromOtherClient);//seirnei j o pareas s, j pianeis to noumero j sigkrineis
        while(newPlayer.numbersFromRoll1 == newPlayer.numbersFromRoll2){//sigkrineis na deis an en ta idia
            dice1.rollDice();//seirneis to proto gia na deis ti enna fkalei
            newPlayer.setNumbersFromRoll1(dice1.getDiceRoll());//ksanafilageis to noumero p eseires
            //notify player and send number to be added where this comment line is
            //notify player and get his result
            newPlayer.setNumbersFromRoll2(resultFromOtherClient);//seirnei j o pareas s, j pianeis to noumero j sigkrineis
        }

        if(newPlayer.numbersFromRoll1 > newPlayer.numbersFromRoll2){//an to zari s en pio megalo p t antipalou paeis protos
            newPlayer.setPlayerNumber(1);//to noumero s en 1
            newPlayer.setPlayerColor("w");//to xroma s en aspro men me rotiseis giati
            //send number 2 and to other client
        }
        if(newPlayer.numbersFromRoll1 < newPlayer.numbersFromRoll2){//an to zari s en pio mitsi p t antipalou s paeis defteros
            newPlayer.setPlayerNumber(2);//to noumero s en 2
            newPlayer.setPlayerColor("b");//to xroma s en mavro men me rotiseis giati
            //send number 1 and to other client
        }

        while(newPlayer.playerNumber == 1){
            //block p2 from acting
            dice1.rollDice();//sirneis zarkan
            dice2.rollDice();//sirneis zarkan
            newPlayer.setNumbersFromRoll1(dice1.getDiceRoll());//to proto noumero p esheis
            newPlayer.setNumbersFromRoll2(dice2.getDiceRoll());//to deftero noumero p esheis

            if(newPlayer.numbersFromRoll1 == newPlayer.numbersFromRoll2){//an en ta idia, diples, 4 kiniseis
                noMoves = 4;
            }
            if(newPlayer.numbersFromRoll1 != newPlayer.numbersFromRoll2){//an den einai ta idia, 2 kiniseis
                noMoves = 2;
            }
            makeMoves(noMoves, newPlayer.numbersFromRoll1, newPlayer.numbersFromRoll2);
        }

    }
}



