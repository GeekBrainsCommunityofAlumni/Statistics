package crawler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Serg on 08.06.2017.
 */
public class Crawler {
    private static ArrayList<String> urlsForScan;
    private static Downloader downloader;
    private static ArrayList<String> newSites;
    private static ArrayList<String> pagesWithoutScanDate;
    private static Parser parser;
    private static DBHelper dbHelper;

    public static void main(String[] args) throws IOException {
        init();
        crawlsNewSite();
        recrawls();
    }

    private static void crawlsNewSite() throws IOException {
        newSites = getNewSites();
        addNewSitesToPages(newSites);
        urlsForScan = getPagesWithoutScanDate();
        for (String url : urlsForScan) {
            downloader.download(url);
            //Коллеги тут вопрос, как и кем будем определять тип получаемого контента? (robot.txt, sitemap, старница)
        }
    }

    private static void recrawls() {
        //Функция повторного обхода
    }

    private static void init() {
        downloader = new Downloader();
        parser = new Parser();
        dbHelper = new DBHelper();
    }

    private static void addNewSitesToPages(ArrayList<String> newSites) {
        for (String site : newSites) {
            String robotTxt = downloader.downloadRobot(site);
            String sitemapURL = parser.parseRobotTxt(robotTxt);
            String sitemap = downloader.downloadSiteMap(sitemapURL);
            ArrayList<String> urlPages = parser.parseSiteMap(sitemap);
            dbHelper.addPagesToSite(urlPages);
        }
    }

    private static ArrayList<String> getNewSites() {
        //Получить из базы строки из таблицы Sites, которым не соответсвует НИ ОДНОЙ строки в таблице Pages
        return newSites;
    }

    private static ArrayList<String> getPagesWithoutScanDate() {
        //Получить из таблицы Pages все ссылки с LastScanDate равным null
        return pagesWithoutScanDate;
    }
}
