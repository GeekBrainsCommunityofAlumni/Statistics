package crawler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Serg on 08.06.2017.
 */
public class Crawler {
    private Downloader downloader;
    private ArrayList<String> newSites;
    private ArrayList<String> urlsForScan;
    private ArrayList<String> pagesWithoutScanDate;

    public void main(String[] args) throws IOException {
        init();

        newSites = getNewSites();
        addNewSitesToPages(newSites);

        urlsForScan = getPagesWithoutScanDate();
        for (String url: urlsForScan) {
            downloader.download(url);
            //Коллеги тут вопрос, как и кем будем определять тип получаемого контента? (robot.txt, sitemap, старница)
        }

        recrawls();
    }

    private void recrawls() {
        //Функция повторного обхода
    }

    private void init() {
        downloader = new Downloader();
    }

    private void addNewSitesToPages(ArrayList<String> newSites) {
        for (String site: newSites) {
            //Добавляем ссылку на robots.txt в таблицу Pages для site
        }
    }

    public ArrayList<String> getNewSites() {
        //Получить из базы строки из таблицы Sites, которым не соответсвует НИ ОДНОЙ строки в таблице Pages
        return newSites;
    }

    public ArrayList<String> getPagesWithoutScanDate() {
        //Получить из таблицы Pages все ссылки с LastScanDate равным null
        return pagesWithoutScanDate;
    }
}
