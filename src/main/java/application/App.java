package application;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import adapter.AudioInputSocket;
import adapter.STTTool;
import adapter.TextOutputSocket;
import deepspeech.DeepSpeech;
import julius.Julius;


public class App {
    private static int server_port = 16667;
    private static String audio_dir = "/tmp/module2_files";
    private static int m3_server_port = 16669;
    private static String m3_server_host = "module3";
    private static int m3_learning_server_port = 42069;
    private static String m3_learning_server_host = "module3learning";

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
    AudioInputSocket socket = new AudioInputSocket(audio_dir, server_port);
    socket.init();
    String filename, text;
    STTTool deepspeech = new DeepSpeech("", //deepspeech dir
    "deepspeech-0.6.1-models/output_graph.pbmm", // model
    "deepspeech-0.6.1-models/lm.binary", // language model
    "deepspeech-0.6.1-models/trie"// trie
    );

    TextOutputSocket textOut = new TextOutputSocket(m3_server_host, m3_server_port);
    TextOutputSocket textOutLearning = new TextOutputSocket(m3_learning_server_host, m3_learning_server_port);
    while(true){
        filename = socket.getNextFile();
        text = deepspeech.recognizeAudio(filename);
        System.out.println("Processed text: "+text);
        textOut.sendText(text);
        textOutLearning.sendText(text);
    }

    }
}