package data;

import data.cache.CacheRetriever;
import data.cache.SetCacheValues;
import data.json.JsonElement;
import data.network.SiteRetriever;
import data.network.SiteParser;
import data.objects.ETFObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RetrieveData {

    private String key;
    private HashMap<String, ArrayList> pairing;
    private List<JsonElement> detail;

    public RetrieveData(String key) {

        this.key = encodeString(key);
    }

    private String encodeString(String key) {
        return key
                .replaceAll("%","%25" )
                .replaceAll(" ","%20")
                .replaceAll(":", "%3A" )
                .replaceAll(";","%3B" )
                .replaceAll("@","%40" )
                .replaceAll("<","%3C" )
                .replaceAll(">","%3E" )
                .replaceAll("=","%3D" )
                .replaceAll("&","%26" )
                .replaceAll("\\$","%24" )
                .replaceAll("#","%23" )
                .replaceAll("\\+","%2B" )
                .replaceAll(",","%2C" )
                .replaceAll("\\?","%3F" )
                .replaceAll("“","%93" )
                .replaceAll("”","%94" )
                .replaceAll("\"","%22" )
                .replaceAll("/","%2F" );

    }
    public ETFObject getData(){
        CacheRetriever cacheRetriever = new CacheRetriever(this.key);

        if( !cacheRetriever.doesExist() ){
            loadTheSite();
            SetCacheValues setCacheValues = new SetCacheValues(this.key, this.pairing, this.detail);
            setCacheValues.cache();
        } else {
            cacheRetriever.loadDataFromCache();
            this.detail = cacheRetriever.getDetail();
            this.pairing =cacheRetriever.getPairing();
        }
        return new ETFObject(this.detail , this.pairing);
    }

    private void loadTheSite(){
        data.network.SiteRetriever siteRetriever = new SiteRetriever(this.key);
        List<String> site = siteRetriever.getSite();
        SiteParser itemDetailsRetriever = new SiteParser(site);
        this.pairing = itemDetailsRetriever.getItemHash();
        this.detail = itemDetailsRetriever.getItemDetails();
    }



}


