package adapter;

import java.io.FileInputStream;

/**
 * Interface for implementing a Speech-To-Text tool
 */
public interface STTTool {
    
    // TODO: Add methods to transcript text given an audio file 

    /**
     * Transcribes audio file to text and returns it as a string
     * @param path path to audio file to transcribe
     * @return String recognition of audio
     */
    public String recognizeAudio(String path);

    /**
     * @return returns processing time of last audio transcripted
     */
    public String lastProcessDuration();

    /***
     * returns last recognized audio duration
     * @return last audio duration
     */
    public String lastAudioDuration();
}