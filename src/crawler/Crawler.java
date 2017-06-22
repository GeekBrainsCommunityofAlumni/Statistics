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
    //private static Downloader downloader;
    //private static ArrayList<String> newSites;
    //private static Parser parser;
    private static DBHelper dbHelper;

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        init();
        addNewSitesToPages();

        try {
            crawlsNewSite();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recrawls();
    }

    private static void crawlsNewSite() throws IOException, ParserConfigurationException, SAXException, InterruptedException {
        CrawlerTaskRepository crawlerTaskRepository = new CrawlerTaskRepository();

        //подготавливаем список персон для загрузки в CrawlerTask
        ArrayList<Person> personArrayList = new ArrayList<>();
        for (int personID : personsID) {
            personArrayList.add(new Person(personID, dbHelper.getPersonKeywords(personID)));
        }

        pagesIDForScan = dbHelper.getPagesIDWithoutScanDate(); //Метод возвращает список ID старниц для сканирования. БЕЗ robots.txt и sitemap
        for (int pageID : pagesIDForScan){
            CrawlerTask crawlerTask = new CrawlerTask();
            crawlerTask.addPageForScanning(new PageForScanning(pageID, dbHelper.getUrlPageViaID(pageID)));
            crawlerTask.addPersonList(personArrayList);
            crawlerTaskRepository.addTask(crawlerTask);
        }

        System.out.println("Заданий в crawlerTaskRepository - " + crawlerTaskRepository.size());

        CrawlerThread crawlerThread1 = new CrawlerThread(crawlerTaskRepository, "thread1");
        CrawlerThread crawlerThread2 = new CrawlerThread(crawlerTaskRepository, "thread2");
        CrawlerThread crawlerThread3 = new CrawlerThread(crawlerTaskRepository, "thread3");

        crawlerThread1.start();
        crawlerThread2.start();
        crawlerThread3.start();

        crawlerThread1.join();
        crawlerThread2.join();
        crawlerThread3.join();



//        for (int pageID : pagesIDForScan) {
//            String pageSource = downloader.download(dbHelper.getUrlPageViaID(pageID));
//            //т.к. страницы мы не сохраняем то статистику придется считать "налету"
//            for (int personID : personsID)
//                ArrayList<String> personKeywords = dbHelper.getPersonKeywords(personID);
//                int rank = parser.calculateRank(pageSource, personKeywords);
//                dbHelper.savePersonPageRank(personID, pageID, rank);
//            }
//        }
    }

    static class CrawlerThread extends Thread {
        CrawlerTaskRepository crawlerTaskRepository;
        CrawlerTask crawlerTask;
        Downloader downloader = new Downloader();
        Parser parser = new Parser();
        String threadName;

        CrawlerThread(CrawlerTaskRepository crawlerTaskRepository, String threadName){
            this.crawlerTaskRepository = crawlerTaskRepository;
            this.threadName = threadName;
        }

        @Override
        public void run(){
            while((crawlerTask = crawlerTaskRepository.getTask())!= null){
                System.out.println("Поток: " + threadName + "  Заданий в crawlerTaskRepository - " + crawlerTaskRepository.size());
                int pageID = crawlerTask.getPage().getPageID();
                String pageSource = null;

                try {
                    pageSource = downloader.download(crawlerTask.getPage().getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Person person: crawlerTask.getPersonList()) {
                    ArrayList<String> personKeywords = person.getKeywordsList();
                    int rank = parser.calculateRank(pageSource, personKeywords);
                    dbHelper.savePersonPageRank(person.getID(), pageID, rank);
                    dbHelper.setLastScanDateNow(pageID);
                }
            }
            System.out.println("Поток завершен");
        }
    }

    private static void recrawls() {
        //Функция повторного обхода
    }

    private static void init() {
        dbHelper = new DBHelper();
        dbHelper.connectToDB();
        personsID = dbHelper.getPersonsID();
    }

    private static void addNewSitesToPages() throws IOException, ParserConfigurationException, SAXException { // Решить что делать с пробрасываемыми исключениями. throws IOException, ParserConfigurationException, SAXException
        String newSite;
        while((newSite = dbHelper.getNewSite()) != null){
            System.out.println("Crawler:addNewSitesToPage: получен сайт для обработки: " + newSite);

            //Результирующий массив URLов, который в итоге будет записан в базу
            ArrayList<String> urlPages = new ArrayList<>();

            //Пытаемся получить robots.txt
            String robotTxt = null;
            String sitemap = null;
            robotTxt = new Downloader().downloadRobot(newSite);
            if(robotTxt != null){
                System.out.println("Crawler:addNewSitesToPage: парсим robotTxt");
                String sitemapURL = null;
                sitemapURL = new Parser().parseRobotTxt(robotTxt);
                System.out.println("Crawler:addNewSitesToPage:sitemapURL: " + sitemapURL);

                //Если найден адрес sitemap.xml то скачиваем его
                if(sitemapURL != null){
                    sitemap = new Downloader().downloadSiteMap(sitemapURL);

                    //Если это sitemapIndex.xml
                    if (isItSitemapIndex(sitemap)) {
                        ArrayList<String> sitemapsURL = new Parser().parseSiteMapIndex(sitemap);
                        for (String sitemapUrl : sitemapsURL) {
                            sitemap = new Downloader().downloadSiteMap(sitemapUrl);
                            ArrayList<String> urlPagesSiteMapIndex = new Parser().parseSiteMap(sitemap);
                            dbHelper.addPagesToSite(urlPagesSiteMapIndex, newSite);
                            urlPages.clear();
                        }
                    }

                    //Если это sitemap.xml
                    if (isItSitemap(sitemap)){
                        urlPages = new Parser().parseSiteMap(sitemap);
                        dbHelper.addPagesToSite(urlPages, newSite);
                    }
                }
            } else {
                System.out.println("robots.txt - отсутсвует");
                //TODO Сайтмэп не найден - здесь будет функция построения карты сайта без сайтмэпа
            }
        }
//        ArrayList<String> newSites = dbHelper.getNewSites();
//        System.out.println("Новых сайтов в базе " + newSites.size());
//        if (newSites.size()>0) {

//            for (String site : newSites) {
//                String robotTxt = null;
//                ArrayList<String> urlPages = new ArrayList<>();

//                try {
//                    robotTxt = new Downloader().downloadRobot(site);
//                    System.out.println("Robots.txt содержит:");
//                    System.out.println(robotTxt);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String sitemapURL = null;

//                if (robotTxt != null) {
//                    sitemapURL = new Parser().parseRobotTxt(robotTxt);
//                    System.out.println("Найден sitemap.xml : " + sitemapURL);
//                }

//                if (sitemapURL != null) {
//                    String sitemap = null;
//                    sitemap = new Downloader().downloadSiteMap(sitemapURL);
//                    if (isItSitemapIndex(sitemap)) {
//                        ArrayList<String> sitemapsURL = new Parser().parseSiteMapIndex(sitemap);
//                        for (String sitemapUrl : sitemapsURL) {
//                            sitemap = new Downloader().downloadSiteMap(sitemapUrl);
//                            System.gc();
//                            ArrayList<String> urlPagesSiteMapIndex = new Parser().parseSiteMap(sitemap);
//                            long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//                            for (String url : urlPagesSiteMapIndex) {
//                                urlPages.add(url);
//                            }
//                            dbHelper.addPagesToSite(urlPages, site);
//                            urlPages.clear();
//                        }
//                    }
//
//                    if (isItSitemap(sitemap)) {
//                        urlPages = new Parser().parseSiteMap(sitemap);
//                        dbHelper.addPagesToSite(urlPages, site);
//                    }
//                } else {
//                    //TODO Сайтмэп не найден - здесь будет функция построения карты сайта без сайтмэпа
//                }
//            }
//        }
    }

    private static boolean isItSitemap(String sitemap) {
        return sitemap.contains("urlset");
    }

    private static boolean isItSitemapIndex(String sitemap) {
        return sitemap.contains("sitemapindex");
    }
}
