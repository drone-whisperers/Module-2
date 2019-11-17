package sphinx;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.STTTool;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class Sphinx4 implements STTTool {

    private Configuration configuration;
    private StreamSpeechRecognizer recognizer;

    /**
     * Default constructor. This uses default models for voice recognition
     */
    public Sphinx4() {
        this("resource:/edu/cmu/sphinx/models/en-us/en-us", "resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict",
                "resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
    }

    /**
     * Constructor for a Sphinx voice recognizer
     * @param acousticModelPath  Path to acoustic model
     * @param dictionaryPath  Path to dictionary model
     * @param languageModelPath  Path to language model
     */
    public Sphinx4(String acousticModelPath, String dictionaryPath, String languageModelPath) {
        this.configuration = new Configuration();
        configuration.setAcousticModelPath(acousticModelPath);
        configuration.setDictionaryPath(dictionaryPath);
        configuration.setLanguageModelPath(languageModelPath);
        try {
            recognizer = new StreamSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Transcripts an audio file to text and returns it as string
     * Only accepts audio of the format:
     * RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 16000 Hz
     * or
     * RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 8000 Hz
     */
    @Override
    public String recognizeAudio(FileInputStream fileStream){
        recognizer.startRecognition(fileStream);
        SpeechResult res;
        String result = "";
        while( (res = recognizer.getResult())!= null){
            result += res.getHypothesis()+" ";
        };
        recognizer.stopRecognition();
        return result.trim();
    }
}