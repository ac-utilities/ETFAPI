package data.cache;

import data.Utility;
import data.json.JsonElement;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SetCacheValues {
    private String key;

    private HashMap<String, ArrayList> pairing;
    private List<JsonElement> detail;

    public SetCacheValues(String key, HashMap<String, ArrayList> pairing,  List<JsonElement> detail ) {
        this.key = key;
        this.pairing = pairing;
        this.detail = detail;
    }

    public void cache() {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(Utility.getFileName(this.key));
            fileOutputStream.write(stringJson().getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String stringJson() {
        String quote = "\"";
        StringBuilder print = new StringBuilder();
        print.append("{")
                .append(quote).append("Item").append(quote)
                .append(":")
                .append(quote).append(this.key).append(quote).append(",")

                .append(quote).append("parts").append(quote)
                .append(":")
                .append(stringPartsHashJson()).append(",")

                .append(quote).append("details").append(quote)
                .append(":")
                .append(stringDetailsToHashJson())
                .append("}");

        return print.toString();
    }

    private String stringPartsHashJson() {
        String quote = "\"";
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> strings = pairing.keySet();

        for (String string : strings) {
            stringBuilder.append(quote).append(string).append(quote)
                    .append(" : [");
            for (Object parts : pairing.get(string)){
                stringBuilder.append(quote).append(parts).append(quote).append(",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("],");
        }
        if(stringBuilder.length() != 0) {
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
        }
        return "{".concat(stringBuilder.toString()).concat("}");
    }

    private String stringDetailsToHashJson() {
        String quote = "\"";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" {");
        for (JsonElement parts : detail){
            stringBuilder.append(parts.toJson()).append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
