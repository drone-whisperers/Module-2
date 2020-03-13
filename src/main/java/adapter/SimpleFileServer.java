package adapter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SimpleFileServer {

    public final static int SOCKET_PORT = 16667;  
    public final static String AUDIO_DIR = "/tmp/module2_files";  // you may change this
    public final static String COUNTER_FILE = SimpleFileServer.class.getClassLoader().getResource("counter.txt").getPath();
  
    public static void main (String [] args ) throws IOException {
      ServerSocket servsock = null;
      BufferedOutputStream bos = null;
      FileOutputStream fos = null;
      Socket sock = null;
      File counterFile = new File(COUNTER_FILE);
      int audioCounter = 0;
      try {
          BufferedReader br = new BufferedReader(new FileReader(counterFile));
        audioCounter = Integer.parseInt(br.readLine());
            br.close();
      }
      finally {

      }

      try {
        servsock = new ServerSocket(SOCKET_PORT);
        while (true) {
          System.out.println("Waiting...");
          try {
            sock = servsock.accept();
            System.out.println("Accepted connection : " + sock);
            byte [] buff  = new byte [16 * 1024]; //16kb buffer array to read/write data
            InputStream is = sock.getInputStream();
            int bytesRead = 0;
            fos = new FileOutputStream(AUDIO_DIR+"/audio_"+audioCounter+".wav");
            bos = new BufferedOutputStream(fos);

            while((bytesRead = is.read(buff)) > 0){
                bos.write(buff, 0, bytesRead);
            }

            System.out.println("Done.");
          }
          finally {
            if (sock!=null) sock.close();
            if (bos != null) bos.close();
            audioCounter++;
          }
        }
      }
      finally {
        if (servsock != null) servsock.close();
        BufferedWriter bf = new BufferedWriter(new FileWriter(COUNTER_FILE,false));
        bf.write(audioCounter+"");
        bf.close();
      }
    }
  }