package crawler;

/**
 * Created by Serg on 21.06.2017.
 */
public class PageForScanning {
    private int pageID;
    private String url;

    public PageForScanning(int pageID, String url){
        this.pageID = pageID;
        this.url = url;
    }

    public int getPageID(){return pageID;}
    public String getUrl(){return url;}
}
