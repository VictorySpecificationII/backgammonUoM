package andreas.backgammon;

/**
 * Created by Antreas Christofi on 22/10/2014.
 */
public class graveyard {/*
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
    */
}


/*    public static boolean isCurrentRockAllowed(backgammonPlayer currentPlayer, int currentRock){
        String playerC = currentPlayer.getPlayerColor();//return current player's color
        currentRock = board.deck.get(currentRock);//get number of rocks
        String desiredRockColor = board.colors.get(currentRock);//get color

        if((currentRock >0)&&(playerC == desiredRockColor)){//if there's more than one stones and the colors permit moving
                System.out.println("Ok, rock allowed to move");
                return true;
        }
        System.out.println("Rock not allowed to move");
        return false;
    }*/