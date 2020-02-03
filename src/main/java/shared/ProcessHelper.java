package shared;

import java.io.IOException;
import java.io.InputStream;

public class ProcessHelper {
    private ProcessBuilder process;
    private Process p;
    private String command;

    public ProcessHelper(String command) {
        this.command = command;
    }

    public void start() {
        process = new ProcessBuilder(command.split(" ")).redirectErrorStream(true);
        /** Start inference process and go through output */
        try {
            p = process.start();
        } catch (IOException e) {
            System.out.println("Process start for command: " + command + " failed with exception: ");
            e.printStackTrace();
        }

    }

    public InputStream getInputStream(){
        return p.getInputStream();
    }

    public int exitCode() {
        int exitCode = -1;
        try {
            exitCode = p.waitFor();
        } catch (InterruptedException e) {
            System.out.println("Process with command: "+command+" was interrupted. ");
            e.printStackTrace();
        }
        if (exitCode != 0) {
            System.out.println("Process failed with code: " + exitCode);
            printErrorStream();
        }
        return exitCode;
    }

    public void printErrorStream(){
       Helper.printInputStream(p.getErrorStream());
    }
}