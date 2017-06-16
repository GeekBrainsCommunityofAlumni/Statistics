package crawler;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

/**
 * Created by Serg on 09.06.2017.
 */
public class DBHelper {
    private ArrayList<String> pagesWithoutScanDate;
    private Connection connectionToDB;
    private Statement statement;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    //*******Настройки базы данных*********
    private final String HOSTDB = "localhost";
    private final int PORTDB = 3306;
    private final String DBSCHEMANAME = "statistics";
    private final String DBLOGIN = "statistic_user";
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

    public void addPagesToSite(ArrayList<String> urlPages, String siteName) {//Добавляем страницы нового сайта в таблицу pages, ни одной страницы которого не было в таблице pages.
        try {
            if (existSite(siteName)) {
                int siteID = getSiteID(siteName);
                preparedStatement = connectionToDB.prepareStatement("INSERT INTO pages (url, siteid, founddatetime) VALUES (?, ?, ?);");
                connectionToDB.setAutoCommit(false);
                for (String url : urlPages) {
                    preparedStatement.setString(1, url);
                    preparedStatement.setInt(2, siteID);
                    preparedStatement.setString(3, getCurrentDateTime());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connectionToDB.setAutoCommit(true);
            } else {
                    throw new SQLException("Ошибка: неправильный site передан через метод dbHelper.addPagesToSite(), вызываемый в краулере.");
            }
            //TODO в будущем, при новом функционале сделать проверку: существует ли добавляемая страница уже в бд
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getSiteID (String siteName) {    //возвращает id сайта по его названию
        int siteID = -101;
        try {
            statement = connectionToDB.createStatement();
            resultSet = statement.executeQuery("SELECT id FROM sites WHERE name = '" + siteName + "';");
            if (resultSet.next()) {
                siteID = resultSet.getInt(1);
            } else throw new SQLException("Ошибка: неправильный siteName передан через метод dbHelper.getSiteID() вызываемый в методе dbHelper.addPagesToSite()");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return siteID;
    }

    public String getCurrentDateTime() {    //выдает дату и время в текущий момент в формате для mysql
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
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

    public ArrayList<String> getPersonKeywords(int personID) { //Возвращает ключевые слова для ID персоны из таблиц persons и keywords
        ArrayList<String> resultingArrayList = new ArrayList<>();
        try {
            if (existPersonID(personID)) {
                statement = connectionToDB.createStatement();   //достаем фамилию из таблицы persons
                resultSet = statement.executeQuery("SELECT name FROM persons WHERE id = " + personID + ";");
                if (resultSet.next()) {
                    resultingArrayList.add(resultSet.getString(1));
                }

                statement = connectionToDB.createStatement();   //достаем ключевые фразы из таблицы keywords
                resultSet = statement.executeQuery("SELECT name FROM keywords WHERE personid = " + personID + ";");
                while (resultSet.next()) {
                    resultingArrayList.add(resultSet.getString(1));
                }
            } else throw new SQLException("Неправильный personID передан через dbHelper.getPersonKeywords, вызываемый в краулере.");
        } catch (SQLException e) {
//TODO: Можно добавить обработку ошибок если краулер берет фразу для поиска только из таблицы keywords, а они там не заведены.
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
            } else throw new SQLException("Ошибка: неправильный pageID передан через метод dbHelper.getUrlPageViaID, вызываемый в краулере.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return urlOfPage;
    }

    public void savePersonPageRank(int personID, int pageID, int rank) { //Сохраняет данные в таблицу PersonPageRank
        try {
            if (existPageWithPerson(personID, pageID)) {
                preparedStatement = connectionToDB.prepareStatement("UPDATE personpagerank SET rank = ? WHERE (personid = ? AND pageid = ?);");
                preparedStatement.setInt(1, rank);
                preparedStatement.setInt(2, personID);
                preparedStatement.setInt(3, pageID);
                preparedStatement.executeUpdate();
            } else {
                if (existPersonID(personID)) {
                    if (existPageID(pageID)) {
                        preparedStatement = connectionToDB.prepareStatement("INSERT INTO personpagerank (personid, pageid, rank) VALUES (?, ?, ?);");
                        preparedStatement.setInt(1, personID);
                        preparedStatement.setInt(2, pageID);
                        preparedStatement.setInt(3, rank);
                        preparedStatement.executeUpdate();
                    }
                    else {
                        throw new SQLException("Неправильный pageID передан через dbHelper.savePersonPageRank, вызываемый в краулере.");
                    }
                } else {
                    throw new SQLException("Неправильный personID передан через dbHelper.savePersonPageRank, вызываемый в краулере.");
                }              }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existPageWithPerson(int personID, int pageID) {  //Проверяет, существует ли уже в таблице personpagerank указанная запись с personID и pageID
        try {
            preparedStatement = connectionToDB.prepareStatement("SELECT personID, pageID FROM personpagerank WHERE (personID = ? AND pageID = ?);");
            preparedStatement.setInt(1, personID);
            preparedStatement.setInt(2, pageID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if ((resultSet.getInt(1) == personID) & (resultSet.getInt(2) == pageID)) {
                    return true;
                }
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existPersonID(int personID) {    //Проверяет, есть ли в таблице persons указанный id персоны
        try {
            preparedStatement = connectionToDB.prepareStatement("SELECT id FROM persons WHERE id = ?;");
            preparedStatement.setInt(1, personID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == personID) {
                    return true;
                }
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existPageID(int pageID) {    //Проверяет, есть ли в таблице pages указанный id страницы
        try {
            preparedStatement = connectionToDB.prepareStatement("SELECT id FROM pages WHERE id = ?;");
            preparedStatement.setInt(1, pageID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getInt(1) == pageID) {
                    return true;
                }
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existSite(String nameOfSite) {    //Проверяет, есть ли в таблице sites указанный сайт
        try {
            preparedStatement = connectionToDB.prepareStatement("SELECT name FROM sites WHERE name = ?;");
            preparedStatement.setString(1, nameOfSite);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String resultFromResultSet = resultSet.getString(1);
                if (resultFromResultSet.equals(nameOfSite)) {
                    return true;
                }
            } else return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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

    //Эти методы будет реализовывать sergey_kogut в rest сервисе, но я на всякий случай пропишу их позже, если Сергей не успеет.
    public void addPerson (String personName) { //добавление персоны в таблицу persons

    }

    public void deletePerson (String personName) {  //удаление персоны из таблицы persons

    }

    public void editPerson (String personName) {    //редактирование имени персоны в таблице persons

    }

    public void addSite (String siteName) { //добавление сайта в таблицу sites

    }

    public void deleteSite (String siteName) {  //удаление сайта из таблицы sites

    }

    public void editSite (String siteName) {    //редактирование имени сайта в таблице sites

    }

    public void disconnectFromDB() {
        try {
            connectionToDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
