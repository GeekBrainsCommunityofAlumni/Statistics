package crawler.MultiThreadSpider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by Serg on 24.06.2017.
 */
public class MultiThreadSpider {
    static String scanSite;
    static HashSet<String> notScanedURLs = new HashSet<>();
    static HashSet<String> tmpScanedURLs = new HashSet<>();
    static HashSet<String> scanedURLs = new HashSet<>();

    public static void main(String[] args){
        scanSite = "http://lenta.ru";
        run();
        print();
    }

    public void MultiThreadSpider(String scanSite) {
        this.scanSite = scanSite;
    }

    public static void run() {
        notScanedURLs.add(scanSite);
        while (notScanedURLs.size() > 0) {
            Document doc = null;
            String url = notScanedURLs.iterator().next();

            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Element element : doc.select("a")) {
                if (element.attr("href").startsWith("/")) {
                    tmpScanedURLs.add(element.attr("abs:href"));
                }
            }
            notScanedURLs.remove(url);
            System.out.println(url);
            System.out.println(scanedURLs.size());
            scanedURLs.add(url);

            for (String tmpURL : tmpScanedURLs) {
                if (!scanedURLs.contains(tmpURL)) {
                    notScanedURLs.add(tmpURL);
                }
            }
        }
    }

    public static void print(){
        System.out.println(scanedURLs);
    }
}
