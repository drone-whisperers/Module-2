package adapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;

public class TextOutputSocket {

    private int socket_port = 16669;
    private String server = "module3";

    public TextOutputSocket() {

    }

    public TextOutputSocket(String host, int socket_port) {
        this.server = host;
        this.socket_port = socket_port;
    }

    public void sendText(String textToSend) {
        OutputStream os = null;
        Socket sock = null;
        try {
            System.out.println("Connecting to " + server + ":" + socket_port);
            sock = new Socket(server, socket_port);

            os = sock.getOutputStream();
            os.write(textToSend.getBytes(Charset.forName("UTF-8")));
            os.flush();

            System.out.println("Successfully sent text: " + textToSend);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
                try {
                    if (os != null)
                    os.close();
                    if (sock != null)
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    

}
