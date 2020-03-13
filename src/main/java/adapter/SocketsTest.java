package adapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SocketsTest {

    public final static int SOCKET_PORT = 16667; // you may change this
    public final static String SERVER = "127.0.0.1"; // localhost
    public final static String FILE_TO_SEND = "/mnt/c/audio/thomas_audio.wav"; //


    public static void main(String[] args) throws IOException {
        
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        Socket sock = null;

        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            System.out.println("Connecting...");

            File myFile = new File(FILE_TO_SEND);
            byte[] buff = new byte[16 * 1024]; // 16kbit buffer

            fis = new FileInputStream(myFile);
            bis = new BufferedInputStream(fis);

            int bytesRead;

            os = sock.getOutputStream();

            while ((bytesRead = bis.read(buff)) > 0) {
                os.write(buff, 0, bytesRead);
            }

            os.flush();
            System.out.println("Done.");

        } finally {
            if (fis != null)
                fis.close();
            if (bis != null)
                bis.close();
            if (sock != null)
                sock.close();
        }
    }

}
