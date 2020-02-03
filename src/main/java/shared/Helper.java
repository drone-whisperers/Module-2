package shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Helper {

    public static String getLinuxPath(String path) {
        if (path.startsWith("/mnt/c/"))
            return path;
        if (path.startsWith("C:\\")) {
            path = path.replaceAll("\\\\", "/");
            return path.replaceAll("C:/", "/mnt/c/");
        }
        return "miscalc";
    }

    public static void printInputStream(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String line = "";

        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
}
}