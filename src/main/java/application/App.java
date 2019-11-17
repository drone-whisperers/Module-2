package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import adapter.STTTool;
import sphinx.Sphinx4;

public class App {

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {

       STTTool sphinx = new Sphinx4();
       File audioFile = new File(App.class.getClassLoader().getResource("voice-2.wav").toURI());
       System.out.println(sphinx.recognizeAudio(new FileInputStream(audioFile)));
       audioFile = new File(App.class.getClassLoader().getResource("test_voice.wav").toURI());
       System.out.println(sphinx.recognizeAudio(new FileInputStream(audioFile)));
       audioFile = new File(App.class.getClassLoader().getResource("sample-audio.wav").toURI());
       System.out.println(sphinx.recognizeAudio(new FileInputStream(audioFile)));

    }
}