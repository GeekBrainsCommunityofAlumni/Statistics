package crawler;

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





    }

}
