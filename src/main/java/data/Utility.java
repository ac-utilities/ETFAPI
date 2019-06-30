package data;

public class Utility {
    static public String getFileName(String key){
        return "Cache/".concat(key).concat(".json");
    }
}
