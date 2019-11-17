package adapter;

import java.io.FileInputStream;

/**
 * Interface for implementing a Speech-To-Text tool
 */
public interface STTTool {
    
    // TODO: Add methods to transcript text given an audio file 

    /**
     * Transcribes audio file to text and returns it as a string
     * @param fileStream Audio file to transcribe
     * @return String recognition of audio
     */
    public String recognizeAudio(FileInputStream fileStream);
}