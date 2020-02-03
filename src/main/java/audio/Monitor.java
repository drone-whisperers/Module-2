package audio;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Monitor {
    private final File directory;

    public Monitor(final String directoryName) {
        this.directory = new File(directoryName);
    }

    public Map<String, File> getDirectoryContents() {
        final HashMap<String, File> files = new HashMap<>();
        for (final File file : directory.listFiles()) {
            files.put(file.getName(),file);
        }
        return files;
    }
}