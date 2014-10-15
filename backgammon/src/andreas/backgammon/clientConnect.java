package andreas.backgammon;

import java.io.*;
import java.net.Socket;

/**
 * Created by Antreas Christofi on 15/10/2014.
 */
public class clientConnect {

    String serverName = args[0];
    int port = Integer.parseInt(args[1]);
    try
    {
        System.out.println("Connecting to " + serverName
                + " on port " + port);
        Socket client = new Socket(serverName, port);
        System.out.println("Just connected to "
                + client.getRemoteSocketAddress());
        OutputStream outToServer = client.getOutputStream();
        DataOutputStream out =
                new DataOutputStream(outToServer);

        out.writeUTF("Hello from "
                + client.getLocalSocketAddress());
        InputStream inFromServer = client.getInputStream();
        DataInputStream in =
                new DataInputStream(inFromServer);
        System.out.println("Server says " + in.readUTF());
        client.close();
    }catch(IOException e)
    {
        e.printStackTrace();
    }
}
