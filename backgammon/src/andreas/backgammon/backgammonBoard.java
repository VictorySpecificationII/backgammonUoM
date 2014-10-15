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

    public int gameOver(){
        int white = deck.get(24);//piasto sidepocket tou asprou
        if(white == 15){//an en gemato
        return 1;//epestrepse 1
        }
        int black = deck.get(25);
        if(black == 15){
        return 2;//epestrepse 2
        }
       return 0;//epestrepse 0, to paixnidi paei akoma
    }
}


