package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;

public class ParsRobots {
    private static String host;
    private static final String searhStr = "sitemap";
    private static final String bdAdress = "jdbc:sqlite:test.db";
    private static String sitemap;
    private static Connection connection;
    private static Statement statement;

    public ParsRobots(String host) {
        this.host = host;
    }

    private static void connect() throws SQLException {
        connection = DriverManager.getConnection(bdAdress);
        statement = connection.createStatement();
    }

    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void parsRobotsFile() throws IOException, SQLException {
        URL url = new URL("https://" + host + "/robots.txt");
        URLConnection con = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String robots;
        while ((robots = reader.readLine()) != null) {
            if (robots.contains(searhStr)) {
                sitemap = robots.substring(9);
            }
        }
        try {
            connect();
            statement.executeUpdate("insert into ttt values (null, '" + sitemap + "');");
        } catch (Exception e) {
            System.out.println("Проблемы с БД");
        } finally {
            disconnect();
        }
    }
}
