package data.network;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SiteRetriever {
    private static String HOST = "https://escapefromtarkov.gamepedia.com/";

    private String url;
    private List<String> site;

    public SiteRetriever(String url) {
        this.url = url;
    }

    public List<String> getSite() {
        DataInputStream dataInputStream = getPayload();
        this.site = getUnformattedSiteLines(dataInputStream);
        return site;
    }

    private DataInputStream getPayload () {
        DataInputStream dataInputStream = null;
        try {

            URLConnection openConnection = new URL(HOST.concat(this.url)).openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            InputStream inputStream = openConnection.getInputStream();
            dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataInputStream;
    }

    private List<String> getUnformattedSiteLines(DataInputStream dataInputStream) {
        List<String> site = new ArrayList<String>();
        try {
            String s = "";
            while (true) {
                String input = dataInputStream.readUTF();
                for (int i = 0; i < input.length(); i++) {
                    char c = input.charAt(i);
                    if ('<' == c || '>' == c) {
                        if(s.length() != 0) {
                            site.add(s);
                        }
                        s = "";
                    } else {
                        s = s.concat(Character.toString(c));
                    }
                }
            }
        } catch (EOFException e) {
            System.out.println("Loaded From :" + HOST.concat(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return site;
    }
}
