package andreas.backgammon;

import java.util.*;
/**
 * Created by Antreas Christofi on 05/10/2014.
 */
public class backgammonBoard {

    //A backgammon deck has the following properties
    public static Hashtable<Integer, Integer> deck; //deck, bar, side pockets go here slots 24 and 25
    public static Hashtable<Integer, String> colors;//colors for the rocks
    //Constructor
    public backgammonBoard(){
        deck = new Hashtable<Integer, Integer>(26);
        colors = new Hashtable<Integer, String>(26);
    }

    //Methods to access/modify hash tables

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
        System.out.println("Hashes 24 and 25 used for side pockets");
        System.out.println("");

        //Setting up white rocks
        deck.put(0, 2);
        colors.put(0,"w");
        deck.put(11, 5);
        colors.put(11,"w");
        deck.put(16, 3);
        colors.put(16,"w");
        deck.put(18, 5);
        colors.put(18,"w");
        deck.put(24, 0);
        colors.put(24, "w");
        //Setting up black rocks
        deck.put(23, 2);
        colors.put(23,"b");
        deck.put(12, 5);
        colors.put(12,"b");
        deck.put(7, 3);
        colors.put(7,"b");
        deck.put(5, 5);
        colors.put(5,"b");
        deck.put(25, 0);
        colors.put(25, "b");
        System.out.println("Modified Hash Tables:");
        System.out.println(deck.toString());
        System.out.println(colors.toString());
        System.out.println("");
        System.out.println("24 -> white side pocket, 25 -> black side pocket");
    }

    public void addStone(int target, String color, backgammonPlayer enemyPlayer, backgammonPlayer currentPlayer){
        //if empty vector
        if((deck.get(target) == 0)&&(colors.get(target) == "n")) {
            deck.put(target, deck.get(target) + 1);
            colors.put(target, color);
        }
        //if 1 or more rocks
        else if(((deck.get(target) == 1)||(deck.get(target) > 1))){
            deck.put(target, deck.get(target)+1);

        }
        //if 1 rock, and the color is of the enemy player
        else if((deck.get(target) == 1)&&(colors.get(target) == enemyPlayer.getPlayerColor())){
            
            enemyPlayer.setBar(enemyPlayer.bar + 1);
            deck.put(enemyPlayer.getBar(), enemyPlayer.bar + 1);
            deck.put(target, 1);
            colors.put(target, currentPlayer.getPlayerColor());
        }
    }

    public void removeStone(int target, String color ){
        //if operation performed on an empty vector
        if((deck.get(target) == 0)&&(colors.get(target) == "n")) {
           System.out.println("Not allowed, empty vector");
        }
        //if one rock
        else if((deck.get(target) > 1)){//if more than one
            deck.put(target, deck.get(target)-1);//remove one
            }
        else if(deck.get(target) == 1){
            deck.put(target, deck.get(target) -1);
            colors.put(target, "n");
        }
        else
            System.out.println("Something's wrong");

    }
    public int gameOver(){
        int white = deck.get(24);//piasto sidepocket tou asprou
        if(white == 15){//an en gemato
            System.out.println("white player won!");
        return 1;//epestrepse 1
        }
        int black = deck.get(25);
        if(black == 15){
            System.out.println("black player won!");
        return 2;//epestrepse 2
        }
       return 0;//epestrepse 0, to paixnidi paei akoma
    }
    public int thereExistRocksOnBar(String color) {
        int r;
        if (color == "w") {
            r = deck.get(24);
            if (r != 0) {//if the result is not zero then it means that there's stones in the bar
                return 1;
            }
        }
        else if (color == "b") {
            r = deck.get(25);
            if (r != 0) {//if the result is not zero then it means that there's stones in the bar
                return 2;
            }
        }
        else
            return 10;

        return 0;

    }


}


