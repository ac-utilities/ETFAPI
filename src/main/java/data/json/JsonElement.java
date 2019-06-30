package data.json;

public class JsonElement {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }
    public String getValue() {
        return value;
    }

    public JsonElement(String key, String value){
        this.key = key;
        this.value = value;
    }


    public String toJson() {
        return "\""+ key + "\" : \"" + value +"\"";
    }

    @Override
    public String toString() {
        return "Detail{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
