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

public class AudioInputSocket {

  private int socket_port = 16667;
  private String audio_dir = "/tmp/module2_files";
  private String counter_file = AudioInputSocket.class.getClassLoader().getResource("counter.txt").getPath();

  private ServerSocket servsock = null;
  private BufferedOutputStream bos = null;
  private FileOutputStream fos = null;
  private Socket sock = null;
  private File counterFile = new File(counter_file);
  private int audioCounter = 0;


  /**
   * Default constructor. Opens a socket to port 16667 and saves files in directory /tmp/module2_files
   */
  public AudioInputSocket(){

  }

  public AudioInputSocket(String audioFileDir, int port) {
    this.audio_dir = audioFileDir;
    this.socket_port = port;

  }

  public void init() {
    try {
      BufferedReader br = new BufferedReader(new FileReader(counterFile));
      audioCounter = Integer.parseInt(br.readLine());
      br.close();
    } catch (Exception E) {

    } finally {

    }
    try {
      servsock = new ServerSocket(socket_port);
      System.out.println("Running on port " + socket_port + ", waiting for files to process...");
    } catch (Exception e) {
      System.out.println("Server socket failed to start... maybe port " + socket_port + " is already occupied?");
      e.printStackTrace();
    }
  }

  public String getNextFile() {
    try {
      sock = servsock.accept();
      System.out.println("Accepted connection : " + sock);
      byte[] buff = new byte[16 * 1024]; // 16kb buffer array to read/write data
      InputStream is = sock.getInputStream();
      int bytesRead = 0;
      String audioFileName = audio_dir + "/audio_" + audioCounter + ".wav";
      fos = new FileOutputStream(audioFileName);
      bos = new BufferedOutputStream(fos);

      while ((bytesRead = is.read(buff)) > 0) {
        bos.write(buff, 0, bytesRead);
      }

      System.out.println("File received");

      bos.close();
      sock.close();
      return audioFileName;
    }
    catch(Exception e){
      System.out.println("Error occurred while attempting to process file.");
      e.printStackTrace();
    } 
    finally {
      try{

      audioCounter++;
      BufferedWriter bf = new BufferedWriter(new FileWriter(counter_file, false));
      bf.write(audioCounter + "");
      bf.close();
      }
      catch(Exception e){
        System.out.println("Failed to close buffers");
        e.printStackTrace();
      }
    }
    return "Socket failure";
  }

  public void close(){
    try {
      this.servsock.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
 