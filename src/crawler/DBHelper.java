package crawler;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Serg on 09.06.2017.
 */
public class DBHelper {
    private ArrayList<String> pagesWithoutScanDate;
    private Connection connectionToDB;
    private Statement statement;
    private ResultSet resultSet;
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

    public ArrayList<Integer> getPagesIDWithoutScanDate() { //Метод возвращает список ID страниц для сканирования. БЕЗ robots.txt и sitemap
        ArrayList<Integer> resultingArrayList = new ArrayList<>();
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT id FROM pages WHERE lastscandate IS NULL;");
            while (resultSet.next()) {
                resultingArrayList.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultingArrayList;
    }

    public ArrayList<Integer> getPersonsID() { //Возвращает список ID таблицы Persons, для которых считаем статистику.
        ArrayList<Integer> resultingArrayList = new ArrayList<>();
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT id FROM persons;");
            while (resultSet.next()) {
                resultingArrayList.add(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultingArrayList;
    }

    public ArrayList<String> getPersonKeywords(int personID) { //Возвращает ключевые слова для ID персоны.
        ArrayList<String> resultingArrayList = new ArrayList<>();
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM keywords WHERE personid = " + personID + ";");
            while (resultSet.next()) {
                resultingArrayList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
//Можно добавить обработку ошибок если краулер берет фразу для поиска только из таблицы keywords, а они там не заведены.
            e.printStackTrace();
        }
        return resultingArrayList;
    }

    public String getUrlPageViaID(int pageID) { //Возвращает URL страницы в зависимости от ID
        String urlOfPage = "";
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT url FROM pages WHERE id = " + pageID + ";");
            if (resultSet.next()) {
                urlOfPage = resultSet.getString(1);
            } else throw new SQLException("Ошибка: неправильный id url'a от краулера для метода getUrlPageViaID для таблицы pages.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urlOfPage;
    }

    public void savePersonPageRank(int personID, int pageID, int rank) { //Сохраняет данные в таблицу PersonPageRank

    }

    public ArrayList<String> getNewSites() { //Возвращает список URL сайтов для которых нет НИ ОДНОЙ строки в таблице Pages
        ArrayList<String> resultingArrayList = new ArrayList<>();
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT name FROM sites WHERE id NOT IN (SELECT siteid FROM pages);");
            while (resultSet.next()) {
                resultingArrayList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultingArrayList;
    }

    public void disconnectFromDB() {
        try {
            connectionToDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
