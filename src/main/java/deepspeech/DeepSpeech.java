package deepspeech;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import adapter.STTTool;
import shared.ProcessHelper;

public class DeepSpeech implements STTTool {
    /* Path to model */
    private String model;
    /* Path to language model */
    private String lmodel;
    /* Path to Trie */
    private String trie;
    /* DeepSpeech directory */
    private String dsdir;
    /* Last audio processed time */
    private String lastProcessDuration = "Process not run yet";
    /* Last audio time */
    private String lastAudioDuration = "Process not run yet";

    /** default constructor. This creates a DeepSpeech recognizer with models all in /mnt/c/DeepSpeech 
     * using 0.6.1 models
     */
    public DeepSpeech() {
        this("/mnt/c/DeepSpeech", //deepspeech dir
                "deepspeech-0.6.1-models/output_graph.pbmm", // model
                "deepspeech-0.6.1-models/lm.binary", // language model
                "deepspeech-0.6.1-models/trie"// trie
                ); 
    }

    /***
     *  Creates DeepSpeech with paths to models and directory
     * @param dsdir main directory of DeepSpeech
     * @param model relative path of model from main dir
     * @param lmodel relative path of language model from main dir
     * @param trie relative path of trie from main dir
     */
    public DeepSpeech(String dsdir, String model, String lmodel, String trie) {
        this.dsdir = dsdir;
        this.model = dsdir+"/"+model;
        this.lmodel = dsdir+"/"+lmodel;
        this.trie = dsdir+"/"+trie;

    }



    @Override
    public String recognizeAudio(String path) {
        System.out.println("DeepSpeech is currently processing audio file: "+path);
        
        try {
            /** Create ProcessBuilder to run DeepSpeech command given parameters */
            ProcessHelper process = new ProcessHelper("deepspeech --model "+model+" --lm "+lmodel+" --trie "+trie+" --audio "+path);
            /** Start inference process and go through output  */
            process.start();


            
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";

            while ((line = br.readLine()) != null) {
                
                /**
                 * Running inference. statement show right before the inference process and no extra lines are given until it is finished
                 * This will be used to parse inference text
                 */
                if (line.equals("Running inference.")) {
                    ArrayList<String> lines = new ArrayList<String>();
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                    String retStr = "";
                    /**
                     * The only two lines after Running inference. are the audio text,
                     * and "Inerence took xs for xs audio file". This standard will be used
                     * to parse the text and the procesing times
                     * 
                     */
                    for(String str : lines){
                        if(!str.startsWith("Inference")){
                            retStr += str + " ";
                        } else {
                            // messy code to parse durations
                            String[] splt = str.split(" for ");
                            String[] procTimeSplit = splt[0].split(" ");
                            lastProcessDuration = procTimeSplit[procTimeSplit.length - 1];
                            String[] audioSplit = splt[1].split(" ");
                            lastAudioDuration = audioSplit[0];
                        }
                    }
                    return retStr;
                }
            }
            
            int exitCode = process.exitCode();
            if(exitCode != 0) {
                System.out.println("Process failed with code: "+exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Audio recognition failed. Are paths correct?";
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLmodel() {
        return lmodel;
    }

    public void setLmodel(String lmodel) {
        this.lmodel = lmodel;
    }

    public String getTrie() {
        return trie;
    }

    public void setTrie(String trie) {
        this.trie = trie;
    }

    public String getDsdir() {
        return dsdir;
    }

    public void setDsdir(String dsdir) {
        this.dsdir = dsdir;
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