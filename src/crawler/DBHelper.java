package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 09.06.2017.
 */
public class DBHelper {
    private ArrayList<String> pagesWithoutScanDate;

    public void addPagesToSite(ArrayList<String> urlPages) {

    }

    public ArrayList<Integer> getPagesIDWithoutScanDate() { //Метод возвращает список ID старниц для сканирования. БЕЗ robots.txt и sitemap
        return new ArrayList<>();
    }

    public ArrayList<Integer> getPersonsID() { //Возвращает список ID таблицы Persons, для которых считаем статистику.
        return new ArrayList<>();
    }

    public ArrayList<String> getPersonKeywords(int personID) { //Возвращает ключевые слова для ID персоны.
        return new ArrayList<>();
    }

    public String getUrlPageViaID(int pageID) { //Возвращает URL старинцы в зависимости от ID
        return "";
    }

    public void savePersonPageRank(int personID, int pageID, int rank) { //Сохраняет данные в таблицу PersonPageRank

    }
}
