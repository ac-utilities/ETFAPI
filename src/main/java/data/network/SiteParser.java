package data.network;

import data.json.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SiteParser {

    private List<String> site;
    private List<String> weaponModTables;

    public SiteParser(List<String> site) {
        this.site = site;
    }

    private List<String> getItemModTable() {
        if (weaponModTables != null)
            return this.weaponModTables;

        List<String> tables = new ArrayList<>();
        boolean write = false;
        String temp = "";
        for(String s : this.site){
            if(s.contains("div class=\"tabber") || s.contains("div id=\"tabber")) {
                temp = "";
                write = true;
            }else if (s.contains("/div")){
                write = false;
                if(temp != "")
                    tables.add(temp);
                temp = "";
            }
            if(write){
                temp = temp.concat(" " + s);
            }
        }

        this.weaponModTables = tables;
        return tables;
    }

    public HashMap<String, ArrayList> getItemHash(){
        List<String> itemTable = getItemModTable();
        HashMap<String, ArrayList> hashMap = new HashMap<String, ArrayList>();

        for (String s : itemTable) {
            String key = "";
            String[] split = s.split("\\n");
            for (String splits : split ) {
                if(splits.contains("div class=\"tabbertab\"")){
                    key = getTitle(splits);
                } else if (splits.contains("a href=\"")) {
                    String url = getUrl(splits);
                    Object o = hashMap.get(key);
                    if (o == null ) {
                        hashMap.put(key, new ArrayList<String>());
                    }
                    hashMap.get(key).add(url);
                }
            }
        }

        return hashMap;
    }

    private String getTitle(String splits) {
        int index = splits.indexOf(" title=\"");
        String url = splits.substring(index+8);
        String[] split = url.split("\" ");
        return split[0];
    }

    private String getUrl(String splits) {
        int index = splits.indexOf("a href=\"");
        String url = splits.substring(index+8);
        String[] split = url.split("\" ");
        return split[0].substring(1);
    }

    public List<JsonElement> getItemDetails(){
        List<JsonElement> details= new ArrayList<>();
        List<String> subData = GetRawDetailsList();

        for (int index = 0 ; index < subData.size() ; index++){
            if(subData.get(index).contains("div class=\"va-infobox-title-main\"")) {
                details.add(getItemTitle(index,subData ));
            }
            if(subData.get(index).contains("Sold by")){
                details.add(getSoldBy(index, subData));
            }
            if(subData.get(index).contains("Performance")){
                details.addAll(getPerformanceDetails(index, subData));
            }
        }

        return details;
    }

    private JsonElement getItemTitle(int index, List<String> subData) {
        return new JsonElement("title",subData.get(index+1));
    }

    private ArrayList<JsonElement> getPerformanceDetails(int index, List<String> subData) {
        ArrayList<JsonElement> arrayList = new ArrayList<>();

        for(int i = index ; i < subData.size() ; i++){
            String recoil = getPerformanceDetailsByName(subData, i, "Recoil");
            if(recoil != null) {
                arrayList.add(new JsonElement("Recoil",recoil));
            }

            String effective_distance = getPerformanceDetailsByName(subData, i, "Effective distance");
            if(effective_distance != null) {
                arrayList.add(new JsonElement("Effective distance",effective_distance));
            }

            String ergonomics = getPerformanceDetailsByName(subData, i, "Ergonomics");
            if(ergonomics != null) {
                arrayList.add(new JsonElement("Ergonomics",ergonomics));
            }

            String firing_modes = getPerformanceDetailsByName(subData, i, "Firing modes");
            if(firing_modes != null) {
                arrayList.add( new JsonElement("Firing modes",  firing_modes));
            }

            String rate_of_fire = getPerformanceDetailsByName(subData, i, "Rate of fire");
            if(rate_of_fire != null) {
                arrayList.add( new JsonElement("Rate of fire", rate_of_fire));
            }

            String sighting_range = getPerformanceDetailsByName(subData, i, "Sighting range");
            if(sighting_range != null) {
                arrayList.add( new JsonElement("Sighting range",sighting_range));
            }

            String accuracy = getPerformanceDetailsByName(subData, i, "Accuracy");
            if(accuracy != null) {
                arrayList.add(new JsonElement("Accuracy :", accuracy));
            }
        }

        return arrayList;
    }

    private String getPerformanceDetailsByName(List<String> subData, int i, String recoil) {
        StringBuilder stringBuilder = new StringBuilder();
        if (subData.get(i).contains(recoil)) {
            for (int y = i + 1; y < subData.size(); y++) {
                if (!subData.get(y).contains("td") && !subData.get(y).contains("br") && !subData.get(y).contains("tr") && !subData.get(y).contains("font") ) {
                    stringBuilder.append(subData.get(y)).append(",");
                }
                if (subData.get(y).contains("/tr")) {
                    y = subData.size();
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            return stringBuilder.toString();
        }
        return null;
    }

    private JsonElement getSoldBy(int index, List<String> subData) {
        String soldBy = "";

        for(int i = index ; index < subData.size() ; i++){
            soldBy = getString(subData, soldBy, i, "Prapor");
            soldBy = getString(subData, soldBy, i, "Therapist");
            soldBy = getString(subData, soldBy, i, "Skier");
            soldBy = getString(subData, soldBy, i, "Peacekeeper");
            soldBy = getString(subData, soldBy, i, "Mechanic");
            soldBy = getString(subData, soldBy, i, "Ragman");

            if(subData.get(i).contains("Performance")) {
                soldBy = soldBy.substring(0,soldBy.length()-1);
                return new JsonElement("SoldBy", soldBy);
            }
        }
        soldBy = soldBy.substring(0,soldBy.length()-1);
        return new JsonElement("SoldBy", soldBy);
    }

    private String getString(List<String> subData, String soldBy, int i, String dealer) {
        if (subData.get(i).contains(dealer) && !soldBy.contains(dealer)) {
            soldBy = soldBy.concat(dealer);
            for(int index = i; index < subData.size() ; index++) {
                if(subData.get(index).contains("LL")) {
                    return soldBy.concat(" ").concat(subData.get(index)).concat(", ");
                }
            }
        }

        return soldBy;
    }

    private List<String> GetRawDetailsList() {
        int record = 0;

        List<String> subData = new ArrayList<>();
        for(String s : this.site){
            if (s.contains("va-infobox0") && s.contains("table")){
                record++;
                subData.add(s);
            } else if(record > 0){
                if(s.contains("table") && !s.contains("/table")){
                    record++;
                }
                subData.add(s);
            }
            if(s.contains("/table")){
                if(record > 0){
                    record--;
                }
            }
        }
        return subData;
    }
}

