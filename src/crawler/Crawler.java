package crawler;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Serg on 08.06.2017.
 */
public class Crawler {
    private static ArrayList<Integer> pagesIDForScan;
    private static ArrayList<Integer> personsID;
    private static Downloader downloader;
    private static ArrayList<String> newSites;
    private static Parser parser;
    private static DBHelper dbHelper;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        init();
        crawlsNewSite();
        recrawls();
    }

    private static void crawlsNewSite() throws IOException, ParserConfigurationException, SAXException {
        newSites = dbHelper.getNewSites();
        addNewSitesToPages(newSites);
        pagesIDForScan = dbHelper.getPagesIDWithoutScanDate(); //Метод возвращает список ID старниц для сканирования. БЕЗ robots.txt и sitemap
        for (int pageID : pagesIDForScan) {
            String pageSource = downloader.download(dbHelper.getUrlPageViaID(pageID));
            //т.к. страницы мы не сохраняем то статистику придется считать "налету"
            for (int personID : personsID) {
                ArrayList<String> personKeywords = dbHelper.getPersonKeywords(personID);
                int rank = parser.calculateRank(pageSource, personKeywords);
                dbHelper.savePersonPageRank(personID, pageID, rank);
            }
        }
    }

    private static void recrawls() {
        //Функция повторного обхода
    }

    private static void init() {
        downloader = new Downloader();
        parser = new Parser();
        dbHelper = new DBHelper();
        personsID = dbHelper.getPersonsID();
    }

    private static void addNewSitesToPages(ArrayList<String> newSites) throws IOException, ParserConfigurationException, SAXException { // Решить что делать с пробрасываемыми исключениями. throws IOException, ParserConfigurationException, SAXException

        for (String site : newSites) {
            String robotTxt = null;
            ArrayList<String> urlPages = null;

            try {
                robotTxt = downloader.downloadRobot(site);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String sitemapURL = null;
            sitemapURL = parser.parseRobotTxt(robotTxt);
            String sitemap = null;

            sitemap = downloader.downloadSiteMap(sitemapURL);

            if(isItSitemapIndex(sitemap)){
                ArrayList<String> sitemapsURL = parser.parseSiteMapIndex(sitemap);
                for (String sitemapUrl: sitemapsURL) {
                    sitemap = downloader.downloadSiteMap(sitemapURL);
                    for (String url: parser.parseSiteMap(sitemap)) {
                        urlPages.add(url);
                    }
                }
            }

            if(isItSitemap(sitemap)){
                urlPages = parser.parseSiteMap(sitemap);
            }

            dbHelper.addPagesToSite(urlPages);
        }
    }

    private static boolean isItSitemap(String sitemap) {
        return sitemap.contains("urlset");
    }

    private static boolean isItSitemapIndex(String sitemap) {
        return sitemap.contains("sitemapindex");
    }
}
