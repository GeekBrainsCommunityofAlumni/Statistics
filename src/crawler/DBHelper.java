package crawler;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Serg on 09.06.2017.
 */
public class DBHelper {
    private ArrayList<String> pagesWithoutScanDate;
    private Connection connectionToDB;
    //*******Настройки базы данных*********
    private final String HOSTDB = "localhost";
    private final int PORTDB = 3306;
    private final String DBSCHEMANAME = "statistics";
    private final String DBLOGIN = "root";
    private final String DBPASSWORD = "123456";
    //*******Конец настройки базы данных*********

    public void connectToDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connectionToDB = DriverManager.getConnection("jdbc:mysql://" + HOSTDB + ":" + PORTDB + "/" + DBSCHEMANAME + "?useSSL=false", DBLOGIN , DBPASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public ArrayList<String> getNewSites() { //Возвращает список URL сайтов для которой нет НИ ОДНОЙ строки в таблице Pages
        return new ArrayList<>();
    }

    public void disconnectFromDB() {
        try {
            connectionToDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
