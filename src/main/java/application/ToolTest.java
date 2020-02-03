package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import adapter.STTTool;
import audio.Monitor;

public class ToolTest {

    private STTTool tool;
    private String directory = ToolTest.class.getClassLoader().getResource("audio_files").getPath();
    private String transcriptsDir = ToolTest.class.getClassLoader().getResource("transcripts").getPath();;
    private final String audioExt = ".wav";
    private final String textExt = ".txt";

    private Monitor monitor;

    public ToolTest(STTTool tool) {
        this.tool = tool;
        this.monitor = new Monitor(directory);
    }

    public String testTool() {
        Monitor transcriptMonitor = new Monitor(transcriptsDir);
        String result = "";
        Map<String, File> filesToProcess = monitor.getDirectoryContents();
        Map<String, File> transcripts = transcriptMonitor.getDirectoryContents();
        for (String filename : filesToProcess.keySet()) {
            filename = removeExtension(filename);
            if (filesToProcess.get(filename + audioExt) != null && transcripts.get(filename + textExt) != null) {
                String actual = audioToText(filesToProcess.get(filename + audioExt));
                String theoretical = readOne(transcripts.get(filename + textExt));
                result += "Expected text: " + theoretical + "\n";
                result += "Actual text: " + actual + "\n";
                result += "========================================================================\n";
            } else {
                System.out.println(filename + " was null");
            }
        }

        return result;
    }

    public String audioToText(File file) {
        return tool.recognizeAudio(file.getPath());

    }

    public String readOne(File file) {
        String result = "Transcript not processed correctly";
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            return line;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        return result;
    }

    public String removeExtension(String filename) {
        return filename.replaceFirst("[.][^.]+$", "");
    }

}