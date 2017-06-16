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


        String testRobotsTxt1 = "User-agent: *\n" +
                "Allow: /infographics/\n" +
                "Disallow: /ajax\n" +
                "Disallow: /rate\n" +
                "Disallow: /pogoda\n" +
                "Disallow: /maps\n" +
                "Disallow: */page\n" +
                "Disallow: */comments\n" +
                "Disallow: /inf\n" +
                "Disallow: /pic\n" +
                "Disallow: /search*\n" +
                "Disallow: /monitoring\n" +
                "Disallow: /preview\n" +
                "Disallow: /rb*\n" +
                "Disallow: /*utm_\n" +
                "Disallow: /*/gallery/\n" +
                "Disallow: /*q=\n" +
                "Disallow: /currency.html?date=\n" +
                "Disallow: /go-web/\n" +
                "Disallow: /go-mobile/\n" +
                "Host: https://news.mail.ru\n" +
                "Sitemap: https://news.mail.ru/sitemap_index.xml";
        System.out.println("testRobotsTxt1:" + (parser.parseRobotTxt(testRobotsTxt1).equals("https://news.mail.ru/sitemap_index.xml")));
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
