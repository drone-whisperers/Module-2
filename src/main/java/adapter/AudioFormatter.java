package adapter;

/**
 * An interface to use some kind of audio formatter to convert audio to certain formats
 * Some tools only accept certain formats, e.g. CMUSphinx only accepts audio files of 
 * the format RIFF (little-endian) data, WAVE audio, Microsoft PCM, 16 bit, mono 16000 Hz
 * 
 */
public interface AudioFormatter {

    /**
     * Format audio file
     */
    public File format(File inputFile);
}