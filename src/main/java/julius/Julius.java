package julius;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import adapter.STTTool;
import shared.ProcessHelper;

public class Julius implements STTTool {

    private String juliusdir;
    private String appdir;
    private String configdir;
    private String dnndir;

    /**
     * Default constructor for running Julius in the top level "Julius" directory. Model directory must be inside this directory
     */
    public Julius (){
        this("/mnt/c/julius",                       //Julius top level directory 
                "julius/julius/julius",                    //Path to julius application
                "ENVR-v5.4.Dnn.Bin/julius.jconf",   //Configuration file path
                "ENVR-v5.4.Dnn.Bin/dnn.jconf"
                );
    }

    /**
     * Creates Julius with paths to models and directory
     * @param julius main directory of Julius
     * @param app path to julius application
     * @param config configuration file path
     */
    public Julius (String julius, String app, String config, String dnn){
         this.juliusdir = julius;
         this.appdir = julius + "/" + app;
         this.configdir = juliusdir + "/" + config;
         this.dnndir = juliusdir + "/" + dnn;
    }

    @Override
    public String recognizeAudio(String path) {
        System.out.println("Julius is currently processing audio file: "+ path);
        
        try {
            /** Create ProcessBuilder to run Julius command given parameters */
            System.out.println( this.appdir + " -C " + this.configdir+" -dnnconf "+this.dnndir);
            ProcessHelper process = new ProcessHelper(this.appdir + " -C " + this.configdir+" -dnnconf "+this.dnndir);
            /** Start inference process and go through output  */
            process.start();
            
            System.out.println(process.exitCode());
            
            
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
            ArrayList<String> lines = new ArrayList<String>();
            String line = "";
            while ((line = br.readLine()) != null) {
                
                /**
                 * Running inference. statement show right before the inference process and no extra lines are given until it is finished
                 * This will be used to parse inference text
                 */
                if (line.contains("sentence1")) {
                    String text = line.substring(line.indexOf("<s> "), line.indexOf("</s>"));
                    lines.add(text);
                }
            }
            
            if (lines.size() > 0){
                String retStr = "";

                for (String l : lines){
                    retStr += l;
                }

                return retStr;
            }
            
            int exitCode = process.exitCode();
            if(exitCode != 0) throw new Exception();

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
        return "";
    }

    /***
     * returns last recognized audio duration
     * @return last audio duration
     */
    public String lastAudioDuration(){
        return "";
    }
    
}