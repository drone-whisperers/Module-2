package julius;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import adapter.STTTool;
import shared.ProcessHelper;

public class Julius implements STTTool {

    private String juliusdir;
    private String appdir;
    private String configdir;
    private String dnndir;
    private String audiolistdir;
    private String lastProcessDuration = "Process not run yet"; //Last audio processed time
    private String lastAudioDuration = "Process not run yet"; //Last audio time */

    /**
     * Default constructor for running Julius in the top level "Julius" directory. Model directory must be inside this directory
     */
    public Julius (){
        this("/mnt/c/julius",                       //Julius top level directory 
                "julius/julius",                    //Path to julius application
                "ENVR-v5.4.Dnn.Bin/julius.jconf",   //Configuration file path
                "ENVR-v5.4.Dnn.Bin/dnn.jconf",
                "ENVR-v5.4.Dnn.Bin/audio_list.dbl"
                );
    }

    /**
     * Creates Julius with paths to models and directory
     * @param julius main directory of Julius
     * @param app path to julius application
     * @param config configuration file path
     */
    public Julius (String julius, String app, String config, String dnn, String audiolist){
         this.juliusdir = julius;
         this.appdir = julius + "/" + app;
         this.configdir = juliusdir + "/" + config;
         this.dnndir = juliusdir + "/" + dnn;
         this.audiolistdir = juliusdir + "/" + audiolist;
    }

    @Override
    public String recognizeAudio(String path) {
        System.out.println("Julius is currently processing audio file: "+ path);

        try {
            File file = new File(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long audioFileLength = file.length();
            int frameSize = format.getFrameSize();
            float frameRate = format.getFrameRate();
            float durationInSeconds = (audioFileLength / (frameSize * frameRate));
            this.lastAudioDuration = String.valueOf(durationInSeconds) + " sec";
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileWriter myWriter = new FileWriter(audiolistdir);
            myWriter.write(path);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Audio list file could not be modified. Unable to convert this file to text.");
            e.printStackTrace();
        }
        
        try {
            /** Create ProcessBuilder to run Julius command given parameters */
            ProcessHelper process = new ProcessHelper(this.appdir + " -C " + this.configdir+" -dnnconf "+this.dnndir);
            /** Start inference process and go through output  */
            process.start();
            
            System.out.println(process.exitCode());
            
            
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            ArrayList<String> lines = new ArrayList<String>();
            ArrayList<String> timeLines = new ArrayList<String>();
            String line = "";
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                /**
                 * Running inference. statement show right before the inference process and no extra lines are given until it is finished
                 * This will be used to parse inference text
                 */
                if (line.contains("sentence1")) {
                    String text = line.substring(line.indexOf("<s> ") + 4, line.indexOf("</s>"));
                    lines.add(text);
                }

                if (line.contains("sec.)")) {
                    String timeText = line.substring(line.indexOf("(") + 1, line.indexOf("sec.)"));
                    timeLines.add(timeText);
                }
            }
            
            int exitCode = process.exitCode();
            if(exitCode != 0) throw new Exception();

            if (lines.size() > 0){
                Float processDuration = Float.intBitsToFloat(0);
                for (String l : timeLines){
                    processDuration += Float.parseFloat(l);
                }
                this.lastProcessDuration = processDuration.toString() + " sec.";

                String retStr = "";

                for (String l : lines){
                    retStr += l;
                }

                return retStr;
            }

        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
        return "Audio recognition failed. Are paths correct?";
    }

    /***
     * returns last recognized audio processing time
     * @return last processing duration
     */
    public String lastProcessDuration(){
        return lastProcessDuration;
    }

    /***
     * returns last recognized audio duration
     * @return last audio duration
     */
    public String lastAudioDuration(){
        return lastAudioDuration;
    }
    
}