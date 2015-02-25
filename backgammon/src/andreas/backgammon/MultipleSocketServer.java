package andreas.backgammon;

import java.net.*;
import java.io.*;
import java.nio.CharBuffer;
import java.util.*;

public class MultipleSocketServer implements Runnable {
/**
 *
 *
 * what the server wants
 * -turn
 * -board
 * -dice:
 *       client 1 has 2 players, p1 and p2. Since I can't change the architecture of the game now, I do it this way:
 *       client rolls dice. saves it in player1.
 *       client 2 does the same.
 *       rolls are sent to the server.
 *       each one has the second value now.
 *       they compare and set player numbers.
 */

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
    public static int turn = 1;//turn for when its time to let the other player know it's their turn


    public static void main(String[] args) {

        try{
            ServerSocket socket1 = new ServerSocket(port);//create a new serversocket
            System.out.println("MultipleSocketServer Initialized");
            while (true) {//runs forever
                Socket connection = socket1.accept();//accept the connection
                Runnable runnable = new MultipleSocketServer(connection, count++);//create a runnable and up the connection count
                connectionsMap.put(count, connection.getRemoteSocketAddress());
                Thread thread = new Thread(runnable);//create a thread for it
                thread.start();//and start it
            }
        }
        catch (Exception e) {}
    }
    //object for creating runnables
    MultipleSocketServer(Socket s, int i) {
        this.connection = s;
        this.ID = i;
    }

    public void run() {
        try {
            BufferedInputStream is = new BufferedInputStream(connection.getInputStream());//create input stream
            InputStreamReader isr = new InputStreamReader(is);//create a reader
            int character;//variable for characters
            StringBuffer process = new StringBuffer();//create a string buffer for printing
            while((character = isr.read()) != 13) {//while !EOF
                process.append((char)character);//add to the string
            }
            System.out.println(process);//print it out at the end

            try {
                Thread.sleep(10000);
                receiveRoll(connection);
                System.out.println("Count so far is "+ count);
                //todo:check logic again
                //if(count == 2){
                //    sendRollC1(connection);
                //    sendRollC2(connection);
               // }
                //else{
                //    System.out.println("Client 2 has yet to send his roll in");
               // }


            }
            catch (Exception e){}
            TimeStamp = new java.util.Date().toString();//create a timestamp for the connection
            String returnCode = "MultipleSocketServer responded at "+ TimeStamp + (char) 13;//the code to be sent back to client
            BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());//create output stream
            OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");//create a writer for the stream
            osw.write(returnCode);//write the return code and send it
            osw.flush();//and flush the buffer
        }
        catch (Exception e) {
            System.out.println(e);
        }
        finally {
            try {
                System.out.println("Task completed. Exiting...");
                connection.close();
            }
            catch (IOException e){}
        }
    }

    public static void receiveRoll(Socket server) throws IOException {
        if(count == 1) {//one client connected, one connection made
            DataInputStream in = new DataInputStream(server.getInputStream());//create stream
            InputStreamReader isr = new InputStreamReader(in);//create stream reader
            dice1FromClient = isr.read();//read stream
            in.close();//close
            System.out.println("value received:" + dice1FromClient);
            System.out.println(connectionsMap.get(count));//print out log
        }//This works assuming one client connects and then the other connects. Now, for 2 same connections, it wont.
        if (count == 2){//the second client has connected as well
            DataInputStream in = new DataInputStream(server.getInputStream());//create stream
            InputStreamReader isr = new InputStreamReader(in);//create stream reader
            dice2FromClient = isr.read();//read stream
            in.close();//close
        }
    }

    public static void sendRollC1(Socket server) throws IOException{
        //send roll from client 2 to client 1
        //grab clients IP from log, establish connection, send stuff
        DataOutputStream in = new DataOutputStream(server.getOutputStream());//create stream
        OutputStreamWriter osw = new OutputStreamWriter(in);//create stream reader
        osw.write(dice1FromClient);//read stream
        in.close();//close
    }

    public static void sendRollC2(Socket socket) throws IOException{
        //send roll from client 1 to client 2
        //grab clients IP from log, establish connection, send stuff
        DataOutputStream in = new DataOutputStream(socket.getOutputStream());//create stream
        OutputStreamWriter osw = new OutputStreamWriter(in);//create stream reader
        osw.write(dice2FromClient);//read stream
        in.close();//close
    }


    public static void receiveTurn(Socket socket) throws IOException {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());//create stream
            InputStreamReader isr = new InputStreamReader(in);//create stream reader
            turn = isr.read();//read stream
            isr.close();//close
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTurn(Socket socket){
        try{
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            OutputStreamWriter osw = new OutputStreamWriter(out);
            osw.write(turn);
            osw.close();
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
        }
        catch(IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}