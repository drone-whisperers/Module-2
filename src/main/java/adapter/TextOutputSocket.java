package adapter;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class TextOutputSocket {

    private int socket_port = 16669;
    private String server = "127.0.0.1";
    



    public TextOutputSocket(){

    }

    public TextOutputSocket( String host, int socket_port){
        this.server = host;
        this.socket_port = socket_port;
    }

    public void sendText(String textToSend){
        try{
            System.out.println("Connecting to "+server+":"+socket_port);
            Socket sock = new Socket(server, socket_port);

            OutputStream os = sock.getOutputStream();
            os.write(textToSend.getBytes(Charset.forName("UTF-8")));
            os.flush();
            os.close();
            sock.close();
            System.out.println("Successfully sent text: "+textToSend);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    

}
