package data.cache;

import data.Utility;
import data.json.JsonElement;
import helper.BaseLogger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CacheRetriever {
    static private BaseLogger logger = new BaseLogger(CacheRetriever.class);
    private String key;

    public HashMap<String, ArrayList> getPairing() {
        return pairing;
    }

    public List<JsonElement> getDetail() {
        return detail;
    }

    private HashMap<String, ArrayList> pairing;
    private List<JsonElement> detail;

    public CacheRetriever(String key) {
        this.key = key;
    }

    public boolean doesExist() {
        try {
            new FileReader(Utility.getFileName(this.key));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }


    public Boolean loadDataFromCache(){
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(Utility.getFileName(this.key));
        } catch (FileNotFoundException e) {
            return false;
        }

        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        try {
            obj = jsonParser.parse(fileReader);
            JSONObject jsonObject = (JSONObject) obj;
            this.key = (String) jsonObject.get("Item");
            getParingFromJson(jsonObject);
            getDetailsFromJson(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.put("item", "loaded");
        properties.put("filename", Utility.getFileName(this.key));

        logger.addEvent(properties);
        return true;
    }


    private void getParingFromJson(JSONObject test) {
        this.pairing = new HashMap<>();

        JSONObject parts =  (JSONObject) test.get("parts");
        Set partkeys = parts.keySet();
        for (Object part : partkeys ){
            JSONArray jsonArray = (JSONArray) parts.get(part);
            this.pairing.put((String)part ,new ArrayList());

            for(Object item : jsonArray){
                String subItme = (String)item;
                this.pairing.get(part).add(subItme);
            }
        }
    }

    private void getDetailsFromJson (JSONObject test){
        this.detail = new ArrayList<>();
        JSONObject details =  (JSONObject) test.get("details");
        Set partkeys = details.keySet();

        for (Object detail : partkeys ) {
            String str = (String) details.get(detail);
            this.detail.add(new JsonElement((String) detail, str));
        }
    }
}
