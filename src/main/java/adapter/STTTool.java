package adapter;

/**
 * Interface for implementing a Speech-To-Text tool
 */
public interface STTTool {
    
    // TODO: Add methods to transcript text given an audio file

    /**
     * Transcript text from audio file
     */
    public String voiceToText(File file);

    /**
     * Transcript audio file stream into text
     */
    public String voiceToText(FileInputStream fileStream);

}