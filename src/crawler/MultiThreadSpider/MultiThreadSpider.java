package crawler.MultiThreadSpider;

import crawler.DBHelper;
import crawler.Downloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Serg on 24.06.2017.
 */
public class MultiThreadSpider {
    static String scanSite;
    static int linkLimit;
    static HashSet<String> notScanedURLs = new HashSet<>();
    static HashSet<String> tmpScanedURLs = new HashSet<>();
    static HashSet<String> scanedURLs = new HashSet<>();
    static Downloader downloader = new Downloader();


//    public static void main(String[] args){
//        scanSite = "http://lenta.ru";
//        linkLimit = 100;
//        run();
//        print();
//    }

    public MultiThreadSpider(String scanSite, int linkLimit) {
        this.scanSite = scanSite;
        this.linkLimit = linkLimit;
    }

    public static void run() {
        notScanedURLs.add(scanSite);
        while ((notScanedURLs.size() > 0) && (scanedURLs.size() <= linkLimit)) {
            Document doc = null;
            String url = notScanedURLs.iterator().next();

            try {
                //System.out.println("URL: " + url);
                String html = downloader.download(url);
                //System.out.println("HTML length: " + html.length());
                doc = Jsoup.parse(html);
                doc.absUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Element element : doc.select("a")) {


                if (element.attr("href") != "") {
                    //System.out.println(element.attr("href").startsWith("/"));
                    if (element.attr("href").startsWith("/")) {
                        //System.out.println(element);
                        tmpScanedURLs.add(url + element.attr("href"));
                    }
                }
            }
            notScanedURLs.remove(url);
            //System.out.println(url);
            //System.out.println(scanedURLs.size());
            scanedURLs.add(url);


            for (String tmpURL : tmpScanedURLs) {
                if (!scanedURLs.contains(tmpURL)) {
                    notScanedURLs.add(tmpURL);
                }
            }
        }
    }

    public static void print() {
        System.out.println(scanedURLs);
    }

    public static ArrayList<String> getScanedURLs() {
        return new ArrayList<>(scanedURLs);
    }
}
