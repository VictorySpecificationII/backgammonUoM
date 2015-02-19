package andreas.backgammon;


import java.net.*;
import java.io.*;
import java.util.HashMap;

/**
 * Created by Antreas Christofi on 15/10/2014.
 */


    public class backgammonServer extends Thread
    {
        private ServerSocket serverSocket;
        public static HashMap<Integer, Integer> deckServerSide  = new HashMap<Integer, Integer>(26);//create deck on server;
        public static HashMap<Integer, String> colorsServerSide= new HashMap<Integer, String>(26);//create colors for deck on server
        public static HashMap<Integer, Integer> barServerSide = new HashMap<Integer, Integer>(2);//create a bar on server
        public static HashMap<Integer, String> barColorsServerSide= new HashMap<Integer, String>(2);//create colors on server
        public static int turn = 1;//turn for when its time to let the other player know it's their turn

        public backgammonServer(int port) throws IOException
        {
            serverSocket = new ServerSocket(port);//instantiate a ServerSocket object, denoting which port number communication is to occur on.
            serverSocket.setSoTimeout(99999999);//set timeout period
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
        public void run()
        {
            while(true)
            {
                try
                {

                    System.out.println("Waiting for client on port " +  serverSocket.getLocalPort() + "...");
                    Socket server = serverSocket.accept();
                    System.out.println("Just connected to " + server.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(server.getInputStream());
                    System.out.println(in.readUTF());
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    receiveBoardFromClient(server);
                    out.writeUTF("Board updated!");
                    out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + ".\n See you next round!");
                    serverSocket.close();//server will never stop by itself, only when interrupt from CLI occurs - just close connection
                }catch(SocketTimeoutException s)
                {
                    System.out.println("Socket timed out!");
                    break;
                }catch(IOException e)
                {
                    e.printStackTrace();
                    break;
                }
            }
        }
        public static void main(String [] args)
        {
            System.out.println("Fire and forget server for backgammon");
            int port = 6061;
            try
            {
                Thread t = new backgammonServer(port);
                t.start();
            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
