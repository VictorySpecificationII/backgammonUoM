package andreas.backgammon;

import java.net.*;
import java.io.*;
import java.util.*;

public class MultipleSocketServer implements Runnable {
/**
 *
 *
 * what the server wants
 * -turn
 * -board
 * -dice
 */

    private Socket connection;//create socket for connection
    private String TimeStamp;//create timestamp for connection
    private int ID;//create ID of client for connection
    static int port = 6061;//define the port
    static int count = 0;//how many clients have connected
    public static HashMap<Integer, Integer> deckServerSide  = new HashMap<Integer, Integer>(26);//create deck on server;
    public static HashMap<Integer, String> colorsServerSide= new HashMap<Integer, String>(26);//create colors for deck on server
    public static HashMap<Integer, Integer> barServerSide = new HashMap<Integer, Integer>(2);//create a bar on server
    public static HashMap<Integer, String> barColorsServerSide= new HashMap<Integer, String>(2);//create colors on server
    public static int turn = 1;//turn for when its time to let the other player know it's their turn


    public static void main(String[] args) {

        try{
            ServerSocket socket1 = new ServerSocket(port);//create a new serversocket
            System.out.println("MultipleSocketServer Initialized");
            while (true) {//runs forever
                Socket connection = socket1.accept();//accept the connection
                Runnable runnable = new MultipleSocketServer(connection, count++);//create a runnable and up the connection count
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
                connection.close();
            }
            catch (IOException e){}
        }
    }

    public static void receiveRollC1(Socket server) throws IOException {
        DataInputStream in = new DataInputStream(server.getInputStream());
        int rollC1 = in.readInt();
    }

    public static void receiveRollC2(Socket server) throws IOException{
        DataInputStream in = new DataInputStream(server.getInputStream());
        int rollC2 = in.readInt();

    }

    private static void sendBoardToClient(){
        //todo: code sending objects to client
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

            DataInputStream primitiveTypes = new DataInputStream(InputStream);//create data stream for primitive types
            turn = primitiveTypes.readInt();//read turn
            primitiveTypes.close();//close input stream

        }
        catch(IOException e){
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }










}