package data.objects;

import data.json.JsonElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ETFObject {

    private Details details;
    private HashMap<String, ArrayList> slots;
    private ArrayList compatibility;

    public HashMap<String, ArrayList> getSlots() {
        return slots;
    }

    public Details getDetails() {
        return details;
    }

    public ETFObject(List<JsonElement> detail, HashMap<String, ArrayList> pairing) {
        details = new Details();
        slots = pairing;
        compatibility = setCompatibility(pairing);
        mapAllDetails(detail);
        mapPairing(pairing);
    }
    public ArrayList getCompatibility() {
        return compatibility;
    }

    private ArrayList setCompatibility(HashMap<String, ArrayList> pairing) {
        ArrayList compatibility = pairing.get("Compatibility");
        pairing.remove("Compatibility");
        if (compatibility == null){
            return new ArrayList();
        }
        return compatibility;
    }

    private void mapPairing(HashMap<String, ArrayList> pairing) {
//        for(String key : pairing.keySet()) {
//            slots.add(key , pairing.get(key));
//        }
    }

    private void mapAllDetails(List<JsonElement> detail) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        for ( JsonElement tmp : detail ) {
            if(tmp.getKey().contains("title")) {
                details.setTitle(tmp.getValue());
            } else if(tmp.getKey().contains("SoldBy")) {
                details.setSoldBy(tmp.getValue());
            } else if(tmp.getKey().contains("Recoil")) {
                details.setRecoil(tmp.getValue());
            } else if(tmp.getKey().contains("Ergonomics")) {
                details.setErgonomics(tmp.getValue());
            } else {
                System.out.println( tmp.getKey() + " : " + tmp.getValue() );
            }
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
    }




}
