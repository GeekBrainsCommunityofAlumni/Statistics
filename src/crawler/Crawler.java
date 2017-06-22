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
    private static DBHelper dbHelper;

    private static final int NUMBEROFTHREAD = 8;

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

        ArrayList<CrawlerThread> crawlerThreadArrayList = new ArrayList<>();

        for(int i = 0; i < NUMBEROFTHREAD; i++){
            crawlerThreadArrayList.add(new CrawlerThread(crawlerTaskRepository, "thread__" + i));
        }

        for (CrawlerThread crawlerThread: crawlerThreadArrayList) {
            crawlerThread.start();
        }

        for (CrawlerThread crawlerThread: crawlerThreadArrayList) {
            crawlerThread.join();
        }
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
    }

    private static boolean isItSitemap(String sitemap) {
        return sitemap.contains("urlset");
    }

    private static boolean isItSitemapIndex(String sitemap) {
        return sitemap.contains("sitemapindex");
    }
}
