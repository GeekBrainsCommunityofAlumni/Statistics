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
    private PreparedStatement preparedStatement;
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
            } else throw new SQLException("Ошибка: неправильный id url'a от краулера для метода getUrlPageViaID для таблицы pages.");
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
                        throw new SQLException("Неправильный pageID передан через savePersonPageRank().");
                    }
                } else {
                    throw new SQLException("Неправильный personID передан через savePersonPageRank().");
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
