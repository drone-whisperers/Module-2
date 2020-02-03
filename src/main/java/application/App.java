package application;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import adapter.STTTool;
import deepspeech.DeepSpeech;


public class App {
    private final String audioDirectory = App.class.getClassLoader().getResource("/audio").getPath();
    private STTTool tool;

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
       ToolTest tt = new ToolTest(new DeepSpeech());
       System.out.println(tt.testTool());
    }
}