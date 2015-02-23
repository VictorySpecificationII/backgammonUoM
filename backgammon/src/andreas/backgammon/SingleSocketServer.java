package andreas.backgammon;

/**
 * Created by Antreas Christofi on 23/02/2015.
 */
import java.util.*;//utilities
import java.net.*;//networking layer
import java.io.*;//basics of I/O

public class SingleSocketServer {
    static ServerSocket socket1;//create server socket
    static int port = 6061;//define connection port
    static Socket connection;//create sockert for client connection
    static boolean first;//first client
    static StringBuffer process;
    static String TimeStamp;

    public static void main(String[] args){
        try{
            socket1 = new ServerSocket(port);//create serversocket
            System.out.println("Server Initialized successfully");
            int character;
            while(true){
                connection = socket1.accept();//accept connection
                BufferedInputStream is = new BufferedInputStream(connection.getInputStream());
                InputStreamReader isr = new InputStreamReader(is);//create input reader
                process = new StringBuffer();
                while((character = isr.read())!= 13){//while not EOF
                    process.append((char)character);//append character to string
                }
                System.out.println(process);//print out the string


                try {
                    Thread.sleep(10000);
                }
                catch (Exception e){}
                TimeStamp = new java.util.Date().toString();
                String returnCode = "SingleSocketServer responded at "+ TimeStamp + (char) 13;
                BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
                OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
                osw.write(returnCode);
                osw.flush();
            }
        }
        catch (IOException e) {}
        try {
            connection.close();
        }
        catch (IOException e) {}
    }
}

