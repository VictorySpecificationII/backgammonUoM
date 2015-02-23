package andreas.backgammon;

import java.net.*;
import java.io.*;
import java.util.*;

public class MultipleSocketServer implements Runnable {

    private Socket connection;//create socket for connection
    private String TimeStamp;//create timestamp for connection
    private int ID;//create ID of client for connection
    static int port = 6061;//define the port
    static int count = 0;//how many clients have connected

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
}