package crawler;

import java.util.ArrayList;

/**
 * Created by Serg on 12.06.2017.
 */
public class CrawlerTest {
    private static DBHelper dbHelper;
    private static Parser parser;


    public static void main(String[] args) {
        init();

        //testDBHelper();
        testParser();
    }

    private static void testParser() {
        System.out.println("Тестируем Parser...");
        parser = new Parser();
        String testText1 = "<body>Иванов Иванов не лопух.</body>";
        ArrayList<String> testKeywords1 = new ArrayList<>();
        testKeywords1.add("Иванов");
        testKeywords1.add("лопух");
        int rank1 = parser.calculateRank(testText1, testKeywords1);
        System.out.println("testRank1:" +(3==rank1));

        String testText2 = "alksqw jdhfkslerjf hsaerlkjfhs'f;lskqwf';aslkfaks jhfdksa faaserqwgfdksjhf dg;drtslfkgj ;dslrtjgrtrt";
        ArrayList<String> testKeywords2 = new ArrayList<>();
        testKeywords2.add("qw");
        testKeywords2.add("er");
        testKeywords2.add("rt");
        int rank2 = parser.calculateRank(testText2, testKeywords2);
        System.out.println("testRank2:" + (10==rank2));
    }


    private static void init() {
    }

    private static void testDBHelper() {
        dbHelper = new DBHelper();
        System.out.println("Подключаемся к MySQL");
        dbHelper.connectToDB();
        System.out.println("Вероятно успешно");

        System.out.println("---");

        System.out.println("Список ID имеющихся в базе:");
        ArrayList<Integer> personsID = dbHelper.getPersonsID();
        //Показываем что получили.
        for (Integer personID : personsID) {
            System.out.println(personID);
        }
    }

}
