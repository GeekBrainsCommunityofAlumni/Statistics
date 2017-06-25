package crawler;

import crawler.MultiThreadSpider.MultiThreadSpider;
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

        //TODO завернуть в while до тех пор пока не кончаться page в базе для сканирования.
        pagesIDForScan = dbHelper.getPagesIDWithoutScanDate(); //TODO Использовать метод который возвращает определенное количество Page для многоэкземплярного режима.
        for (int pageID : pagesIDForScan) {
            CrawlerTask crawlerTask = new CrawlerTask();
            crawlerTask.addPageForScanning(new PageForScanning(pageID, dbHelper.getUrlPageViaID(pageID)));
            crawlerTask.addPersonList(personArrayList);
            crawlerTaskRepository.addTask(crawlerTask);
        }

        System.out.println("Заданий в crawlerTaskRepository - " + crawlerTaskRepository.size());

        ArrayList<CrawlerThread> crawlerThreadArrayList = new ArrayList<>();

        for (int i = 0; i < NUMBEROFTHREAD; i++) {
            crawlerThreadArrayList.add(new CrawlerThread(crawlerTaskRepository, "thread__" + i));
        }

        for (CrawlerThread crawlerThread : crawlerThreadArrayList) {
            crawlerThread.start();
        }

        for (CrawlerThread crawlerThread : crawlerThreadArrayList) {
            crawlerThread.join();
        }
    }

    static class CrawlerThread extends Thread {
        CrawlerTaskRepository crawlerTaskRepository;
        CrawlerTask crawlerTask;
        Downloader downloader = new Downloader();
        Parser parser = new Parser();
        String threadName;

        CrawlerThread(CrawlerTaskRepository crawlerTaskRepository, String threadName) {
            this.crawlerTaskRepository = crawlerTaskRepository;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            while ((crawlerTask = crawlerTaskRepository.getTask()) != null) {
                System.out.println("Поток: " + threadName + "  Заданий в crawlerTaskRepository - " + crawlerTaskRepository.size());
                int pageID = crawlerTask.getPage().getPageID();
                String pageSource = null;

                try {
                    pageSource = downloader.download(crawlerTask.getPage().getUrl());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Проверяем  удалось ли получить страницу перед парсингом.
                if (pageSource != null) {
                    for (Person person : crawlerTask.getPersonList()) {
                        ArrayList<String> personKeywords = person.getKeywordsList();
                        int rank = parser.calculateRank(pageSource, personKeywords);
                        dbHelper.savePersonPageRank(person.getID(), pageID, rank);
                        dbHelper.setLastScanDateNow(pageID);
                    }
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
        while ((newSite = dbHelper.getNewSite()) != null) { //TODO При получении новой сайта для парсинга сразу в pages создавать ему фейковый page с временем и при выполнении данного метода удалаять его если прошло более часа.
            //TODO тем самым мы исключаем из вечного цикла - те сайты которые не могут распарситься в данный момент, но были по какой либо причине добавлены в список.
            System.out.println("Crawler:addNewSitesToPage: получен сайт для обработки: " + newSite);

            //Результирующий массив URLов, который в итоге будет записан в базу
            ArrayList<String> urlPages = new ArrayList<>();

            //Пытаемся получить robots.txt
            String robotTxt = null;
            String sitemap = null;
            boolean sitemapFound = false;
            String sitemapURL = null;

            robotTxt = new Downloader().downloadRobot(newSite);
            if (robotTxt != null) {
                System.out.println("Crawler:addNewSitesToPage: парсим robotTxt");
                sitemapURL = new Parser().parseRobotTxt(robotTxt);
                System.out.println("Crawler:addNewSitesToPage:sitemapURL: " + sitemapURL);
            } else {
                System.out.println("Crawler:addNewSitesToPage: robots.txt не найден");
            }

            // Если sitemap не найде в robots.txt, то присваиваем путь по умолчанию.
            // TODO Возможно этот функционал следует перенести в Downloader
            if (sitemapURL == null) {
                sitemapURL = "http://" + newSite + "/sitemap.xml";
            }

            System.out.println("Пробуем загрузить sitemap с адреса: " + sitemapURL);
            //  если Downloader сумел загрузить sitemap.xml по указанному адрессу то вытаскиваем из него ссылки для индексации
            if ((sitemap = new Downloader().downloadSiteMap(sitemapURL)) != null) {
                if (isItSitemapIndex(sitemap)) {
                    sitemapFound = true;
                    ArrayList<String> sitemapsURL = new Parser().parseSiteMapIndex(sitemap);
                    for (String sitemapUrl : sitemapsURL) {
                        sitemap = new Downloader().downloadSiteMap(sitemapUrl);
                        ArrayList<String> urlPagesSiteMapIndex = new Parser().parseSiteMap(sitemap);
                        dbHelper.addPagesToSite(urlPagesSiteMapIndex, newSite);
                        urlPages.clear();
                    }
                }

                //Если это sitemap.xml
                if (isItSitemap(sitemap)) {
                    sitemapFound = true;
                    urlPages = new Parser().parseSiteMap(sitemap);
                    dbHelper.addPagesToSite(urlPages, newSite);
                }
            }
            //если sitemap не найден или ответ не сумели распарсить, то запускаем паука.
            if (!sitemapFound) {
                System.out.println("Crawler:addNewSitesToPage: Sitemap для сайта " + newSite + " загрузить не удалось.");
                System.out.println("Тут дергаем паука");
                MultiThreadSpider multiThreadSpider = new MultiThreadSpider(newSite, 1000);
                MultiThreadSpider.run();
                dbHelper.addPagesToSite(multiThreadSpider.getScanedURLs(), newSite);
                //TODO здесь запускаем паука по newSite.

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
