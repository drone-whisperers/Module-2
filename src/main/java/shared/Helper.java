package shared;

public class Helper{

    public static String getLinuxPath(String path){
        if(path.startsWith("/mnt/c/")) return path;
        if(path.startsWith("C:\\")) {
            path = path.replaceAll("\\\\", "/");
            return path.replaceAll("C:/", "/mnt/c/");
        }
        return "miscalc";
    }
}