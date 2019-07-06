package api.eft.contollers;

import java.io.File;
import java.util.ArrayList;

public class CacheFileName  {

    private ArrayList<String> arrayList;

    public CacheFileName() {
        this.arrayList = new ArrayList<>();
    }

    public ArrayList<String> getFileNames(){
        File folder = new File("Cache/");
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles) {
            String name = file.getName();
            name = name.replace(".json", "");
            name = removeChars(name);
            arrayList.add(name);

        }
        return arrayList;
    }

    private String removeChars(String name) {
          name = name
                .replaceAll("%20"," ")
                .replaceAll("%3A",":")
                .replaceAll("%3B", ";")
                .replaceAll("%40","@")
                .replaceAll("%3C","<")
                .replaceAll("%3E", ">")
                .replaceAll("%3D","=")
                .replaceAll("%26", "&")
                .replaceAll("%24", "\\$")
                .replaceAll("%23", "#")
                .replaceAll("%2B", "\\+")
                .replaceAll("%2C",",")
                .replaceAll("%3F","\\?")
                .replaceAll("%93" , "“")
                .replaceAll("%94","”")
                .replaceAll("%22","\"")
                .replaceAll("%2F", "/")
                .replaceAll("%25","%");
        return name;
    }
}
