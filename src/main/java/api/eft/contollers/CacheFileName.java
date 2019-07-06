package api.eft.contollers;

import java.io.File;
import java.util.ArrayList;

public class CacheFileName  {

    private ArrayList<String> arrayList;

    public CacheFileName() {
        this.arrayList = new ArrayList<>();
    }

    public ArrayList<String> getFileNames(){
        File folder = new File("Cache\\");
        File[] listOfFiles = folder.listFiles();
        for(File file : listOfFiles) {
            arrayList.add(file.getName());
        }
        return arrayList;
    }
}
