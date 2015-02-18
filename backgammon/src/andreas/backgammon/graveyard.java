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


/* *

    * */


//====================================================================================================================

/*
        int i;
        int j;
        String color;
        int howMany;

        for(i = 0; i>12; i++){
            color = board.colors.get(i);
            howMany = board.deck.get(i);
            System.out.println(color);
            System.out.println(howMany);
            if(howMany == 1){
                if (color.equals("w")) {
                    sb.append("---x---\n");
                }
                else if (color.equals("b")) {
                    sb.append("   o   \n");
                }
                else if (color.equals("n")) {
                    sb.append("       \n");
                }
                else
                    System.out.println("Soemthing's off at CLI1");
            }
            else if(howMany > 1){
                if (color.equals("w")) {
                    for(int temp = 0; temp < howMany; temp++)
                        sb.append("   x   \n");
                }
                else if (color.equals("b")) {
                    for(int temp = 0; temp < howMany; temp++)
                        sb.append("   o   \n");
                }
                else if (color.equals("n")) {
                    sb.append("       \n");
                }
                else
                    System.out.println("Soemthing's off at CLI2");
            }
        }

        for(i = 13; i>23; i++){
            color = board.colors.get(i);
            howMany = board.deck.get(i);
            if(howMany == 1){
                if (color.equals("w")) {
                    sb.append("   x   \n");
                }
                else if (color.equals("b")) {
                    sb.append("   o   \n");
                }
                else if (color.equals("n")) {
                    sb.append("       \n");
                }
                else
                    System.out.println("Soemthing's off at CLI3");
            }
            else if(howMany > 1){
                if (color.equals("w")) {
                    for(int temp = 0; temp < howMany; temp++)
                        sb.append("   x   \n");
                }
                else if (color.equals("b")) {
                    for(int temp = 0; temp < howMany; temp++)
                        sb.append("   o   \n");
                }
                else if (color.equals("n")) {
                    sb.append("       \n");
                }
                else
                    System.out.println("Soemthing's off at CLI4");
            }
        }
*
* */