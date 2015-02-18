package andreas.backgammon;

import java.util.*;
/**
 * Created by Antreas Christofi on 05/10/2014.
 */
public class backgammonBoard {

    //A backgammon deck has the following properties
    public static Hashtable<Integer, Integer> deck; //deck, bar, side pockets go here slots 24 and 25
    public static Hashtable<Integer, String> colors;//colors for the rocks
    public static Hashtable<Integer, Integer> bar;
    public static Hashtable<Integer, String> barColors;
    //Constructor
    public backgammonBoard(){
        deck = new Hashtable<Integer, Integer>(26);
        colors = new Hashtable<Integer, String>(26);
        bar = new Hashtable<Integer, Integer>(2);
        barColors = new Hashtable<Integer, String>(2);


    }

    //Methods to access/modify hash tables

    //Setting up the board
    public void setupBoard(){
        for(int keyForDeckHash = 0; keyForDeckHash < 26; keyForDeckHash++){
            deck.put(keyForDeckHash, 0);
        }
        for(int keyForColorHash = 0; keyForColorHash < 26; keyForColorHash++){
            colors.put(keyForColorHash, "n");
        }
        System.out.println("Initial Hash Tables:");
        System.out.println(deck.toString());
        System.out.println(colors.toString());
        System.out.println("");
        System.out.println("");

        //Setting up white rocks
        deck.put(1, 2);
        colors.put(1,"w");
        deck.put(12, 5);
        colors.put(12,"w");
        deck.put(17, 3);
        colors.put(17,"w");
        deck.put(19, 5);
        colors.put(19,"w");
        //Setting up black rocks
        deck.put(24, 2);
        colors.put(24,"b");
        deck.put(13, 5);
        colors.put(13,"b");
        deck.put(8, 3);
        colors.put(8,"b");
        deck.put(6, 5);
        colors.put(6,"b");
        //deck.put(25, 0);
        //colors.put(25, "b");
        System.out.println("Modified Hash Tables:");
        System.out.println(deck.toString());
        System.out.println(colors.toString());
        System.out.println("");

        bar.put(0,0);
        bar.put(1,0);
        barColors.put(0, "w");
        barColors.put(1, "b");
        System.out.println(bar.toString());
        System.out.println(barColors.toString());
    }

    //adding stones to a vector
    public void addStone(int target, String color, backgammonPlayer enemyPlayer, backgammonPlayer currentPlayer){
        //If the vector is empty, and the color is set to none,
        if((deck.get(target) == 0)&&(colors.get(target) == "n")) {
            deck.put(target, deck.get(target) + 1);//add stone along
            colors.put(target, color);//with it's color.
            System.out.println("If the vector is empty, and the color is set to none,");
        }
        //OR, if there's one or more rocks of the same color
        else if((((deck.get(target) == 1)||(deck.get(target) > 1)))&&(colors.get(target) == currentPlayer.getPlayerColor())){
            deck.put(target, deck.get(target)+1);//then just add another one, no need for the color.
        System.out.println(" there's one or more rocks of the same color");
        } //OR, there's a rock that is of the enemy's color
        else if((deck.get(target) == 1)&&(colors.get(target) == enemyPlayer.getPlayerColor())){
           if(enemyPlayer.getPlayerColor() == "w"){//You hit your opponent, if he's white
               bar.put(0,(bar.get(0))+1);//up his count of white rocks on the bar
               deck.put(target, 1);//add the rock you hit him with to the vector
               colors.put(target, currentPlayer.getPlayerColor());//along with the color
               System.out.println("there's a rock that is of the enemy's color");
           }//else
           if(enemyPlayer.getPlayerColor() == "b"){//you hit your opponent, if he's black
               bar.put(1,(bar.get(0))+1);//up his count of black rocks on the bar
               deck.put(target, 1);//add the rock you hit him with on the vector
               colors.put(target, currentPlayer.getPlayerColor());//and its color
           }
        }
    }

  // public void removeStoneFromTheBar(int whichBar,int target, String color){
  //     bar.put(whichBar, bar.get(whichBar)-1);//remove a rock from the bar

  // }
   public void addStoneBackToDeck(int target, String color){
       deck.put(target, deck.get(target)+1);//put to the deck
       colors.put(target, barColors.get(target));//along with it's respective color
   }

   public void removeStone(int target, String color ){
        //If you try to remove a stone from a vector where there are no stones (I don't know why you'd do that so that's why this is here)
        if((deck.get(target) == 0)&&(colors.get(target) == "n")) {
           System.out.println("Not allowed, empty vector");
        }
        //OR, there are more than one rocks on that vector,
        else if((deck.get(target) > 1)){
            deck.put(target, deck.get(target)-1);//then you just remove one
            }
        //OR, there's only one rock on that vector in which case,
        else if(deck.get(target) == 1){
            deck.put(target, 0);//you remove it
            colors.put(target, "n");//and you set the color to none because there are no rocks there.
        }
        else//If none of the above works, something's wrong.
            System.out.println("Something's wrong");

    }//This is where you check whether the game has ended and if it has, well act accordingly.
    public int gameOver(){
        //You look at the sidepockets.

        int white = deck.get(24);//You look at the first side pocket.
        if(white == 15){//If it's full,
            System.out.println("white player won!");
        return 1;//the game is over.
        }
        //OR, the other sidepocket is full.
        int black = deck.get(25);//You look at it,
        if(black == 15){//you check whether it's fifteen rocks in there and if yes,
            System.out.println("black player won!");
        return 2;//the game is over,
        }
       return 0;//else, the game is still well on.
    }
    public int thereExistRocksOnBar(String color) {
        int r;
        if (color == "w") {
            r = bar.get(0);
            System.out.println("There exist "+ r +" white rocks on bar");
            if (r != 0) {//if the result is not zero then it means that there's stones in the bar
                return 1;
            }
        }
        if (color == "b") {
            r = bar.get(1);
            System.out.println("There exist "+ r +" black rocks on bar");
            if (r != 0) {//if the result is not zero then it means that there's stones in the bar
                return 2;
            }
        }


        return 0;

    }


}


