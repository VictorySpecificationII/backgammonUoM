package andreas.backgammon;

/**
 * Created by Antreas Christofi on 23/02/2015.
 */
import java.net.*;//for network operations
import java.io.*;//for I/O basics
import java.util.Date;

public class socketClient {


    public static void main(String[]args){
        //Sending
        String host = "localhost";//change for different server
        int port = 6061;//port for devices to connect to
        StringBuffer instr = new StringBuffer();
        String Timestamp;
        System.out.println("SocketClient initialized succesfully.");
        try{
            InetAddress address = InetAddress.getByName(host);//server realizes its own address
            Socket connection = new Socket(address, port);//establishes a socket for connection on that address, on that port
            BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());//create output stream
            OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");//create write for output
            Timestamp = new Date().toString();//get timestamp of connection
            String process = "Connecting on "+ host +", on port "+port+", at "+Timestamp + (char) 13;
            osw.write(process);//write to outputstreamwriter
            osw.flush();//flush anything left in the buffer

            //Receiving

            BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());//input stream
            InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");// read input stream
            int c;//integer 13 in text, remember?
            while((c = isr.read()) != 13){//while we've not met the EOF
                instr.append((char) c);//append to the string
                connection.close();//close socket

            }
            System.out.println(instr);//print out string

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
