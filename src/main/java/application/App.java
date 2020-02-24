package application;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import adapter.STTTool;
import deepspeech.DeepSpeech;
import julius.Julius;


public class App {
   // private final String audioDirectory = App.class.getClassLoader().getResource("/audio").getPath();
    private STTTool tool;

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        ToolTest tt = new ToolTest(new Julius());
        System.out.println(tt.testTool());
    }
}