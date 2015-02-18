package andreas.backgammon;

/**
 * Created by Antreas Christofi on 11/02/2015.
 */
public class CLI {

    public static void draw(backgammonBoard board){
    StringBuilder sb = new StringBuilder();

    sb.append("---12---11---10---09---08---07---06---05---04---03---02---01---\n");
        sb.append("                               |                               \n");


        sb.append("   "+board.colors.get(12)+"    "+
            board.colors.get(11)+"    "+
            board.colors.get(10)+"    "+
            board.colors.get(9)+"    "+
            board.colors.get(8)+"    "+
            board.colors.get(7)+"  |  "+
            board.colors.get(6)+"    "+
            board.colors.get(5)+"    "+
            board.colors.get(4)+"    "+
            board.colors.get(3)+"    "+
            board.colors.get(2)+"    "+
            board.colors.get(1)+"    \n");

        sb.append("   "+board.deck.get(12)+"    "+
                board.deck.get(11)+"    "+
                board.deck.get(10)+"    "+
                board.deck.get(9)+"    "+
                board.deck.get(8)+"    "+
                board.deck.get(7)+"  |  "+
                board.deck.get(6)+"    "+
                board.deck.get(5)+"    "+
                board.deck.get(4)+"    "+
                board.deck.get(3)+"    "+
                board.deck.get(2)+"    "+
                board.deck.get(1)+"    \n");

        sb.append("                               |                               \n");
        sb.append("                               |                               \n");
        sb.append("                               |                               \n");

        sb.append("   "+board.deck.get(13)+"    "+
                board.deck.get(14)+"    "+
                board.deck.get(15)+"    "+
                board.deck.get(16)+"    "+
                board.deck.get(17)+"    "+
                board.deck.get(18)+"  |  "+
                board.deck.get(19)+"    "+
                board.deck.get(20)+"    "+
                board.deck.get(21)+"    "+
                board.deck.get(22)+"    "+
                board.deck.get(23)+"    "+
                board.deck.get(24)+"\n");

        sb.append("   "+board.colors.get(13)+"    "+
                board.colors.get(14)+"    "+
                board.colors.get(15)+"    "+
                board.colors.get(16)+"    "+
                board.colors.get(17)+"    "+
                board.colors.get(18)+"  |  "+
                board.colors.get(19)+"    "+
                board.colors.get(20)+"    "+
                board.colors.get(21)+"    "+
                board.colors.get(22)+"    "+
                board.colors.get(23)+"    "+
                board.colors.get(24)+"    \n");


        sb.append("                               |                               \n");
        sb.append("---13---14---15---16---17---18---19---20---21---22---23---24---\n");
    System.out.println(sb);
    }

}
