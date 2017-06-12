package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 12.06.2017.
 */
public class CrawlerTest {
    private static DBHelper dbHelper;


    public static void main(String[] args) {
        init();

        testDBHelper();
    }


    private static void init() {
        dbHelper = new DBHelper();
    }

    private static void testDBHelper() {
        System.out.println("Подключаемся к MySQL");
        dbHelper.connectToDB();
        System.out.println("Вероятно успешно");

        System.out.println("---");

        System.out.println("Список ID имеющихся в базе:");
        ArrayList<Integer> personsID = dbHelper.getPersonsID();
        for (Integer personID:personsID) {
            System.out.println(personID);
        }


    }

}
