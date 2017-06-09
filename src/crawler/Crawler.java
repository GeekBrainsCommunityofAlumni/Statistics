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

    public static void main(String[] args) throws IOException {
        init();
        crawlsNewSite();
        recrawls();
    }

    private static void crawlsNewSite() throws IOException {
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

    private static void addNewSitesToPages(ArrayList<String> newSites) { //throws IOException, ParserConfigurationException, SAXException
        for (String site : newSites) {
            String robotTxt = downloader.downloadRobot(site);
            String sitemapURL = parser.parseRobotTxt(robotTxt);
            String sitemap = downloader.downloadSiteMap(sitemapURL);
            ArrayList<String> urlPages = parser.parseSiteMap(sitemap);
            dbHelper.addPagesToSite(urlPages);
        }
    }
}
