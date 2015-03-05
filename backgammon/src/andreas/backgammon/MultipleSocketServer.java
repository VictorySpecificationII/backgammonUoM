package andreas.backgammon;

import java.net.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class MultipleSocketServer implements Runnable {


    private Socket connection;//create socket for connection
    private String TimeStamp;//create timestamp for connection
    private int ID;//create ID of client for connection
    static int port = 6061;//define the port
    static int count = 0;//how many clients have connected
    public static int dice1FromClient = 0;
    public static int dice2FromClient = 0;
    public static HashMap<Integer, Integer> deckServerSide  = new HashMap<Integer, Integer>(26);//create deck on server;
    public static HashMap<Integer, String> colorsServerSide= new HashMap<Integer, String>(26);//create colors for deck on server
    public static HashMap<Integer, Integer> barServerSide = new HashMap<Integer, Integer>(2);//create a bar on server
    public static HashMap<Integer, String> barColorsServerSide= new HashMap<Integer, String>(2);//create colors on server
    public static HashMap<Integer, SocketAddress> connectionsMap = new HashMap(2);//map to keep track of connections
    public static int turn = 0;//turn for when its time to let the other player know it's their turn
    public static int dice1 = 0;
    public static int dice2 = 0;
    public static boolean firstConnection = true;//after first client connects, reverse the numbers
    public static boolean doAgain = true;//for sendRoll
    public static int receiveTurnVariable = 0;
    public static int sendTurnVariable = 0;
    public static int receiveTablesvariable = 0;
    public static int sendTablesVariable = 0;


    //object for creating runnables
    MultipleSocketServer(Socket s, int i) {
        this.connection = s;
        this.ID = i;

    }

    public void run() {
        try {
           //   if(doAgain) {
                  sendRoll(connection);
           //   }

                  receiveTurn(connection);

              //    sendTurn(connection);

              //    sendBoardToClient(connection);

              //    receiveBoardFromClient(connection);


            TimeStamp = new java.util.Date().toString();
            System.out.println("Server responded at " + TimeStamp);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                System.out.println("Closing connection...");
                connection.close();
            }
            catch (IOException e){}
        }
    }

    private static void calculateDice(){
        backgammonDice serverDice = new backgammonDice();
        while(dice1 == dice2) {
            serverDice.rollDice();
            dice1 = serverDice.getDiceRoll1();
            dice2 = serverDice.getDiceRoll2();
            System.out.println("Dice 1: " + dice1);
            System.out.println("Dice 2: " + dice2);
        }
    }

    private static void sendRoll(Socket socket) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        OutputStreamWriter osw = new OutputStreamWriter(out);
        if (firstConnection == true){
            osw.write(dice1);
            System.out.println("Value 1 sent");
            osw.flush();
            System.out.println("Value 2 sent");
            osw.write(dice2);
            osw.flush();
            firstConnection = false;

        }
        else if(firstConnection == false){
            osw.write(dice2);
            System.out.println("Value 2 sent");
            osw.flush();
            System.out.println("Value 1 sent");
            osw.write(dice1);
            osw.flush();
            System.out.println("Else clause in sendRoll executed");
            doAgain = false;//so it won't send anything again everytime the thread is ran
            System.out.println("doAgain turned to false");
        }
        else
            System.out.println("None executed in sendRoll");

     }


    //receiveTurn WORKS
    public static void receiveTurn(Socket socket) throws IOException {
        InputStream in = new DataInputStream(socket.getInputStream());//create stream
        InputStreamReader isr = new InputStreamReader(in);//create stream reader

        try {
            Thread.sleep(15000);//to avoid java.lang.OutOfMemoryError
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            receiveTurnVariable = isr.read();//enable receiveTurn variable
            if(receiveTurnVariable == 1) {
                turn = isr.read();//updateTurn
                System.out.println("Turn received");
                System.out.println(turn);
            }
            else{
                System.out.println("receiveTurn not activated.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTurn(Socket socket){
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(out);
            osw.write(turn);
            osw.flush();
            sendTurnVariable = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendBoardToClient(Socket socket){
        try {
            //todo: verify function
            OutputStream OutputStream = socket.getOutputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            ObjectOutputStream mapOutputStream = new ObjectOutputStream(OutputStream);//define input stream for incoming map
            mapOutputStream.writeObject(deckServerSide);//read deck into map
            mapOutputStream.close();//close input stream

            OutputStream = socket.getOutputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapOutputStream = new ObjectOutputStream(OutputStream);//define input stream for incoming map
            mapOutputStream.writeObject(colorsServerSide);//read deck into map
            mapOutputStream.close();//close input stream

            OutputStream = socket.getOutputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapOutputStream = new ObjectOutputStream(OutputStream);//define input stream for incoming map
            mapOutputStream.writeObject(barServerSide);//read deck into map
            mapOutputStream.close();//close input stream


            OutputStream = socket.getOutputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapOutputStream = new ObjectOutputStream(OutputStream);//define input stream for incoming map
            mapOutputStream.writeObject(barColorsServerSide);//read deck into map
            mapOutputStream.close();//close input stream

            DataOutputStream primitiveTypes = new DataOutputStream(OutputStream);//create data stream for primitive types
            OutputStreamWriter osw = new OutputStreamWriter(primitiveTypes);
            osw.write(turn);
            primitiveTypes.close();//close input stream

            sendTablesVariable = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveBoardFromClient(Socket socket){
        try {
            //todo: verify function
            InputStream InputStream = socket.getInputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            ObjectInputStream mapInputStream = new ObjectInputStream(InputStream);//define input stream for incoming map
            deckServerSide =(HashMap<Integer, Integer>)mapInputStream.readObject();//read deck into map
            mapInputStream.close();//close input stream

            InputStream = socket.getInputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapInputStream = new ObjectInputStream(InputStream);//define input stream for incoming map
            colorsServerSide = (HashMap<Integer, String>)mapInputStream.readObject();//read colors into map
            mapInputStream.close();//close input stream

            InputStream = socket.getInputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapInputStream = new ObjectInputStream(InputStream);//define input stream for incoming map
            barServerSide = (HashMap<Integer, Integer>)mapInputStream.readObject();//read bar contents into map
            mapInputStream.close();//close input stream

            InputStream = socket.getInputStream();//InputStream from where to receive the map, in case of network you get it from the Socket instance.
            mapInputStream = new ObjectInputStream(InputStream);//define input stream for incoming map
            barColorsServerSide = (HashMap<Integer, String>)mapInputStream.readObject();//read bar color contents into map
            mapInputStream.close();//close input stream
            receiveTablesvariable = 0;
        }
        catch(IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        try{

            ServerSocket socket = new ServerSocket(port);//create a new serversocket
            System.out.println("MultipleSocketServer Initialized");
            calculateDice();
            while (true) {//runs forever
                Socket connection = socket.accept();//accept the connection
                Runnable runnable = new MultipleSocketServer(connection, count++);//create a runnable and up the connection count
                if(connectionsMap.containsValue(connection.getRemoteSocketAddress()))
                    continue;
                else
                    connectionsMap.put(count, connection.getRemoteSocketAddress());
                System.out.println(connectionsMap.toString());
                Thread thread = new Thread(runnable);//create a thread for it
                thread.start();//and start it
            }
        }
        catch (Exception e) {}
    }

}