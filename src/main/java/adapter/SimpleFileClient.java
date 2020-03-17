import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class SimpleFileClient {

  public final static int SOCKET_PORT = 16667;      // you may change this
  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String
       FILE_TO_RECEIVED = "/Users/dvc/go/src/github.com/Module-2/src/main/resources/audio_files/thomas_audio.wav";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {

    int current = 0;
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    Socket sock = null;
    try {
      sock = new Socket(SERVER, SOCKET_PORT);
      System.out.println("Connecting...");

      // receive file
      byte [] buff  = new byte [8192];
      OutputStream os = sock.getOutputStream();
      fis = new FileInputStream(FILE_TO_RECEIVED);
      bis = new BufferedInputStream(fis);

      int count;

      while((count = bis.read(buff)) > 0){
        os.write(buff, 0, count);
      }

      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fis != null) fis.close();
      if (bis != null) bis.close();
      if (sock != null) sock.close();
    }
  }

}